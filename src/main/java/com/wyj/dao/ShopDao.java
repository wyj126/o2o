package com.wyj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wyj.pojo.Shop;

public interface ShopDao {

	/**
	 * 分页查询店铺
	 * @param 店铺名（模糊） 店铺状态 店铺类别 区域id owner 
	 * rowIndex 从第几行开始取数据
	 *  pageSize几条数据
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shop,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	/**
	 * 返回queryShopList 总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	/**
	 *通过shopiId查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	
	/**
	 * 新增店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);

	/**
	 * 更新店铺
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
	
}
