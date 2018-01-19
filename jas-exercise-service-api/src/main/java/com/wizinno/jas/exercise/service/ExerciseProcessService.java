package com.wizinno.jas.exercise.service;

import com.wizinno.jas.exercise.service.dto.ExerciseProcessDoctorDto;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessDto;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessPatientDto;

import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2017/8/14.
 */

public interface ExerciseProcessService {


    void createExerciseProcess(ExerciseProcessDto exerciseProcessDto);

    List<ExerciseProcessDoctorDto> getDoctorsByPatientId(Long id);

    List<ExerciseProcessDto> getExerciseProcessByPatientId(Long patientId);

    void updateExerciseProcess(ExerciseProcessDto exerciseProcessDto);

    void deleteExerciseProcess(Long epId);

    ExerciseProcessDto getEPById(Long epId);

    List<ExerciseProcessPatientDto> getPatientsByPatientId(Long patientId);

    List<ExerciseProcessDto> getEPByPIdAndDId(Long patientId, Long id);

    //医生得到所有通过验证的病人
    List<ExerciseProcessPatientDto>getPatientByPatientId(Map map);

    //管理后台查询康复计划列表
    List<ExerciseProcessDto>findExerciseProcessList(ExerciseProcessDto exerciseProcessDto);
}
