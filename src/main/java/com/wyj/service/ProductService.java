package com.wyj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wyj.dto.ProductExecution;
import com.wyj.pojo.Product;

@Service
public interface ProductService {

	ProductExecution addProduct(Product product,CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs);
	
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	Product getProductById(long productId);
	
	ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,List<CommonsMultipartFile> productImgs) throws RuntimeException;
}
