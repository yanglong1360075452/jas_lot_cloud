package com.wizinno.jas.user.service.dto;

/**
 * Created by HP on 2017/8/11.
 */
public class DoctorDto {

    private Long id;

    private Long userId;

    private Integer hospital;

    private Integer department;

    private String professiona;

    private String description;
    private  int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
