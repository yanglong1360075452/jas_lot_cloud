package com.wizinno.jas.exercise.service.dto;

import org.springframework.http.ResponseEntity;

import java.util.Date;

/**
 * Created by LiuMei on 2017-07-26.
 */
public class ExerciseProcessDoctorDto {


    private Long patientId;  //病人id

    private Long doctorId;  //医生id

    private String doctorName;

    private Integer sex;

    private Integer age;

    private Date brith;

    private String professiona; //职称

    private String description; //简介

    private Integer hospital;//医院
    private String hospitalDesc;

    private Integer department;  //科室
    private String departmentDesc;

    public String getHospitalDesc() {
        return hospitalDesc;
    }

    public void setHospitalDesc(String hospitalDesc) {
        this.hospitalDesc = hospitalDesc;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    private String openId;

    private Integer status;

    private Integer isComfirmed;
    private Date comfirmedTime;

    public Integer getIsComfirmed() {
        return isComfirmed;
    }

    public void setIsComfirmed(Integer isComfirmed) {
        this.isComfirmed = isComfirmed;
    }

    public Date getComfirmedTime() {
        return comfirmedTime;
    }

    public void setComfirmedTime(Date comfirmedTime) {
        this.comfirmedTime = comfirmedTime;
    }

    private String headPortRait;
    private ResponseEntity responseEntity;

    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    public String getHeadPortRait() {
        return headPortRait;
    }

    public void setHeadPortRait(String headPortRait) {
        this.headPortRait = headPortRait;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBrith() {
        return brith;
    }

    public void setBrith(Date brith) {
        this.brith = brith;
    }

    public String getProfessiona() {
        return professiona;
    }

    public void setProfessiona(String professiona) {
        this.professiona = professiona;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHospital() {
        return hospital;
    }

    public void setHospital(Integer hospital) {
        this.hospital = hospital;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
