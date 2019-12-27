package com.ming.o2o.service;

import com.ming.o2o.BaseTest;
import com.ming.o2o.dto.ImageHolder;
import com.ming.o2o.dto.ShopExecution;
import com.ming.o2o.entity.Area;
import com.ming.o2o.entity.PersonInfo;
import com.ming.o2o.entity.Shop;
import com.ming.o2o.entity.ShopCategory;
import com.ming.o2o.enums.ShopStateEnum;
import com.ming.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        shopCondition.setShopCategory(shopCategory);
        ShopExecution shopExecution = shopService.getShopList(shopCondition, 1, 1);
        System.out.println("店铺列表数：" + shopExecution.getShopList().size());
        System.out.println("店铺列表的大小：" + shopExecution.getCount());
    }

    @Test
    @Ignore
    public void testModifyShop() throws ShopOperationException, FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("测试修改店铺信息");
        File shopImg= new File("/Users/wangxiaoming/Desktop/o2o/src/test/resources/hmbb.jpeg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder("hmbb.jpeg", is);
        ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
        System.out.println("新的图片地址：" + shopExecution.getShop().getShopImg());
    }

    @Test
    @Ignore
    public void testAddShop(){
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
        shop.setShopName("更新测试Service店铺");
        shop.setShopDesc("测试Service店铺功能模块中");
        shop.setShopAddr("测试Service店铺地址");
        shop.setPhone("18534902229");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImgTest = new File("/Users/wangxiaoming/Desktop/o2o/src/test/resources/hmbb.jpeg");
        InputStream is = null;
        try {
            is = new FileInputStream(shopImgTest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageHolder imageHolder = new ImageHolder("hmbb.jpeg", is);
        ShopExecution se = shopService.addShop(shop, imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());

    }
}
