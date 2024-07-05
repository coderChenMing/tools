package com.learn.java8.compress;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class Base64Util {

	/**
	 * 将文件转成base64 字符串
	 *
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {
		return encodeBase64File(path, true);
	}

	/**
	 * 将文件转成base64 字符串
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(File file) throws Exception {
		return encodeBase64File(file, true);
	}

	/**
	 * 将文件转成base64 字符串
	 *
	 * @param path
	 * @param isNewline
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path, boolean isNewline) throws Exception {
		return encodeBase64File( new File(path), isNewline);
	}

	/**
	 * 将文件转成base64 字符串
	 *
	 * @param file
	 * @param isNewline
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(File file, boolean isNewline) throws Exception {
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		String str = new BASE64Encoder().encode(buffer);
		return isNewline?str:str.replaceAll("(\r\n|\r|\n|\n\r)", "");
	}

	/**
	 * 将base64字符解码保存文件
	 *
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);

		String path = targetPath.substring(0,targetPath.lastIndexOf("/"));
		File file = new File(path);
		//如果文件夹不存在则创建
		if(!file.exists()){
			file.mkdir();
		}

		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}

	/**
	 * 将base64字符保存文本文件
	 *
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void toFile(String base64Code, String targetPath) throws Exception {

		byte[] buffer = base64Code.getBytes();
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}

	public static void main(String[] args) {
		try {
			String base64Code = encodeBase64File("D:/detect/findRes_find.zip");
			System.out.println(base64Code);
			decoderBase64File(base64Code, "D:/findRes_find.zip");
			//toFile(base64Code, "D:\\three.txt");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
