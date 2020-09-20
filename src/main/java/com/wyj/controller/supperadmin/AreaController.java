package com.wyj.controller.supperadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wyj.pojo.Area;
import com.wyj.service.AreaService;

@Controller
@RequestMapping("/areaController")
public class AreaController {

	Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/listArea")
	@ResponseBody//转为json
	private Map<String,Object> listArea() {
		logger.info("===start====");
		long startTime = System.currentTimeMillis();
		Map<String, Object> modelMap = new HashMap<String,Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			modelMap.put("rows",list);
			modelMap.put("total",list.size());
		} catch (Exception e) {
			modelMap.put("susccess",false);
			modelMap.put("errMsg",e.toString());
		}
		logger.error("error!");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime:[{}ms]",endTime - startTime);
		logger.info("===end===");
		return modelMap;
	}
	
}
