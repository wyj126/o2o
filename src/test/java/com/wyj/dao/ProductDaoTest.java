package com.wyj.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyj.BaseTest;
import com.wyj.pojo.Product;
import com.wyj.pojo.ProductCategory;
import com.wyj.pojo.Shop;

public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testAinsertProduct() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(10L);
		Product product = new Product();
		product.setProductName("测试1");
		product.setProductDesc("测试描述1");
		product.setImgAddr("test_Addr");
		product.setPriority(1);
		product.setEnableStatus(0);
		product.setCreateTime(new Date());
		product.setLastEditTime(new Date());
		product.setShop(shop);
		product.setProductCategory(productCategory);
		Product product2 = new Product();
		product2.setProductName("测试2");
		product2.setProductDesc("测试描述2");
		product2.setImgAddr("test_Addr2");
		product2.setPriority(1);
		product2.setEnableStatus(0);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop);
		product2.setProductCategory(productCategory);
		int effectedNum1 = productDao.insertProduct(product);
		int effectedNum2 = productDao.insertProduct(product2);
		System.out.println(effectedNum1+","+effectedNum2);
	}
	
}
