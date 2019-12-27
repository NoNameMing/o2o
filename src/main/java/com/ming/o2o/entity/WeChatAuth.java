package com.ming.o2o.entity;

import java.util.Date;

public class WeChatAuth {
    // 微信ID
    private Long wechatId;
    // 用户ID
    private String openId;
    // 创建时间
    private Date createTime;
    // 个人信息
    private PersonInfo personInfo;

    public Long getWechatId() {
        return wechatId;
    }

    public void setWechatId(Long wechatId) {
        this.wechatId = wechatId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
