package com.wyj.util;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtil {

	private static String seperator = System.getProperty("file.sperator");
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final Random r = new Random();
	
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "H:/projectdev/image/";
		} else {
			basePath = "/home/image/";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	
	public static String getHandLineImagePath() {
		String handLineImagePath = "upload/image/item/headtitle/";
		handLineImagePath = handLineImagePath.replace("/", seperator);
		return handLineImagePath;
	}
	
	public static String getShopCategoryImagePath() {
		String shopCategoryImagePath = "upload/image/item/shopCategory/";
		shopCategoryImagePath = shopCategoryImagePath.replace("/", seperator);
		return shopCategoryImagePath;
	}
	
	public static String getPersonInfoImagePath() {
		String personInfoImagePath = "upload/image/item/personInfo/";
		personInfoImagePath = personInfoImagePath.replace("/", seperator);
		return personInfoImagePath;
	}
	
	public static String getShopImagePath(long shopId) {
		StringBuilder shopImgerBuilder = new StringBuilder();
		shopImgerBuilder.append("upload/image/item/shop/");
		shopImgerBuilder.append(shopId);
		shopImgerBuilder.append("/");
		String shopImgPath = shopImgerBuilder.toString().replace("/", seperator);
		return shopImgPath;
	}
	
	public static String getRandomFileName() {
		int rannum = (int) ((r.nextDouble() * (99999-10000+1)) + 10000);
		String nowTime = sdf.format(new Date());
		return nowTime + rannum;
	}
	
	public static void deleteFile(String storePath) {
		File file = new File(PathUtil.getImgBasePath() + storePath);
		if (file.exists()) {
			if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i= 0;i<files.length;i++) {
					files[i].delete();
				}
			}
			file.delete();
		}
	}
	
	
}
