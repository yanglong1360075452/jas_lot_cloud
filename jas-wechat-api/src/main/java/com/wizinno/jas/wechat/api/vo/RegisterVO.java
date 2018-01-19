package com.wizinno.jas.wechat.api.vo;

/**
 * Created by HP on 2017/8/11.
 */
public class RegisterVO {
    //用户标识
    private String openId;
    //真实姓名
    private String realName;

    //手机号码
    private String phone;

    private String captcha;

    private Integer type;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
