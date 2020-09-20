package com.wyj.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyj.BaseTest;
import com.wyj.pojo.Area;

public class AreaDaoTest extends BaseTest {

	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void testQueryArea() {
		List<Area> areaList = areaDao.queryArea();
//		assertEquals(2, areaList.size());//
		System.out.println(areaList.size());
	}
	
}
