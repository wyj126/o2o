package com.wyj.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyj.BaseTest;
import com.wyj.dto.ShopExecution;
import com.wyj.enums.ShopStateEnum;
import com.wyj.exception.ShopOperationException;
import com.wyj.pojo.Area;
import com.wyj.pojo.PersonInfo;
import com.wyj.pojo.Shop;
import com.wyj.pojo.ShopCategory;

public class ShopServiceTest extends BaseTest {

	@Autowired
	private ShopService shopService;

	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(1L);
		shopCondition.setShopCategory(shopCategory);
		ShopExecution se = shopService.getShopList(shopCondition, 2, 4);//pageIndex 所以是1
		System.out.println(se.getShopList().size());
		System.out.println(se.getCount());
	}
	
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException, FileNotFoundException {
		Shop shop = new Shop();
		shop.setShopId(1);
		shop.setShopName("修改后的名字");
		File file = new File("H:\\Desktop\\新建文件夹\\1.jpg");
		InputStream is = new FileInputStream(file);
//		ShopExecution shopExecution = shopService.modifyShop(shop,);
//		System.out.println(shopExecution.getShop().getShopImg());
	}

	@Test
	@Ignore
	public void testShopService() throws FileNotFoundException {
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
		shop.setShopName("testshop-3");
		shop.setShopDesc("testshopdesc-3");
		shop.setShopAddr("testaddr-3");
		shop.setPhone("testphone-3");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg = new File("H:\\img\\微信图片1.png");
//		ShopExecution se = shopService.addShop(shop,shopImg );
//		System.out.println(se.getState());
	}

}
