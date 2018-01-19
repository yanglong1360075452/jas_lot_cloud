package com.wizinno.jas.user.domain.model;

import java.io.Serializable;

public class Doctor implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctor.id
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctor.user_id
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctor.hospital
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    private Integer hospital;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctor.department
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    private Integer department;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctor.professiona
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    private String professiona;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctor.description
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table doctor
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctor.id
     *
     * @return the value of doctor.id
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctor.id
     *
     * @param id the value for doctor.id
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctor.user_id
     *
     * @return the value of doctor.user_id
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctor.user_id
     *
     * @param userId the value for doctor.user_id
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctor.hospital
     *
     * @return the value of doctor.hospital
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public Integer getHospital() {
        return hospital;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctor.hospital
     *
     * @param hospital the value for doctor.hospital
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public void setHospital(Integer hospital) {
        this.hospital = hospital;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctor.department
     *
     * @return the value of doctor.department
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public Integer getDepartment() {
        return department;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctor.department
     *
     * @param department the value for doctor.department
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public void setDepartment(Integer department) {
        this.department = department;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctor.professiona
     *
     * @return the value of doctor.professiona
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public String getProfessiona() {
        return professiona;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctor.professiona
     *
     * @param professiona the value for doctor.professiona
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public void setProfessiona(String professiona) {
        this.professiona = professiona == null ? null : professiona.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctor.description
     *
     * @return the value of doctor.description
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctor.description
     *
     * @param description the value for doctor.description
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}