package com.wizinno.jas.exercise.service.dto;

import java.util.Date;

/**
 * Created by HP on 2017/8/21.
 */
public class ReportDto {

    private Long id;

    private Long epId;

    private Long doctorId;
    private String doctorName;//医生姓名

    private Date startTime;

    private Date endTime;

    private Integer verdict;

    private String content;

    private Date createTime;

    private Date updateTime;
    private String verdictDes;

    public String getVerdictDes() {
        return verdictDes;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setVerdictDes(Integer verdict) {
        if(verdict==1){
            this.verdictDes = "保持";
        }else if(verdict==2){
            this.verdictDes = "改善";
        }else if(verdict==3) {
            this.verdictDes = "恶化";
        }else if(verdict==4){
            this.verdictDes = "脱落";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEpId() {
        return epId;
    }

    public void setEpId(Long epId) {
        this.epId = epId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

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

    public Integer getVerdict() {
        return verdict;
    }

    public void setVerdict(Integer verdict) {
        this.verdict = verdict;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
