package com.wizinno.jas.user.service.impl;

import com.wizinno.jas.user.domain.DoctorMapper;
import com.wizinno.jas.user.domain.model.Doctor;
import com.wizinno.jas.user.service.DoctorService;
import com.wizinno.jas.user.service.dto.DoctorDto;
import com.wizinno.jas.user.service.dto.UserDoctorDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/8/11.
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public void saveDoctor(DoctorDto doctorDto) {
        Doctor doctor=null;
        if (doctorDto != null){
            doctor=new Doctor();
            BeanUtils.copyProperties(doctorDto,doctor);
            doctorMapper.insert(doctor);
        }

    }

    @Override
    public DoctorDto getDoctorByUserId(Long userId) {
        Doctor doctor = doctorMapper.getDoctorByUserId(userId);
        DoctorDto doctordto = new DoctorDto();
        if (doctor != null){
            BeanUtils.copyProperties(doctor,doctordto);
            return doctordto;
        }
        return null;

    }
    @Override
    public List<UserDoctorDto> selectUdByUserId(Long userId) {
        List<UserDoctorDto> userDoctorDto = doctorMapper.selectUdByUserId(userId);
        return userDoctorDto;

    }

    @Override
    public int updateDoctor(DoctorDto doctorDto) {
        Doctor doctor= new Doctor();
        BeanUtils.copyProperties(doctorDto,doctor);
        return doctorMapper.updateByPrimaryKey(doctor);
    }

    @Override
    public List<UserDoctorDto> getDoctorsByHospital(Integer code) {

        List<UserDoctorDto> doctors=doctorMapper.getDoctorsByHospital(code);

        return doctors;
    }

    @Override
    public UserDoctorDto getDoctorAndPatientStatus(Long doctorId, Long id) {

        UserDoctorDto dto=doctorMapper.getDoctorAndPatientStatus(doctorId,id);
        return dto;
    }

    @Override
    public UserDoctorDto getDoctorByDId(Long doctorId) {
        UserDoctorDto dto= doctorMapper.getDoctorByDId(doctorId);
        return dto;
    }

    @Override
    public void deleteDoctorById(Long id) {
        doctorMapper.deleteByPrimaryKey(id);
    }
}
