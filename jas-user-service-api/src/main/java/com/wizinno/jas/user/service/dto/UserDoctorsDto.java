package com.wizinno.jas.user.service.dto;

import org.springframework.http.ResponseEntity;

import java.util.Date;

/**
 * Created by LiuMei on 2017-07-26.
 */
public class UserDoctorsDto {

    private Long id;
    //头像
    private String headPortrait;
    //性别
    private Integer sex;

    private String realName;

    private Integer hospital;
    private Integer status;
    private String statusDesc;
    private String phone;
    private Date createTime;

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer department;
    private String professiona;
    private String description;

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

    private Date birthDate;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private ResponseEntity responseEntity;

    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

}
