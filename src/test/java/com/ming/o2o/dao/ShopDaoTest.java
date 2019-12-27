package com.ming.o2o.dao;

import com.ming.o2o.BaseTest;
import com.ming.o2o.dao.ShopDao;
import com.ming.o2o.entity.Area;
import com.ming.o2o.entity.PersonInfo;
import com.ming.o2o.entity.Shop;
import com.ming.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopList(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setOwnerId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表的大小" + shopList.size());
        System.out.println("店铺总数" + count);
        // 店铺是否可以通过排列组合的方式获取信息
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        shopCondition.setShopCategory(shopCategory);
        try{
            shopList = shopDao.queryShopList(shopCondition, 0 ,2);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("新店铺列表的大小：" + shopList.size());
        try{
            count = shopDao.queryShopCount(shopCondition);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("新店铺总数：" + count);
    }

    @Test
    @Ignore
    public void testCombineQueryShopList(){
        Shop shopCondition = new Shop();
        List<Shop> shopList;
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        shopCondition.setShopCategory(shopCategory);
        shopList = shopDao.queryShopList(shopCondition, 0 ,2);
        System.out.println("新店铺列表的大小：" + shopList.size());
        System.out.println("新店铺列表：" + shopList);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("新店铺总数：" + count);
    }

    @Test
    @Ignore
    public void testQueryByShopId(){
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId:" + shop.getArea().getAreaId());
        System.out.println("shopDesc:" + shop.getShopDesc());
        System.out.println("areaName:" + shop.getArea().getAreaName());
    }

    @Test
    @Ignore
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setOwnerId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺");
        shop.setShopDesc("测试店铺功能模块中");
        shop.setShopAddr("在你心中");
        shop.setPhone("18534902229");
        shop.setShopImg("test-null");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("测试更新模块，描述测试");
        shop.setShopAddr("测试更新模块，店铺测试");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void testQueryShopListAndCount() {
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        // 父类店铺 Id 为 1
        parentCategory.setShopCategoryId(1L);
        // 给子类别赋上父类 Id
        childCategory.setParent(parentCategory);
        // 给 shop 对象 shopCondition 的 shopCategory 赋值 childCategory
        shopCondition.setShopCategory(childCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表大小：" + shopList.size());
        System.out.println("店铺总数：" + count);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        shopCondition.setShopCategory(shopCategory);
        shopList = shopDao.queryShopList(shopCondition, 0, 2);
        System.out.println("新店铺列表的大小：" + shopList.size());
        count = shopDao.queryShopCount(shopCondition);
        System.out.println("新店铺总数：" + count);
    }
}
