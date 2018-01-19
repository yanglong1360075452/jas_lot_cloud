package com.wizinno.jas.user.domain;

import com.wizinno.jas.user.domain.model.Doctor;
import com.wizinno.jas.user.service.dto.DoctorDto;
import com.wizinno.jas.user.service.dto.UserDoctorDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DoctorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table doctor
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table doctor
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    int insert(Doctor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table doctor
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    Doctor selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table doctor
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    List<Doctor> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table doctor
     *
     * @mbggenerated Thu Aug 17 13:40:02 CST 2017
     */
    int updateByPrimaryKey(Doctor record);

    Doctor getDoctorByUserId(Long userId);

    List<UserDoctorDto> selectUdByUserId(Long userId);

    List<UserDoctorDto> getDoctorsByHospital(Integer code);

    UserDoctorDto getDoctorAndPatientStatus(@Param("doctorId") Long doctorId, @Param("id") Long id);

    UserDoctorDto getDoctorByDId(Long doctorId);
}