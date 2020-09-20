package com.wyj.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyj.BaseTest;
import com.wyj.pojo.ProductImg;

public class ProductImgDaoTest extends BaseTest {

	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	public void testABatchInsertProductImg() {
		ProductImg productImg = new ProductImg();
		productImg.setImgAddr("pic1");
		productImg.setImgDesc("测试pro_img1");
		productImg.setPriority(1);
		productImg.setCreateTime(new Date());
		productImg.setProductId(3L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("pic2");
		productImg2.setImgDesc("测试pro_img2");
		productImg2.setPriority(2);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(3L);
		
		List<ProductImg> proimgList = new ArrayList<ProductImg>();
		proimgList.add(productImg);
		proimgList.add(productImg2);
		
		int effectedNum = productImgDao.batchInsertProductImg(proimgList);
		System.out.println(effectedNum);
	}
	
	
}
