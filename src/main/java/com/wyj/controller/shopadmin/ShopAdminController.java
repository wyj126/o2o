package com.wyj.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/shopadmin",method = RequestMethod.GET)
public class ShopAdminController {

	@RequestMapping("/shopedit")
	public String ShopEdit() {
		return "shop/shopedit";
	}
	
	@RequestMapping("/shoplist")
	public String ShopList() {
		return "shop/shoplist";
	}
	
	@RequestMapping("/shopmanagement")
	public String shopManagement() {
		return "shop/shopmanagement";
	}
	
	@RequestMapping("/productcategorymanage")
	public String productCategoryManage() {
		return "shop/productcategorymanage";
	}
	
	@RequestMapping("/productedit")
	public String productEdit() {
		return "shop/addProduct";
	}
	
	@RequestMapping("/productmanage")
	public String productManage() {
		return "shop/productmanage";
	}
	
}
