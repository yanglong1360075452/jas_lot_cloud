package com.wizinno.jas.archival.api.vo;

import java.util.Date;

/**
 * Created by HP on 2017/8/16.
 */
public class ClinicalDataVO {

    private Long id;

    private Long maId;  //病历档案id
    private int indexType;//图片类型索引
    private String content;  //图片描述

    private String picture;  //图片路径

    private Date time;  //图片时间

    private String type;  //图片标签

    private Date createTime;

    private Date updateTime;

    public int getIndexType() {
        return indexType;
    }

    public void setIndexType(int indexType) {
        this.indexType = indexType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaId() {
        return maId;
    }

    public void setMaId(Long maId) {
        this.maId = maId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
