package com.wyj.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.wyj.dto.ProductCategoryExecution;
import com.wyj.exception.ProductCategoryOperationException;
import com.wyj.pojo.ProductCategory;

@Service
public interface ProductCategoryService {

	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)throws ProductCategoryOperationException;
	
	ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)throws ProductCategoryOperationException;

	/**
	 * 查询某店铺下所有商品信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);

}
