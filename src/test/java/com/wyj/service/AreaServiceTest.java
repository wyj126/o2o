package com.wyj.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyj.BaseTest;
import com.wyj.pojo.Area;

public class AreaServiceTest extends BaseTest {

	@Autowired
	private AreaService areaService;
	
	@Test
	public void testGetAreaList() {
		List<Area> areaList = areaService.getAreaList();
//		assertEquals("南京", areaList.get(0).getAreaName());
		System.out.println(areaList.get(0).getAreaName());
	}
	
}
