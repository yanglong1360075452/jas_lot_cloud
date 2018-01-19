package com.wizinno.jas.archival.api.vo;

import com.wizinno.jas.archival.service.dto.ClinicalDataDto;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/8/15.
 */
public class MedicalArchivalVO {

    private Long id;

    private Long patientId;  //病人id

    private Date surgeryTime;  //手术时间

    private Date startTime;  //开始时间/初诊时间

    private Integer illnessState;  //病情说明

    private Integer activity;//活动不足因素

    private Integer process;//病程

    private Integer department; //科室

    private Integer hospital; //医院

    private String doctorName;  //主刀医生

    private String content; //病例描述

    private Date createTime;

    private Date updateTime;
    private List<ClinicalDataDto> clinicalDataDtoList;
    private ClinicalDataDto [][] aedClinicalDataDtoList;

    public ClinicalDataDto[][] getAedClinicalDataDtoList() {
        return aedClinicalDataDtoList;
    }

    public void setAedClinicalDataDtoList(ClinicalDataDto[][] aedClinicalDataDtoList) {
        this.aedClinicalDataDtoList = aedClinicalDataDtoList;
    }

    public List<ClinicalDataDto> getClinicalDataDtoList() {
        return clinicalDataDtoList;
    }

    public void setClinicalDataDtoList(List<ClinicalDataDto> clinicalDataDtoList) {
        this.clinicalDataDtoList = clinicalDataDtoList;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getIllnessState() {
        return illnessState;
    }

    public void setIllnessState(Integer illnessState) {
        this.illnessState = illnessState;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Date getSurgeryTime() {
        return surgeryTime;
    }

    public void setSurgeryTime(Date surgeryTime) {
        this.surgeryTime = surgeryTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getHospital() {
        return hospital;
    }

    public void setHospital(Integer hospital) {
        this.hospital = hospital;
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
