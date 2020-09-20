package com.wyj.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ch.qos.logback.classic.Logger;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final Random r = new Random();
	
	public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
	
		String extension = getFileExtension(thumbnail);
		
		makeDirPath(targetAddr);
	
		String relativeAddr = targetAddr + realFileName + extension;
		
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			//注意watermark文件路径
//			Thumbnails.of(thumbnail.getInputStream()).size(250,250)
//					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
//					.outputQuality(0.8f).toFile(dest);
			Thumbnails.of(thumbnail.getInputStream()).size(200, 200).outputQuality(0.25f).toFile(dest);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(relativeAddr);
		return relativeAddr;
	}
	/**
	 * 处理详情图
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImgs(CommonsMultipartFile thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
		
		String extension = getFileExtension(thumbnail);
		
		makeDirPath(targetAddr);
		
		String relativeAddr = targetAddr + realFileName + extension;
		
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			//注意watermark文件路径
//			Thumbnails.of(thumbnail.getInputStream()).size(337,640)
//			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
//			.outputQuality(0.9f).toFile(dest);
			Thumbnails.of(thumbnail.getInputStream()).size(337, 640).outputQuality(0.5f).toFile(dest);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(relativeAddr);
		return relativeAddr;
	}

	/**
	 * 创建目标路径所涉及到的目录 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		
		File dir = new File(realFileParentPath);
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(CommonsMultipartFile thumbnail) {
		String originalFilename = thumbnail.getOriginalFilename();
		return originalFilename.substring(originalFilename.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名 当前时间+五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		//获取随机数
		int rannum = r.nextInt(89999) + 10000;
		String format = sdf.format(new Date());
		return format + rannum;
	}

	/**
	 * 删除图片
	 * 判断path是目录还是文件并删除
	 */
	public static void deleteFileOrPath(String path) {
		File fileOrPath = new File(PathUtil.getImgBasePath()+path);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for (int i=0;i<files.length;i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
//	public static void main(String[] args) {
//		String shopImgAddr = ImageUtil.generateThumbnail(,"upload\\item\\shop\\19\\");
//	}

}
