package com.wyj.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wyj.dao.ShopDao;
import com.wyj.dto.ShopExecution;
import com.wyj.enums.ShopStateEnum;
import com.wyj.exception.ShopOperationException;
import com.wyj.pojo.Shop;
import com.wyj.service.ShopService;
import com.wyj.util.ImageUtil;
import com.wyj.util.PageCalculator;
import com.wyj.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
		//空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//给店铺赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum<=0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				if (shopImg != null) {
					//存储图片
					try {
						addShopImg(shop, shopImg);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:"+e.getMessage());
					}
					//更新店铺图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum<=0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		}catch (Exception e) {
			throw new ShopOperationException("addShop error:"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
		//获取shop图片目录的相对路径
		String dest = PathUtil.getShopImgPath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImg,dest);
		System.out.println(shopImgAddr);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop queryByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg )
			throws ShopOperationException {
		try {
			if (shop == null || shop.getShopId() <= 0) {
				return new ShopExecution(ShopStateEnum.NULL_SHOP);
			} else {
				//1.判断是否需要处理图片
				if (shopImg != null) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop, shopImg);
				}
				//2 更新店铺信息
				shop.setLastEditTime(new Date());
				int effectNum = shopDao.updateShop(shop);
			
				if (effectNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS,shop);
				}
			} 
		} catch (Exception e) {
			throw new ShopOperationException("modifyShop error:"+e.getMessage());
		}
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculaterRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution shopExecution = new ShopExecution();
		if (shopList != null) {
			shopExecution.setShopList(shopList);
			shopExecution.setCount(count);
		}else {
			shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return shopExecution;
	}

	
	
}
