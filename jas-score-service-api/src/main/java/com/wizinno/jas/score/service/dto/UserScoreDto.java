package com.wizinno.jas.score.service.dto;

import java.util.Date;

/**
 * Created by HP on 2017/8/14.
 */
public class UserScoreDto {

    private Long id;
    private String userName;
    private String type;
    private Integer scoreChange;
    private String changeValue;
    private Integer scoreValue;
    private Date createTime;
    private Date updateTime;
    private Date startTime;
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getScoreChange() {
        return scoreChange;
    }

    public void setScoreChange(Integer scoreChange) {
        if(scoreChange==1){
            this.type = "注册";
        }else if(scoreChange==2){
            this.type = "兑换";
        }else if(scoreChange==3){
            this.type = "锻炼";
        }else if(scoreChange==4){
            this.type = "分享";
        }
    }

    public String getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(String changeValue) {
        this.changeValue = changeValue;
    }

    public Integer getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Integer scoreValue) {
        this.scoreValue = scoreValue;
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
