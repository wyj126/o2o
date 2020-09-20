package com.wyj.util;

public class PathUtil {

	public static String separator = System.getProperty("file.separator");

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "H:/projectdev/image/";
		} else {
			basePath = "/home/image/";
		}
		basePath = basePath.replace("/", separator);
		return basePath;
	}

	public static String getShopImgPath(long shopId) {
		String imagePath = "upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", separator);
	}

	public static void main(String[] args) {
		System.out.println(PathUtil.getShopImgPath(15));
	}
	
}
