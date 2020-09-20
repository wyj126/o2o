package com.wyj.dto;

import java.util.List;

import com.wyj.enums.ShopStateEnum;
import com.wyj.pojo.Shop;

public class ShopExecution {

	//结果状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//店铺数量
	private int count;
	
	//操作店铺
	private Shop shop;
	
	//shop列表
	private List<Shop> shopList;
	
	public ShopExecution() {
		
	}
	
	//店铺操作失败时启动
	public ShopExecution(ShopStateEnum sse) {
		this.state = sse.getState();
		this.stateInfo = sse.getStateInfo();
	}
	
	//店铺操作成功时启动
	public ShopExecution(ShopStateEnum sse,Shop shop) {
		this.state = sse.getState();
		this.stateInfo = sse.getStateInfo();
		this.shop = shop;
	}
	
	//店铺操作成功时启动
	public ShopExecution(ShopStateEnum sse,List<Shop> shopList) {
		this.state = sse.getState();
		this.stateInfo = sse.getStateInfo();
		this.shopList = shopList;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
}
