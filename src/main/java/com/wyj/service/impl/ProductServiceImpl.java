package com.wyj.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wyj.dao.ProductDao;
import com.wyj.dao.ProductImgDao;
import com.wyj.dto.ProductExecution;
import com.wyj.enums.ProductStateEnum;
import com.wyj.exception.ProductOperationException;
import com.wyj.pojo.Product;
import com.wyj.pojo.ProductImg;
import com.wyj.service.ProductService;
import com.wyj.util.FileUtil;
import com.wyj.util.ImageUtil;
import com.wyj.util.PageCalculator;
import com.wyj.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImgDao productImgDao;

	//1. 处理缩略图，获取缩略图相对路径并赋值product
	//2. 往t_product写入商品信息，获取productId
	//3. 结合productId批量处理商品详情图
	//4. 将商品详情图列表批量插入t_product_img中

	@Override
	@Transactional
	public ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail,
			List<CommonsMultipartFile> productImgList) {
		if (product != null && product.getShop() != null && product.getShop().getShopId() >0) {
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认上架
			product.setEnableStatus(1);
			//判断缩略图是否为null
			if (thumbnail != null) {
				addThumibnail(product, thumbnail);
			}
			try {
				//创建商品
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <=0) {
					throw new ProductOperationException("创建商品失败");
				}
			}catch (Exception e) {
				throw new ProductOperationException("创建商品失败 error:"+e.getMessage());
			}
			//判断详细图为不为null
			if (productImgList != null && productImgList.size() > 0) {
				addProductImgList(product,productImgList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else {
			//传参为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	
	//详情图
	private void addProductImgList(Product product, List<CommonsMultipartFile> productImgList) {
		//获取图片存储路径，这里直接存放到相应店铺文件夹下
		String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
		List<ProductImg> piList = new ArrayList<ProductImg>();
		//遍历图片处理 ， 并且添加productImg实体类里
		for(CommonsMultipartFile thumbnail : productImgList) {
			String imgAddr = ImageUtil.generateNormalImgs(thumbnail, dest);
			ProductImg productImg = new ProductImg();
			productImg.setCreateTime(new Date());
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			piList.add(productImg);
		}
		if (piList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(piList);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图片失败:" + e.getMessage());
			} 
		}
	}

	//缩略图
	private void addThumibnail(Product product, CommonsMultipartFile thumbnail) {
		String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculaterRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	public ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
		List<CommonsMultipartFile> productImgs) throws RuntimeException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() > 0) {
			product.setLastEditTime(new Date());
			if (thumbnail != null) {
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					FileUtil.deleteFile(tempProduct.getImgAddr());
				}
				addThumibnail(product, thumbnail);
			}
		}
		if (productImgs != null && productImgs.size() > 0) {
			deleteProductImgs(product.getProductId());
			addProductImgList(product, productImgs);
		}
		try {
			int effectedNum = productDao.updateProduct(product);
		}catch(Exception e) {
			
		}
		return null;
	}

	private void deleteProductImgs(long productId) {
		List<ProductImg> proImgList = productImgDao.queryProductImgList(productId);
		for (ProductImg proImg : proImgList) {
			FileUtil.deleteFile(proImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProducId(productId);
	}
	
	
	
}
