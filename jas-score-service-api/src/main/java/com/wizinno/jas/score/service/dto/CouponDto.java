package com.wizinno.jas.score.service.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Created by HP on 2017/8/14.
 */
public class CouponDto {

    private Long id;

    private String couponPassword;
    private int redeemScore;
    private int couponCount;
    private Double couponMoney;
    private Date validity;
    private Date createTime;
    private Date updateTime;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponPassword() {
        return couponPassword;
    }

    public void setCouponPassword(String couponPassword) {
        this.couponPassword = couponPassword;
    }

    public int getRedeemScore() {
        return redeemScore;
    }

    public void setRedeemScore(int redeemScore) {
        this.redeemScore = redeemScore;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public Double getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Double couponMoney) {
        NumberFormat nf = new DecimalFormat("#.0");
        nf.format(couponMoney);
        this.couponMoney = couponMoney;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
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
