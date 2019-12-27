package com.ming.o2o.entity;

import java.util.Date;

public class HeadLine {
    // ID
    private Long lineId;
    // 标题
    private String lineName;
    // 标题连接
    private String lineLink;
    // 标题图片
    private String lineImg;
    // 优先级
    private Integer priority;
    // 状态：0不可用 1可用
    private Integer enableStatus;
    // 创建时间
    private Date createTime;
    // 上次编辑时间
    private Date lastEditTime;

    public Long getLineID() {
        return lineId;
    }

    public void setLineID(Long lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineLink() {
        return lineLink;
    }

    public void setLineLink(String lineLink) {
        this.lineLink = lineLink;
    }

    public String getLineImg() {
        return lineImg;
    }

    public void setLineImg(String lineImg) {
        this.lineImg = lineImg;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
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
}
