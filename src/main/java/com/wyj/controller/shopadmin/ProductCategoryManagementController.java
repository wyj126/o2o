package com.wyj.controller.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wyj.dto.ProductCategoryExecution;
import com.wyj.dto.Result;
import com.wyj.enums.ProductCategoryStateEnum;
import com.wyj.exception.ProductCategoryOperationException;
import com.wyj.pojo.ProductCategory;
import com.wyj.pojo.Shop;
import com.wyj.service.ProductCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value="/removeProductCategory",method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeProductCategory(HttpServletRequest request,Long productCategoryId){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if (productCategoryId != null && productCategoryId > 0) {
			try {
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				ProductCategoryExecution dpe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if (dpe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success",true);
				}else {
					modelMap.put("success",false);
					modelMap.put("errMsg",dpe.getStateInfo());
				}
			}catch (ProductCategoryOperationException e) {
				modelMap.put("success",false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请至少选择一个商品类型");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/addProductCategorys",method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		for (ProductCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution pcExecution = productCategoryService
						.batchInsertProductCategory(productCategoryList);
				if (pcExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					System.out.println(productCategoryList.size());
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pcExecution.getStateInfo());
				} 
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品类型少于1");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getProductCategoryList",method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> pcList = null;
		System.out.println(currentShop.getShopId());
		if (currentShop != null && currentShop.getShopId()>0) {
			pcList = productCategoryService.queryProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true,pcList);
		}else {
			return null;
		}
	}
	
}
