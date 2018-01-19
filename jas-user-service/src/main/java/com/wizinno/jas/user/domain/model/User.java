package com.wizinno.jas.user.domain.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.open_id
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.real_name
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private String realName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.sex
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private Integer sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.phone
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.birth_date
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private Date birthDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.score_value
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private int scoreValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.head_portrait
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private String headPortrait;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.create_time
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.update_time
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.open_id
     *
     * @return the value of user.open_id
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.open_id
     *
     * @param openId the value for user.open_id
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.real_name
     *
     * @return the value of user.real_name
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.real_name
     *
     * @param realName the value for user.real_name
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.sex
     *
     * @return the value of user.sex
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.sex
     *
     * @param sex the value for user.sex
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.phone
     *
     * @return the value of user.phone
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.phone
     *
     * @param phone the value for user.phone
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.birth_date
     *
     * @return the value of user.birth_date
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.birth_date
     *
     * @param birthDate the value for user.birth_date
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.score_value
     *
     * @return the value of user.score_value
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public int getScoreValue() {
        return scoreValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.score_value
     *
     * @param scoreValue the value for user.score_value
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.head_portrait
     *
     * @return the value of user.head_portrait
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public String getHeadPortrait() {
        return headPortrait;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.head_portrait
     *
     * @param headPortrait the value for user.head_portrait
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait == null ? null : headPortrait.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.create_time
     *
     * @return the value of user.create_time
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.create_time
     *
     * @param createTime the value for user.create_time
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.update_time
     *
     * @return the value of user.update_time
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.update_time
     *
     * @param updateTime the value for user.update_time
     *
     * @mbggenerated Fri Aug 11 10:39:13 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}