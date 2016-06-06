package com.netmap.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

public class SpiderHttpClientNewVersion {

	public static void main(String[] args) {
		// String lstrBase = "http://www.njwyw.com/";
		// String lstrUrl =
		// "http://www.njwyw.com/gsgg/xytj/2016/05/23143637727.html";
		// String filepath = "D:/tmp/wanda/";
		// mkDirs(lstrBase, lstrUrl, filepath);
		processMaintenanceFunds();
	}

	private static String buildIndexHtml(int index) {
		if (index == 0) {
			return "index.html";
		} else {
			StringBuffer sbIndexHtml = new StringBuffer();
			sbIndexHtml.append("index");
			sbIndexHtml.append(index);
			sbIndexHtml.append(".html");
			return sbIndexHtml.toString();
		}
	}

	private static String mkDirs(String strBase, String strUrl, String strBasePath) {
		int lastIndex = strUrl.lastIndexOf("/");
		String lstrFileName = strUrl.substring(lastIndex);
		String lstrFolder = strUrl.substring(strBase.length(), lastIndex);
		File fileFolder = new File(strBasePath + File.separator + lstrFolder);
		if (!fileFolder.exists()) {
			fileFolder.mkdirs();
		}
		String strFolderAndFileName = strBasePath + File.separator + lstrFolder + lstrFileName;
		return strFolderAndFileName;
	}

	private static void processMaintenanceFunds() {
		try {
			String lstrBase = "http://www.njwyw.com/";
			String lstrURLBase = "http://www.njwyw.com/gsgg/xytj/";
			String lstrLastFile = "http://www.njwyw.com/gsgg/xytj/2015/06/02162704273.html";
			boolean isLastFile = false;
			String filepath = "D:/tmp/wanda/";
			HttpClient httpClient = HttpClientBuilder.create().build();
			for (int j = 0; j < 100; j++) {
				if (isLastFile) {
					break;
				}
				String strIndexHtml = buildIndexHtml(j);
				HttpGet httpGet = new HttpGet(lstrURLBase + strIndexHtml);
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String lstrIndexFileName = filepath + File.separator + strIndexHtml;
					save2File(entity, lstrIndexFileName);
					File file = new File(lstrIndexFileName);
					String stringHtml = FileUtils.readFileToString(file);
					Source source = new Source(stringHtml);
					List<Element> tags = source.getAllElements("a");
					int liSize = tags.size();
					for (int i = 0; i < liSize; i++) {
						Element eA = tags.get(i);
						String strTarget = eA.getAttributeValue("target");
						if (!"_blank".equals(strTarget)) {
							continue;
						}
						String strHref = eA.getAttributeValue("href");
						if (lstrLastFile.equals(strHref)) {
							isLastFile = true;
							break;
						}
						String lstrFileName = mkDirs(lstrBase, strHref, filepath);
						httpGet = new HttpGet(strHref);
						response = httpClient.execute(httpGet);
						entity = response.getEntity();
						if (entity != null) {
							save2File(entity, lstrFileName);
							file = new File(lstrFileName);
							stringHtml = FileUtils.readFileToString(file);
							Source sourceDay = new Source(stringHtml);
							String lstrEncoding = source.getEncoding();
							boolean isChanged = false;
							OutputDocument outputDocument = new OutputDocument(sourceDay);
							List<Element> tagsDay = sourceDay.getAllElements("img");
							int liTagsDay = tagsDay.size();
							for (int m = 0; m < liTagsDay; m++) {
								Element eTagDay = tagsDay.get(m);
								String lstrImgSrc = eTagDay.getAttributeValue("src");
								if (!lstrImgSrc.startsWith("/upfile/Image/")) {
									continue;
								}
								Attributes attributes = eTagDay.getAttributes();
								Map<String, String> mapAttributs = outputDocument.replace(attributes, true);
								String lstrImgSrcRemoveSlash = lstrImgSrc.substring(1);
								mapAttributs.put("src", lstrImgSrcRemoveSlash);
								isChanged = true;
								httpGet = new HttpGet(lstrBase + lstrImgSrc);
								response = httpClient.execute(httpGet);
								entity = response.getEntity();
								if (entity != null) {
									String lstrImageUrl = lstrBase + lstrImgSrc;
									String lstrImagFile = mkDirs(lstrBase, lstrImageUrl, filepath);
									save2File(entity, lstrImagFile);
								}
							}
							if (isChanged) {
								FileUtils.writeStringToFile(file, outputDocument.toString(), lstrEncoding, false);
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