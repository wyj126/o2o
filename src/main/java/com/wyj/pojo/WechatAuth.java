package com.wyj.pojo;

import java.util.Date;

public class WechatAuth {

	private long wechatAuthId;
	
	private String openId;
	
	private Date createTime;
	
	private PersonInfo personInfo;

	public long getWechatAuthId() {
		return wechatAuthId;
	}

	public void setWechatAuthId(long wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
	
}
