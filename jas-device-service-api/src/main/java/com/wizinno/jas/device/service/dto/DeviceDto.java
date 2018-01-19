package com.wizinno.jas.device.service.dto;

import java.util.Date;

public class DeviceDto {
    private Long id;
    private Long userId;
    private String model;
    private String sn;
    private String codeUrl;
    private String config;
    private Integer lifeStatus;
    private Integer type;
    private Date produceDate;
    private Date activateDate;
    private Date scrapDate;
    private Date createTime;
    private Date updateTime;
    private String patientName;//病人姓名，前端传的字段
    private Integer status;//前端传的状态字段
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(Integer lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public Date getActivateDate() {
        return activateDate;
    }

    public void setActivateDate(Date activateDate) {
        this.activateDate = activateDate;
    }

    public Date getScrapDate() {
        return scrapDate;
    }

    public void setScrapDate(Date scrapDate) {
        this.scrapDate = scrapDate;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
