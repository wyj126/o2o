package com.wyj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyj.dao.ShopCategoryDao;
import com.wyj.pojo.ShopCategory;
import com.wyj.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

	
	
}
