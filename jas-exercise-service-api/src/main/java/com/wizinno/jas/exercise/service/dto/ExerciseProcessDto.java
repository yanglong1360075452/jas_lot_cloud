package com.wizinno.jas.exercise.service.dto;

import java.util.Date;

/**
 * Created by HP on 2017/8/14.
 */
public class ExerciseProcessDto {

    private Long id;

    private Long patientId;  //病人id
    private String patientName;//病人姓名
    private String updateByName;//康复计划修改人姓名
    private Long doctorId;  //医生id
    private String doctorName;//医生姓名

    private Integer arthro;//关节
    private String arthroDesc;//关节

    private Integer postion; //部位
    private String postionDesc; //部位

    private Integer direction;  //方向
    private String directionDesc;//方向
    private Integer exerciseRecordCount;//训练次数
    private String exerciseProcessName;//康复计划名称
    private Integer trainingCycle;//训练周期

    private Date startTime; //开始时间

    private Date endTime;  //结束时间

    private Long createBy;  //创建人

    private Long updateBy; //更改人

    private Integer sign;  //医生是否读到

    private Integer report;  //0代表这个训练计划没有新的评估报告，1代表这个训练计划有新的评估报告

    private Integer record;  //0代表这个训练计划有新的训练记录，1代表这个训练计划有新的训练记录

    private Date createTime;

    private Date updateTime;

    private String note;

    private String realName;

    private Integer isComfirmed;
    private Date comfirmedTime;

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

    public Integer getTrainingCycle() {
        return trainingCycle;
    }

    public void setTrainingCycle(Integer trainingCycle) {
        this.trainingCycle = trainingCycle;
    }

    public String getExerciseProcessName() {
        return exerciseProcessName;
    }

    public void setExerciseProcessName(String exerciseProcessName) {
        this.exerciseProcessName = exerciseProcessName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getArthroDesc() {
        return arthroDesc;
    }

    public void setArthroDesc(String arthroDesc) {
        this.arthroDesc = arthroDesc;
    }

    public String getPostionDesc() {
        return postionDesc;
    }

    public void setPostionDesc(String postionDesc) {
        this.postionDesc = postionDesc;
    }

    public String getDirectionDesc() {
        return directionDesc;
    }

    public void setDirectionDesc(String directionDesc) {
        this.directionDesc = directionDesc;
    }

    public Integer getExerciseRecordCount() {
        return exerciseRecordCount;
    }

    public void setExerciseRecordCount(Integer exerciseRecordCount) {
        this.exerciseRecordCount = exerciseRecordCount;
    }

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

    private Integer overdue; //过期字段
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getOverdue() {
        return overdue;
    }

    public void setOverdue(Integer overdue) {
        this.overdue = overdue;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
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
