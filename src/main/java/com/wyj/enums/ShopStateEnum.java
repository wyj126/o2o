package com.wyj.enums;

public enum ShopStateEnum {

	CHECK(0, "审核中"), OFFINE(-1, "非法店铺"), SUCCESS(1, "操作成功"), PASS(2, "认证通过"), INNER_ERROR(-1001, "内部系统错误"),
	NULL_SHOPID(-1002, "shopid为null"),NULL_SHOP(-1003,"shop信息为空");

	private int state;

	private String stateInfo;

	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	/*
	 * 依据传入的state返回相应的enum值
	 */
	public static ShopStateEnum stateOf(int state) {
		for (ShopStateEnum stateEunm : values()) {
			if (stateEunm.getState() == state) {
				return stateEunm;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
