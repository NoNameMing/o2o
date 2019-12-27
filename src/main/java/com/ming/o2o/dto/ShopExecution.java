package com.ming.o2o.dto;

import com.ming.o2o.entity.Shop;
import com.ming.o2o.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution {
    // 结果状态
    private int state;

    // 状态标记
    private String stateInfo;

    // 店铺数量
    private int count;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    // 操作的 shop （增删改查店铺时用到）
    private Shop shop;

    // shop 列表 （查询店铺列表时用）
    private List<Shop> shopList;

    public ShopExecution(){

    }

    // 店铺操作失败时的构造器
    public ShopExecution(ShopStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 店铺操作成功时的构造器 --- 返回单个 Shop
    public ShopExecution(ShopStateEnum stateEnum, Shop shop){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    // 店铺操作成功时的构造器 --- 返回 Shop 列表
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }


}
