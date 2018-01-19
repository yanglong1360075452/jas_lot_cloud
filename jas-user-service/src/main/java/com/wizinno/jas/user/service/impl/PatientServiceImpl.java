package com.wizinno.jas.user.service.impl;

import com.wizinno.jas.common.util.AgeByBirthUtil;
import com.wizinno.jas.user.domain.PatientMapper;
import com.wizinno.jas.user.domain.model.Patient;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserPatientDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by HP on 2017/8/11.
 */
@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public void savePatient(PatientDto patientDto) {
        Patient patient=null;
        if (patientDto != null){
            patient=new Patient();
            BeanUtils.copyProperties(patientDto,patient);
            patientMapper.insert(patient);
        }

    }

    @Override
    public PatientDto getPatientByUserID(Long userId) {
        Patient  patient = patientMapper.getPatientByUserID(userId);
        PatientDto patientDto = new PatientDto();
        if (patient != null){
            BeanUtils.copyProperties(patient,patientDto);
            return patientDto;
        }
        return null;
    }

    @Override
    public UserPatientDto getPatientByPatientId(Long patientId) {
        UserPatientDto dto=patientMapper.getPatientByPatientId(patientId);

        if (dto.getBirthDate() != null){
            if (dto.getBirthDate() != null){
                int age = AgeByBirthUtil.getAge(dto.getBirthDate());
                dto.setAge(age);
            }
        }
        return dto;
    }

    @Override
    public void deletePatientById(Long userId) {
            patientMapper.deleteByPrimaryKey(userId);
    }

}
