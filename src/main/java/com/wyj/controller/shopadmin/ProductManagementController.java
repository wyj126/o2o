package com.wyj.controller.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock.Catch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyj.dto.ProductExecution;
import com.wyj.enums.ProductStateEnum;
import com.wyj.exception.ProductOperationException;
import com.wyj.pojo.Product;
import com.wyj.pojo.ProductCategory;
import com.wyj.pojo.Shop;
import com.wyj.service.ProductCategoryService;
import com.wyj.service.ProductService;
import com.wyj.util.CodeUtil;
import com.wyj.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

	@Autowired
	private ProductService  productService;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	//图片最大数量
	private static final int IMAGEMAXCOUNT = 6;
	
	@RequestMapping(value="/addProduct",method=RequestMethod.POST)
	@ResponseBody
	public Map<String ,Object> addProduct(HttpServletRequest request){
		Map<String ,Object> modelMap = new HashMap<>();
		//验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//接受前端传输的数据
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest mh = null;
		CommonsMultipartFile thumbNail = null;
		List<CommonsMultipartFile> productImgs = new ArrayList<>();
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			//若请求中存在文件流 则取出文件流
			if (commonsMultipartResolver.isMultipart(request)) {
				mh = (MultipartHttpServletRequest) request;
				//取出缩略图并构建对象
				thumbNail = (CommonsMultipartFile) mh.getFile("thumbNail");
				for (int i=0 ;i<IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile productImg = (CommonsMultipartFile)mh.getFile("productImg" + i);
					if (productImg != null) {
						productImgs.add(productImg);
					}
				}
			}else {
				modelMap.put("success",false);
				modelMap.put("errMsg","上传图片不能为null");
				return modelMap;
			}
		}catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		try {
			mapper.readValue(productStr, Product.class);
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (product != null && thumbNail != null && productImgs.size() > 0) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				ProductExecution pe = productService.addProduct(product, thumbNail, productImgs);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(ProductOperationException e){
				modelMap.put("success",false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请输入商品信息");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getProductById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String ,Object> getProductById(@RequestParam Long productId){
		Map<String ,Object> modelMap = new HashMap<>();
		if (productId > -1) {
			Product product = productService.getProductById(productId);
			List<ProductCategory> productCategoryList = productCategoryService.queryProductCategoryList(product.getShop().getShopId());
			modelMap.put("success", false);
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
		} else {
			modelMap.put("success",false);
			modelMap.put("errMsg","empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/modifyProduct",method=RequestMethod.GET)
	@ResponseBody
	public Map<String ,Object> modifyProduct(HttpServletRequest request){
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		Map<String ,Object> modelMap = new HashMap<>();
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success",false);
			modelMap.put("errMsg","验证码错误");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest mh = null;
		CommonsMultipartFile thumbNail = null;
		List<CommonsMultipartFile> productImgs = new ArrayList<>();
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			//若请求中存在文件流 则取出文件流
			if (commonsMultipartResolver.isMultipart(request)) {
				mh = (MultipartHttpServletRequest) request;
				//取出缩略图并构建对象
				thumbNail = (CommonsMultipartFile) mh.getFile("thumbNail");
				for (int i=0 ;i<IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile productImg = (CommonsMultipartFile)mh.getFile("productImg" + i);
					if (productImg != null) {
						productImgs.add(productImg);
					}
				}
			}else {
				modelMap.put("success",false);
				modelMap.put("errMsg","上传图片不能为null");
				return modelMap;
			}
		}catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		try {
			product = mapper.readValue(productStr, Product.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (product != null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				ProductExecution pe = productService.addProduct(product, thumbNail, productImgs);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(Exception e) {
				modelMap.put("success",false);
				modelMap.put("errMsg",e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请输入商品信息");
		}
		return modelMap;
	}
	
	
}
