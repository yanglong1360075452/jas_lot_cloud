package com.wizinno.jas.user.service;

import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDto;
import com.wizinno.jas.user.service.dto.UserPatientDto;

/**
 * Created by HP on 2017/8/11.
 */
public interface PatientService {

    void savePatient(PatientDto patientDto);

    PatientDto getPatientByUserID(Long userId);

    UserPatientDto getPatientByPatientId(Long patientId);

    //删除病人用户
    void deletePatientById(Long userId);
}
