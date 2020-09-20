package com.wyj.dao;

import java.util.List;

import com.wyj.pojo.ProductImg;

public interface ProductImgDao {

	/**
	 * 批量增加详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	List<ProductImg> queryProductImgList(Long productId);
	
	int deleteProductImgByProducId(long productId);
	
}
