package com.wizinno.jas.device.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Device implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.id
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.user_id
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.model
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private String model;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.sn
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private String sn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.code_Url
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private String codeUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.config
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private String config;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.life_status
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private Integer lifeStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.type
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.produce_date
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private Date produceDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.activate_date
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private Date activateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.scrap_date
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private Date scrapDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.create_time
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.update_time
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table device
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.id
     *
     * @return the value of device.id
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.id
     *
     * @param id the value for device.id
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.user_id
     *
     * @return the value of device.user_id
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.user_id
     *
     * @param userId the value for device.user_id
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.model
     *
     * @return the value of device.model
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public String getModel() {
        return model;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.model
     *
     * @param model the value for device.model
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.sn
     *
     * @return the value of device.sn
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public String getSn() {
        return sn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.sn
     *
     * @param sn the value for device.sn
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.code_Url
     *
     * @return the value of device.code_Url
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public String getCodeUrl() {
        return codeUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.code_Url
     *
     * @param codeUrl the value for device.code_Url
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl == null ? null : codeUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.config
     *
     * @return the value of device.config
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public String getConfig() {
        return config;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.config
     *
     * @param config the value for device.config
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.life_status
     *
     * @return the value of device.life_status
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public Integer getLifeStatus() {
        return lifeStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.life_status
     *
     * @param lifeStatus the value for device.life_status
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setLifeStatus(Integer lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.type
     *
     * @return the value of device.type
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.type
     *
     * @param type the value for device.type
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.produce_date
     *
     * @return the value of device.produce_date
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public Date getProduceDate() {
        return produceDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.produce_date
     *
     * @param produceDate the value for device.produce_date
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.activate_date
     *
     * @return the value of device.activate_date
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public Date getActivateDate() {
        return activateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.activate_date
     *
     * @param activateDate the value for device.activate_date
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setActivateDate(Date activateDate) {
        this.activateDate = activateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.scrap_date
     *
     * @return the value of device.scrap_date
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public Date getScrapDate() {
        return scrapDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.scrap_date
     *
     * @param scrapDate the value for device.scrap_date
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setScrapDate(Date scrapDate) {
        this.scrapDate = scrapDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.create_time
     *
     * @return the value of device.create_time
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.create_time
     *
     * @param createTime the value for device.create_time
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.update_time
     *
     * @return the value of device.update_time
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.update_time
     *
     * @param updateTime the value for device.update_time
     *
     * @mbggenerated Sat Aug 26 14:16:48 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}