package com.wyj.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyj.BaseTest;
import com.wyj.pojo.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)//testABC
public class ProductCategoryDaoTest extends BaseTest {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	public void testDeleteProductCategory() {
		long shopId = 1;
		List<ProductCategory> PCList = productCategoryDao.queryProductCategoryList(shopId);
		for (ProductCategory pc : PCList) {
			int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
			System.out.println(effectedNum);
		}
	}
	
	@Test
	@Ignore
	public void testBatchInsertProductCategory() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("测试产品类别002");
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(1L);
		productCategory.setPriority(2);
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("测试产品类别003");
		productCategory2.setCreateTime(new Date());
		productCategory2.setShopId(1L);
		productCategory2.setPriority(3);
		List<ProductCategory> pcList= new ArrayList<ProductCategory>();
		pcList.add(productCategory);
		pcList.add(productCategory2);
		int count = productCategoryDao.batchInsertProductCategory(pcList);
		System.out.println(count);
	}
	
	@Test
	@Ignore
	public void testQueryProductCategoryList() {
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(1L);
		System.out.println(productCategoryList.size());
	}
}
