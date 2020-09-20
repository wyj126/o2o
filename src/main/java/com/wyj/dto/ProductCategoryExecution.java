package com.wyj.dto;

import java.util.List;

import com.wyj.enums.ProductCategoryStateEnum;
import com.wyj.pojo.ProductCategory;

public class ProductCategoryExecution {

	//结束状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	private List<ProductCategory> productCategoryList;
	
	public ProductCategoryExecution( ) {
	}
	
	//失败时
	public ProductCategoryExecution(ProductCategoryStateEnum productCategoryEnum) {
		this.state = productCategoryEnum.getState();
		this.stateInfo = productCategoryEnum.getStateInfo();
	}
	
	//成功时
	public ProductCategoryExecution(ProductCategoryStateEnum productCategoryEnum,List<ProductCategory> productCategoryList) {
		this.state = productCategoryEnum.getState();
		this.stateInfo = productCategoryEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
}
