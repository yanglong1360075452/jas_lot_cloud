package com.wizinno.jas.score.service.dto;

import java.util.Date;

/**
 * Created by HP on 2017/8/14.
 */
public class ScoreDto {

    private Long id;
    private String ico;
    private String type;
    private Long userId;
    private Integer scoreChange;
    private String changeValue;
    private Long surplusScore;
    private Date createTime;
    private Date updateTime;

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getType() {
        return type;
    }

    public void setType(Integer scoreChange) {
        if(scoreChange==1){
            this.type = "注册";
            this.ico = "icon-rigester";
        }else if(scoreChange==2){
            this.type = "兑换";
            this.ico = "icon-exchange";
        }else if(scoreChange==3){
            this.type = "锻炼";
            this.ico = "icon-sport";
        }else if(scoreChange==4){
            this.type = "分享";
            this.ico = "icon-share";
        }
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

    public Integer getScoreChange() {
        return scoreChange;
    }

    public void setScoreChange(Integer scoreChange) {
        this.scoreChange = scoreChange;
    }

    public String getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(String changeValue) {
        this.changeValue = changeValue;
    }

    public Long getSurplusScore() {
        return surplusScore;
    }

    public void setSurplusScore(Long surplusScore) {
        this.surplusScore = surplusScore;
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
