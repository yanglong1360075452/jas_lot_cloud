package com.wizinno.jas.score.service.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Created by HP on 2017/8/14.
 */
public class ERcouponDto {

    private Long id;
    private Long userId;
    private Long couponId;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private int redeemScore;
    private Double couponMoney;
    private String redeemScores;
    private String couponMoneys;
    private String couponPassword;

    public String getCouponPassword() {
        return couponPassword;
    }

    public void setCouponPassword(String couponPassword) {
        this.couponPassword = couponPassword;
    }

    private Date validity;

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public int getRedeemScore() {
        return redeemScore;
    }

    public void setRedeemScore(int redeemScore) {
        this.redeemScore = redeemScore;
    }

    public Double getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Double couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getRedeemScores() {
        return redeemScores;
    }

    public void setRedeemScores(String redeemScores) {
        this.redeemScores = redeemScores;
    }

    public String getCouponMoneys() {
        return couponMoneys;
    }

    public void setCouponMoneys(String couponMoneys) {
        this.couponMoneys = couponMoneys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
