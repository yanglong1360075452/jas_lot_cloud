package com.wizinno.jas.exercise.service.dto;

import net.sf.json.JSONArray;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2017/8/18.
 */
public class ExerciseRecordDto {

    private Long id;

    private Long patientId;
    private String patientName;//病人姓名
    private Long deviceId;

    private Integer arthro;//关节
    private String arthroDesc;//关节

    private Integer postion; //部位
    private String postionDesc; //部位

    private Integer direction;  //方向
    private String directionDesc;//方向

    private String geolocation;

    private Date startTime;

    private Date endTime;
    private String time;
    private JSONArray jsonArray;

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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getEndAngle() {
        return endAngle;
    }

    private int startAngle;

    private int endAngle;

    private Integer sr;

    private Date createTime;

    private String recordData;

    //训练时长
    private long trainingLength;
    private String trainingLengthDesc;
    //角度变化
    private int angleChange;
    //最长时间
    private int maximumTime;
    //最短时间
    private int minimumTime;
    //平均时间
    private int averageTime;

    public String getTrainingLengthDesc() {
        return trainingLengthDesc;
    }

    public void setTrainingLengthDesc(String trainingLengthDesc) {
        this.trainingLengthDesc = trainingLengthDesc;
    }

    public long getTrainingLength() {
        return trainingLength;
    }

    public void setTrainingLength(long trainingLength) {
        this.trainingLength = trainingLength;
    }

    public int getAngleChange() {
        return angleChange;
    }

    public void setAngleChange(int angleChange) {
        this.angleChange = angleChange;
    }

    public int getMaximumTime() {
        return maximumTime;
    }

    public void setMaximumTime(int maximumTime) {
        this.maximumTime = maximumTime;
    }

    public int getMinimumTime() {
        return minimumTime;
    }

    public void setMinimumTime(int minimumTime) {
        this.minimumTime = minimumTime;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public String getRecordData() {
        return recordData;
    }

    public void setRecordData(String recordData) {
        this.recordData = recordData;
    }
    private List<DataDto> dataDtoList;

    public List<DataDto> getDataDtoList() {
        return dataDtoList;
    }

    public void setDataDtoList(List<DataDto> dataDtoList) {
        this.dataDtoList = dataDtoList;
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

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getEndAngle(int last) {
        return endAngle;
    }

    public void setEndAngle(int endAngle) {
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
