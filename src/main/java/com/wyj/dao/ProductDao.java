package com.wyj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wyj.pojo.Product;

public interface ProductDao {

	/**
	 * 插入商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	
	/**
	 * 分页查询商品
	 * @param productCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(
			@Param("prodictCondition")Product productCondition, @Param("rowIndex") int rowIndex, @Param("pageSize")int pageSize );
	
	/**
	 * 查询商品总数
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondiution")Product productCondition);
	
	/**
	 * 
	 * @param productId
	 * @return
	 */
	Product queryProductById(Long productId);
	
	/**
	 * 
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
	
	/**
	 * 删除商品类别之前，清除商品类别Id
	 * @param productCategoryId
	 * @return
	 */
	int updateProductCategoryToNull(long productCategoryId);
	
	/**
	 * 
	 * @param productId
	 * @param shopId
	 * @return
	 */
	int deleteProduct(@Param("productId")long productId,@Param("shopId")long shopId );
}
