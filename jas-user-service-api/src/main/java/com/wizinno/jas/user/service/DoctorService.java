package com.wizinno.jas.user.service;

import com.wizinno.jas.user.service.dto.DoctorDto;
import com.wizinno.jas.user.service.dto.UserDoctorDto;

import java.util.List;

/**
 * Created by HP on 2017/8/11.
 */
public interface DoctorService {

    void saveDoctor(DoctorDto doctorDto);

    DoctorDto getDoctorByUserId(Long userId);

    List<UserDoctorDto> selectUdByUserId(Long userId);

    int updateDoctor(DoctorDto doctorDto);


    List<UserDoctorDto> getDoctorsByHospital(Integer code);

    UserDoctorDto getDoctorAndPatientStatus(Long doctorId, Long id);

    UserDoctorDto getDoctorByDId(Long doctorId);
    //删除医生用户
    void deleteDoctorById(Long id);
}
