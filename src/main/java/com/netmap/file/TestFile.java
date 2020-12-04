package com.netmap.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestFile {

	public static void main(String[] args) {
		String fromFilePath = "C:/temp/abc.txt";
		String toFilePath = "C:/temp/abc-new.txt";
		readTxtFileAndRemoveLine(fromFilePath, toFilePath);
		System.out.println("===================");
	}

	public static void readTxtFileAndRemoveLine(String fromFilePath, String toFilePath) {
		BufferedWriter writer = null;
		try {
			// String encoding = "GBK";
			File fileFrom = new File(fromFilePath);
			File fileTo = new File(toFilePath);
			writer = new BufferedWriter(new FileWriter(fileTo));
			if (fileTo.isFile() && !fileTo.exists()) {
				System.out.println("找不到指定的文件");
				fileTo.createNewFile();// 不存在则创建
			}
			if (fileFrom.isFile() && fileFrom.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(fileFrom));// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
					if ("".equals(lineTxt)) {
						continue;
					}
					writer.write(lineTxt);
					writer.write("\r\n");
				}
				read.close();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 功能：Java读取txt文件的内容 步骤： 1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 */
	public static void readTxtFile(String filePath) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
				}
				read.close();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}

	public static void writeTxtFile(String filepath) {
		File file = new File(filepath);
		BufferedWriter writer = null;
		try {
			if (file.isFile() && !file.exists()) {
				System.out.println("找不到指定的文件");
				file.createNewFile();// 不存在则创建
			} else {
				// writer = new BufferedWriter(new FileWriter(file,true));
				// //这里加入true 可以不覆盖原有TXT文件内容 续写
				writer = new BufferedWriter(new FileWriter(file));
				writer.write("hello");
				writer.write("\n");
				writer.write("hello");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
