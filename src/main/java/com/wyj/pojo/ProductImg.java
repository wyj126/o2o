package com.wyj.pojo;

import java.util.Date;

public class ProductImg {

	private long productImg;
	
	private String imgAddr;
	
	private String imgDesc;
	
	private Integer priority;
	
	private Date createTime;
	
	private long productId;

	
	
	public long getProductImg() {
		return productImg;
	}

	public void setProductImg(long productImg) {
		this.productImg = productImg;
	}

	public String getImgAddr() {
		return imgAddr;
	}

	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}

	public String getImgDesc() {
		return imgDesc;
	}

	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
	
}
