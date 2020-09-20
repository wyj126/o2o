package com.wyj.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyj.BaseTest;
import com.wyj.pojo.Area;
import com.wyj.pojo.PersonInfo;
import com.wyj.pojo.Shop;
import com.wyj.pojo.ShopCategory;

public class ShopDaoTest extends BaseTest{

	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		//shopCondition.setOwner(owner);
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(1L);
		shopCondition.setShopCategory(shopCategory);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 2);
		System.out.println(shopDao.queryShopCount(shopCondition));
		System.out.println(shopList.size());
	}
	
	@Test
	@Ignore
	public void testQueryByShopId() {
		Shop shop = shopDao.queryByShopId(1);
		System.out.println(shop.getArea().getAreaId());
		System.out.println(shop.getArea().getAreaName());
	}
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setOwner(owner);
		shop.setShopName("testshop");
		shop.setShopDesc("testshopdesc");
		shop.setShopAddr("testaddr");
		shop.setPhone("testphone");
		shop.setShopImg("testimg");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中"); 
		int effectNum = shopDao.insertShop(shop);
		System.out.println(effectNum);
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("更新测试描述002");
		shop.setShopAddr("测试地址002");
		shop.setLastEditTime(new Date());
		int effectNum = shopDao.updateShop(shop);
		System.out.println(effectNum);
	}
	
	
}
