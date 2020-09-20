package com.wyj.pojo;

import java.util.Date;

public class LocalAuth {

	private long localAuth;
	
	private String username;
	
	private String password;
	
	private Date createTime;
	
	private Date lastEditTime;

	
	public long getLocalAuth() {
		return localAuth;
	}

	public void setLocalAuth(long localAuth) {
		this.localAuth = localAuth;
	}

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	
	
}
