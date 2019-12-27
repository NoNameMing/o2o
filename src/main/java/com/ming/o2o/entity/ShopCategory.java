package com.ming.o2o.entity;

import java.util.Date;

public class ShopCategory {
    // 商品类别ID
    private Long shopCategoryId;
    // 商品类名
    private String shopCategoryName;
    // 商品描述
    private String shopCategoryDesc;
    // 商品类别图片
    private String shopCategoryImg;
    // 商品优先级
    private Integer priority;
    // 创建时间
    private Date createTime;
    // 上次编辑时间
    private Date lastEditTime;
    // 上级ID
    private ShopCategory parent;

    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    public String getShopCategoryDesc() {
        return shopCategoryDesc;
    }

    public void setShopCategoryDesc(String shopCategoryDesc) {
        this.shopCategoryDesc = shopCategoryDesc;
    }

    public String getShopCategoryImg() { return shopCategoryImg; }

    public void setShopCategoryImg(String shopCategoryImg) { this.shopCategoryImg = shopCategoryImg; }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public ShopCategory getParent() {
        return parent;
    }

    public void setParent(ShopCategory parent) {
        this.parent = parent;
    }
}
