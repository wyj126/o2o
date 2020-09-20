package com.wyj.dao;

import java.util.List;

import com.wyj.pojo.Area;

public interface AreaDao {
	
	/**
	 * 列出地域列表
	 * @return
	 */
	List<Area> queryArea();
	
}
