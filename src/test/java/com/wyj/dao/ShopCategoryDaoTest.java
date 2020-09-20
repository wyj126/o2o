package com.wyj.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyj.BaseTest;
import com.wyj.pojo.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest {

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testQueryShopCategory() {
		List<ShopCategory> scList = shopCategoryDao.queryShopCategory(new ShopCategory());
		System.out.println(scList.size());
		ShopCategory testCategory = new ShopCategory();
		ShopCategory testParentCategory = new ShopCategory();
		testParentCategory.setShopCategoryId(1L);
		testCategory.setParent(testParentCategory);
		List<ShopCategory> scList1 = shopCategoryDao.queryShopCategory(testCategory);
		System.out.println(scList1.get(0).getShopCategoryName());
	}
	
}
