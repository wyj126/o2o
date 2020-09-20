package com.wyj.service;

import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wyj.dto.ShopExecution;
import com.wyj.exception.ShopOperationException;
import com.wyj.pojo.Shop;

public interface ShopService {

	/**
	 * 根据shopCondition返回相应列表数据
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	/**
	 * 获取店铺信息
	 */
	Shop queryByShopId(long shopId);
	
	/**
	 * 更新店铺信息 包括对图片的处理
	 */
	ShopExecution modifyShop(Shop shop,CommonsMultipartFile shopImg) throws ShopOperationException;
	
	/**
	 * 新增店铺
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 */
	ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg);
	
}
