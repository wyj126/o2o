package com.wyj.controller.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyj.dto.ShopExecution;
import com.wyj.enums.ShopStateEnum;
import com.wyj.pojo.Area;
import com.wyj.pojo.PersonInfo;
import com.wyj.pojo.Shop;
import com.wyj.pojo.ShopCategory;
import com.wyj.service.AreaService;
import com.wyj.service.ShopCategoryService;
import com.wyj.service.ShopService;
import com.wyj.util.CodeUtil;
import com.wyj.util.HttpServletRequestUtil;
import com.wyj.util.ImageUtil;
import com.wyj.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaSerivce;

	@RequestMapping(value="/getManagementInfo",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getManagementInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId <=0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				modelMap.put("redirect",true);
				modelMap.put("url","/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop)currentShopObj; 
				modelMap.put("redirect",false);
				modelMap.put("shopId",currentShop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect",false);
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getShopList",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setUserName("test");
		request.getSession().setAttribute("user", user);
		user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList",se.getShopList());
			modelMap.put("user",user);
			modelMap.put("success", true);
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/getShopById", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopService.queryByShopId(shopId);
				List<Area> areaList = areaSerivce.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;

	}

	@RequestMapping(value = "/modifyShop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 1.接收并转化相应参数，shop shopimg
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");// 接收shopImg
		}
		// 2.注册店铺
		if (shop != null && shop.getShopId() >= 0) {
//			File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
//			try {
//				inputStreamToFile(shopImg.getInputStream(),shopImgFile);
//			} catch (IOException e) {
//				modelMap.put("success",false);
//				modelMap.put("errMsg",e.getMessage());
//				return modelMap;
//			}
			ShopExecution seAddShop;
			System.out.println(shopImg+"***************");
			shop.setOwner((PersonInfo)request.getSession().getAttribute("user"));
			try {
				if (shopImg == null) {
					seAddShop = shopService.addShop(shop, null);
				} else {
					seAddShop = shopService.addShop(shop, shopImg);
				}
				System.out.println(seAddShop.getState());
				if (seAddShop.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", seAddShop.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "店铺id不足");
			return modelMap;
		}
	}

	@RequestMapping(value = "/getShopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopinitinfo() {
		Map<String, Object> modelMap = new HashMap<>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaSerivce.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}

	@RequestMapping(value = "/registerShop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		// 1.接收并转化相应参数，shop shopimg
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");// 接收shopImg
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		// 2.注册店铺
		if (shop != null && shopImg != null) {
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
//			File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
//			try {
//				inputStreamToFile(shopImg.getInputStream(),shopImgFile);
//			} catch (IOException e) {
//				modelMap.put("success",false);
//				modelMap.put("errMsg",e.getMessage());
//				return modelMap;
//			}
			ShopExecution seAddShop;
			try {
				seAddShop = shopService.addShop(shop, shopImg);
				if (seAddShop.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					// 该用户可操作的用户列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if (shopList == null || shopList.size() == 0) {
						shopList = new ArrayList<Shop>();
					}
					shopList.add(seAddShop.getShop());
					request.getSession().setAttribute("shopList", shopList);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", seAddShop.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "店铺信息不足");
			return modelMap;
		}
	}

//	private static void inputStreamToFile(InputStream ins,File file) {
//		FileOutputStream os = null;
//		try {
//			os = new FileOutputStream(file);
//			int byteReader = 0;
//			byte[] b = new byte[1024];
//			while((byteReader = ins.read(b)) != -1) {
//				os.write(b,0,byteReader);
//			}
//			
//		}catch (Exception e) {
//			throw new RuntimeException("调用inputStreamToFile出现异常:"+e.getMessage());
//		}finally {
//			try {
//				if (os != null) {
//					os.close();
//				}
//				if (ins != null) {
//					ins.close();
//				}
//			}catch (Exception e) {
//				throw new RuntimeException("inputStreamToFile关闭io出现异常:"+e.getMessage());
//			}
//		}
//	}

}
