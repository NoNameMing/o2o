package com.ming.o2o.service;

import com.ming.o2o.BaseTest;
import com.ming.o2o.dto.ImageHolder;
import com.ming.o2o.dto.ProductExecution;
import com.ming.o2o.entity.Product;
import com.ming.o2o.entity.ProductCategory;
import com.ming.o2o.entity.Shop;
import com.ming.o2o.enums.ProductStateEnum;
import com.ming.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    @Ignore
    public void testAddProduct() throws ShopOperationException, FileNotFoundException {
        // 创建 shopId 为 1 且 productCategoryId 为 1 的商品实例并给其成员变量赋值
        Product product = new Product();
        Shop shop = new Shop();
        ProductCategory productCategory = new ProductCategory();
        shop.setShopId(1L);
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品Service层功能");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        // 创建缩略图文件流
        File thumbnailFile = new File("/Users/wangxiaoming/Desktop/o2o/src/test/resources/hmbb.jpeg");
        InputStream inputStream = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), inputStream);
        // 创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1 = new File("/Users/wangxiaoming/Desktop/o2o/src/test/resources/hmbb.jpeg");
        InputStream inputStream1 = new FileInputStream(productImg1);
        File productImg2 = new File ("/Users/wangxiaoming/Desktop/o2o/src/test/resources/hmbb.jpeg");
        InputStream inputStream2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(), inputStream1));
        productImgList.add(new ImageHolder(productImg2.getName(), inputStream2));
        // 添加商品并验证
        ProductExecution productExecution = productService.addProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());

    }

    @Test
    public void testModifyProduct() throws ShopOperationException, FileNotFoundException {
        // 创建 shopId 为 1 且 productCategoryId 为 1 的商品实例并给其成员变量赋值
        Shop shop = new Shop();
        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();
        shop.setShopId(1L);
        productCategory.setProductCategoryId(1L);
        product.setProductId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("正式的商品（测试）");
        product.setProductDesc("正式的商品描述（测试）");
        // 创建缩略图文件流
        File thumbnailFile = new File("/Users/wangxiaoming/Desktop/o2o/src/test/resources/2000.jpg");
        InputStream inputStream = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), inputStream);
        // 创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1 = new File("/Users/wangxiaoming/Desktop/o2o/src/test/resources/2000.jpg");
        InputStream inputStream1 = new FileInputStream(productImg1);
        File productImg2 = new File("/Users/wangxiaoming/Desktop/o2o/src/test/resources/2000.jpg");
        InputStream inputStream2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(), inputStream1));
        productImgList.add(new ImageHolder(productImg2.getName(), inputStream2));
        // 添加商品并验证
        ProductExecution productExecution = productService.modifyProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
    }
}
