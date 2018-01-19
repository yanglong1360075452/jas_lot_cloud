package com.wizinno.jas.exercise.service;

import com.wizinno.jas.exercise.service.dto.ExerciseProcessDto;
import com.wizinno.jas.exercise.service.dto.ExerciseRecordDto;
import net.sf.json.JSONArray;

import java.util.List;

/**
 * Created by HP on 2017/8/18.
 */
public interface ExerciseRecordService {
    void createExerciseRecord(ExerciseRecordDto dto);
    List<ExerciseRecordDto> getExerciseRecord(Long id);
    ExerciseRecordDto getExerciseRecordById(Long id);
    List<ExerciseRecordDto> getExerciseRecord(ExerciseRecordDto dto);
    List<ExerciseRecordDto> getExerciseRecordTimes(ExerciseRecordDto dto);
    JSONArray getExerciseRecordWeekMonth(ExerciseRecordDto dto);
    List<ExerciseRecordDto>getExerciseRecordDayByTime(ExerciseRecordDto dto);
    //后台查询得到康复记录条数
    List<ExerciseRecordDto>getExerciseCount(ExerciseRecordDto exerciseRecordDto);
    //后台查询训练记录列表
    List<ExerciseRecordDto>findExerciseRecordList(ExerciseRecordDto exerciseRecordDto);
}
