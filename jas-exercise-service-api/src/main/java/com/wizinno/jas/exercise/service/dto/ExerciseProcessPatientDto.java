package com.wizinno.jas.exercise.service.dto;

import org.springframework.http.ResponseEntity;

import java.util.Date;

/**
 * Created by HP on 2017/8/23.
 */
public class ExerciseProcessPatientDto {

    private Long id;

    private Long patientId;  //病人id

    private Long userId;

    private Long doctorId;  //医生id

    private Integer arthro;//关节

    private Integer postion; //部位

    private Integer direction;  //方向

    private Date startTime; //开始时间

    private Date endTime;  //结束时间
    private String userName;
    private String exerciseProcessDesc;//康复计划

    public String getExerciseProcessDesc() {
        return exerciseProcessDesc;
    }

    public void setExerciseProcessDesc(String exerciseProcessDesc) {
        this.exerciseProcessDesc = exerciseProcessDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Integer sign;  //医生是否读到

    private Integer report;  //0代表这个训练计划没有新的评估报告，1代表这个训练计划有新的评估报告

    private Integer record;  //0代表这个训练计划有新的训练记录，1代表这个训练计划有新的训练记录

    private String note;

    private String patientName;

    private String sex;

    private Integer age;
    private String phone;
    private Date birthDate;

    public Long getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String openId;
    private Date comfirmedTime;
    private String headPortrait;
    private ResponseEntity responseEntity;

    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getComfirmedTime() {
        return comfirmedTime;
    }

    public void setComfirmedTime(Date comfirmedTime) {
        this.comfirmedTime = comfirmedTime;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Integer getReport() {
        return report;
    }

    public void setReport(Integer report) {
        this.report = report;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
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

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}


