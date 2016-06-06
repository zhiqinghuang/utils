package com.netmap.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SpiderHttpClient {

	public static void main(String[] args) {
		//processResidenceNameWithJsoup();
		// processResidenceName();
		// processImgSrc();
		//processMaintenanceFunds();
	}

	private static void processResidenceNameWithJsoup() {
		try {
			String filepath = "D:/tmp/wanda/";
			File fileMaintenanceFunds = new File(filepath);
			File[] fileYears = fileMaintenanceFunds.listFiles();
			for (int i = 0; i < fileYears.length; i++) {
				File fileYear = fileYears[i];
				if (fileYear.isFile()) {
					continue;
				}
				File[] fileMonths = fileYear.listFiles();
				for (int j = 0; j < fileMonths.length; j++) {
					File fileMonth = fileMonths[j];
					if (fileMonth.isFile()) {
						continue;
					}
					File[] fileDays = fileMonth.listFiles();
					for (int m = 0; m < fileDays.length; m++) {
						File fileDay = fileDays[m];
						String lstrFileName = fileDay.getName();
						if (!lstrFileName.equals("index.html")) {
							continue;
						}
						String lstrCharset = "UTF-8";
						Document documentIndex = Jsoup.parse(fileDay, "UTF-8");
						Elements elements = documentIndex.getElementsByTag("a");
						for (org.jsoup.nodes.Element element : elements) {
							String lstrResidenceName = element.text();
							if ("Parent Directory".equals(lstrResidenceName)) {
								continue;
							}
							String lstrHref = element.attr("href");
							Document documentResidence = Jsoup.parse(new File(fileMonth.getPath() + File.separator + lstrHref), "UTF-8");
							Elements elementsMeta = documentResidence.getElementsByTag("meta");
							for (org.jsoup.nodes.Element elementMeta : elementsMeta) {
								String lstrContent = elementMeta.attr("content");
								lstrCharset = lstrContent.substring(lstrContent.indexOf("charset") + 8);
								if (!"UTF-8".equalsIgnoreCase(lstrCharset)) {
									documentResidence = Jsoup.parse(new File(fileMonth.getPath() + File.separator + lstrHref), lstrCharset);
								}
							}
							Elements elementsTd = documentResidence.getElementsByAttributeValue("style", "font-size:19px; color:#EA0000; font-weight:bold");
							if (elementsTd.size() == 0) {
								elementsTd = documentResidence.getElementsByAttributeValue("style", "font-size:23px; font-family:黑体;color:#EA0000; font-weight:bold; line-height:40px");
							}
							for (org.jsoup.nodes.Element elementTd : elementsTd) {
								String lstrName = elementTd.text();
								if (lstrName.indexOf("万达家园") >= 0 || lstrName.indexOf("江南明珠") >= 0) {
									//System.err.println(lstrName);
								}
								if (lstrName.indexOf("万达家园 防水维修") >= 0) {
									//System.err.println(lstrName);
								}
								if (lstrName.indexOf("西堤国际") >= 0) {
									System.err.println(lstrName);
								}
								element.text(lstrName);
							}
						}
						documentIndex.charset(Charset.forName("UTF-8"));
						FileUtils.write(fileDay, documentIndex.html(), "UTF-8", false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void processResidenceName() {
		try {
			String filepath = "D:/tmp/wanda/";
			File fileMaintenanceFunds = new File(filepath);
			File[] fileYears = fileMaintenanceFunds.listFiles();
			for (int i = 0; i < fileYears.length; i++) {
				File fileYear = fileYears[i];
				File[] fileMonths = fileYear.listFiles();
				for (int j = 0; j < fileMonths.length; j++) {
					File fileMonth = fileMonths[j];
					if (fileMonth.isFile()) {
						continue;
					}
					File[] fileDays = fileMonth.listFiles();
					for (int m = 0; m < fileDays.length; m++) {
						File fileDay = fileDays[m];
						String lstrFileName = fileDay.getName();
						if (!lstrFileName.equals("index.html")) {
							continue;
						}
						Source source = new Source(fileDay);
						OutputDocument outputDocument = new OutputDocument(source);
						List<Element> tagsA = source.getAllElements("a");
						boolean isChanged = false;
						for (int n = 0; n < tagsA.size(); n++) {
							Element tagA = tagsA.get(n);
							Segment segment = tagA.getContent();
							String lstrTagA = segment.toString().trim();
							if ("Parent Directory".equals(lstrTagA)) {
								continue;
							}
							isChanged = true;
							String lstrHref = tagA.getAttributeValue("href");
							Source sourceDay = new Source(new File(fileMonth.getPath() + File.separator + lstrHref));
							List<Element> tagsTd = sourceDay.getAllElements("td");
							for (int t = 0; t < tagsTd.size(); t++) {
								Element tagTd = tagsTd.get(t);
								String lstrStyle = tagTd.getAttributeValue("style");
								if ("font-size:19px; color:#EA0000; font-weight:bold".equals(lstrStyle)) {
									outputDocument.replace(segment, tagTd.getContent().toString());
									break;
								}
							}

						}
						if (isChanged) {
							FileUtils.writeStringToFile(fileDay, outputDocument.toString(), "gb2312", false);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void processImgSrc() {
		try {
			String filepath = "D:/tmp/wanda/";
			File fileMaintenanceFunds = new File(filepath);
			File[] fileYears = fileMaintenanceFunds.listFiles();
			for (int i = 0; i < fileYears.length; i++) {
				File fileYear = fileYears[i];
				File[] fileMonths = fileYear.listFiles();
				for (int j = 0; j < fileMonths.length; j++) {
					File fileMonth = fileMonths[j];
					if (fileMonth.isFile()) {
						continue;
					}
					File[] fileDays = fileMonth.listFiles();
					for (int m = 0; m < fileDays.length; m++) {
						File fileDay = fileDays[m];
						String lstrFileName = fileDay.getName();
						if (!lstrFileName.endsWith(".html")) {
							continue;
						}
						Source source = new Source(fileDay);
						String lstrEncoding = source.getEncoding();
						OutputDocument outputDocument = new OutputDocument(source);
						List<Element> tagsImg = source.getAllElements("img");
						boolean isChanged = false;
						for (int n = 0; n < tagsImg.size(); n++) {
							Element tagImg = tagsImg.get(n);
							String lstrImgSrc = tagImg.getAttributeValue("src");
							if (!lstrImgSrc.startsWith("/upfile/Image/")) {
								continue;
							}
							isChanged = true;
							String lstrImgSrcRemoveSlash = lstrImgSrc.substring(1);
							Attributes attributes = tagImg.getAttributes();
							Map<String, String> mapAttributs = outputDocument.replace(attributes, true);
							mapAttributs.put("src", lstrImgSrcRemoveSlash);
						}
						if (isChanged) {
							FileUtils.writeStringToFile(fileDay, outputDocument.toString(), lstrEncoding, false);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void processMaintenanceFunds() {
		try {
			String lstrBase = "http://www.njwyw.com/";
			String lstrURLBase = "http://www.njwyw.com/gsgg/xytj/";
			//String[] arrayYears = { "2012", "2013", "2014", "2015" };
			String[] arrayYears = { "2015" };
			String filepath = "D:/tmp/wanda/";
			for (int j = 0; j < arrayYears.length; j++) {
				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpGet httpGet = new HttpGet(lstrURLBase + arrayYears[j]);
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					File fileFolder = new File(filepath + arrayYears[j]);
					if (!fileFolder.exists()) {
						fileFolder.mkdir();
					}
					String lstrFileName = filepath + arrayYears[j] + File.separator + "index.html";
					save2File(entity, lstrFileName);
					File file = new File(lstrFileName);
					String stringHtml = FileUtils.readFileToString(file);
					Source source = new Source(stringHtml);
					List<Element> tags = source.getAllElements("a");
					int liSize = tags.size();
					for (int i = 0; i < liSize; i++) {
						Element eA = tags.get(i);
						Segment segment = eA.getContent();
						String lstrNameMonth = segment.toString().trim();
						if ("Parent Directory".equals(lstrNameMonth)) {
							continue;
						}
						fileFolder = new File(filepath + arrayYears[j] + File.separator + lstrNameMonth);
						if (!fileFolder.exists()) {
							fileFolder.mkdir();
						}
						httpGet = new HttpGet(lstrURLBase + arrayYears[j] + "/" + lstrNameMonth);
						response = httpClient.execute(httpGet);
						entity = response.getEntity();
						if (entity != null) {
							lstrFileName = filepath + arrayYears[j] + File.separator + lstrNameMonth + "index.html";
							save2File(entity, lstrFileName);
							file = new File(lstrFileName);
							stringHtml = FileUtils.readFileToString(file);
							Source sourceMonth = new Source(stringHtml);
							List<Element> tagsMonth = sourceMonth.getAllElements("a");
							int liTagsMonth = tagsMonth.size();
							for (int n = 0; n < liTagsMonth; n++) {
								Element eTagsMonthA = tagsMonth.get(n);
								Segment segmentTagsMonth = eTagsMonthA.getContent();
								String lstrName = segmentTagsMonth.toString().trim();
								if ("Parent Directory".equals(lstrName)) {
									continue;
								}
								System.out.println(lstrName);
								httpGet = new HttpGet(lstrURLBase + arrayYears[j] + "/" + lstrNameMonth + lstrName);
								response = httpClient.execute(httpGet);
								entity = response.getEntity();
								if (entity != null) {
									lstrFileName = filepath + arrayYears[j] + File.separator + lstrNameMonth + lstrName;
									save2File(entity, lstrFileName);
									file = new File(lstrFileName);
									stringHtml = FileUtils.readFileToString(file);
									Source sourceDay = new Source(stringHtml);
									List<Element> tagsDay = sourceDay.getAllElements("img");
									int liTagsDay = tagsDay.size();
									for (int m = 0; m < liTagsDay; m++) {
										Element eTagDay = tagsDay.get(m);
										String lstrImgSrc = eTagDay.getAttributeValue("src");
										if (!lstrImgSrc.startsWith("/upfile/Image/")) {
											continue;
										}
										httpGet = new HttpGet(lstrBase + lstrImgSrc);
										response = httpClient.execute(httpGet);
										entity = response.getEntity();
										if (entity != null) {
											String lstrImagFile = filepath + arrayYears[j] + File.separator + lstrNameMonth + lstrImgSrc;
											String lstrImagFolder = lstrImagFile.substring(0, lstrImagFile.lastIndexOf("/"));
											fileFolder = new File(lstrImagFolder);
											if (!fileFolder.exists()) {
												fileFolder.mkdirs();
											}
											save2File(entity, lstrImagFile);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void save2File(HttpEntity entity, String strFileName) throws IOException {
		FileOutputStream fos = new FileOutputStream(strFileName);
		entity.writeTo(fos);
		fos.close();
	}
}