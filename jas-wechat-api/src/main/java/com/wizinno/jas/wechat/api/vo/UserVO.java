package com.wizinno.jas.wechat.api.vo;

import java.util.Date;

/**
 * Created by LiuMei on 2017-07-26.
 */
public class UserVO {

    private Long id;
    //用户标识
    private String openId;
    //真实姓名
    private String realName;
    //性别
    private Integer sex;
    //手机号码
    private String phone;
    //出生年月
    private Date birthDate;
    //积分值
    private Long scoreValue;
    //头像
    private String headPortrait;

    private Date createTime;

    private Date updateTime;

    private String Captcha;

    private Integer type;

    public String getCaptcha() {
        return Captcha;
    }

    public void setCaptcha(String captcha) {
        Captcha = captcha;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Long scoreValue) {
        this.scoreValue = scoreValue;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
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
