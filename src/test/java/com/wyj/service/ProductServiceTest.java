package com.wyj.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import org.junit.Test;

import com.wyj.BaseTest;
import com.wyj.enums.ProductStateEnum;
import com.wyj.pojo.Product;
import com.wyj.pojo.ProductCategory;
import com.wyj.pojo.Shop;

public class ProductServiceTest extends BaseTest {

	@Test
	public void testAProductService() throws Exception {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(10L);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		product.setProductName("测试商品名称001");
		product.setProductDesc("测试商品描述");
		product.setPriority(10);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		//创建缩略图文件流
		File thumbnailFile = new File("H:\\Desktop\\img\\1.jpg");
		FileInputStream fileInputStream = new FileInputStream(thumbnailFile);
	}
	
}
