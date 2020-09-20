package com.wyj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyj.dao.ProductCategoryDao;
import com.wyj.dto.ProductCategoryExecution;
import com.wyj.enums.ProductCategoryStateEnum;
import com.wyj.exception.ProductCategoryOperationException;
import com.wyj.pojo.ProductCategory;
import com.wyj.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Override
	public List<ProductCategory> queryProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectionNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectionNum <=0) {
					throw new ProductCategoryOperationException("商品类别添加失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			}catch (Exception e) {
				throw new ProductCategoryOperationException("batchInsertProductCategory error:"+e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		//TODO 此类别下的商品的商品类别Id设置为null
		try {
			int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (effectNum <=0) {
				throw new ProductCategoryOperationException("店铺类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory errMsg:"+e.getMessage());
		}
	}

}
