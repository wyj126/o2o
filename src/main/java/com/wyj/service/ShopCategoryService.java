package com.wyj.service;

import java.util.List;

import com.wyj.pojo.ShopCategory;

public interface ShopCategoryService {

	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
	
}
