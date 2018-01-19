package com.wizinno.jas.exercise.api.vo;

import java.util.Date;

/**
 * Created by HP on 2017/8/18.
 */
public class ExerciseRecordVO {

    private Long id;

    private Long patientId;

    private Long deviceId;

    private Integer arthro;

    private Integer postion;

    private Integer direction;

    private String geolocation;

    private Date startTime;

    private Date endTime;

    private String startAngle;

    private String endAngle;

    private Integer sr;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getArthro() {
        return arthro;
    }

    public void setArthro(Integer arthro) {
        this.arthro = arthro;
    }

    public Integer getPostion() {
        return postion;
    }

    public void setPostion(Integer postion) {
        this.postion = postion;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
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

    public String getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(String startAngle) {
        this.startAngle = startAngle;
    }

    public String getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(String endAngle) {
        this.endAngle = endAngle;
    }

    public Integer getSr() {
        return sr;
    }

    public void setSr(Integer sr) {
        this.sr = sr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
