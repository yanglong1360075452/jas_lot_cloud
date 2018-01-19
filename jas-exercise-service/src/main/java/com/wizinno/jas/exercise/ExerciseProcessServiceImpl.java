package com.wizinno.jas.exercise;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.common.util.AgeByBirthUtil;
import com.wizinno.jas.common.util.DateUtil;
import com.wizinno.jas.dict.service.DataDictService;
import com.wizinno.jas.dict.service.data.ParamKey;
import com.wizinno.jas.dict.service.dto.ArthroDto;
import com.wizinno.jas.dict.service.dto.DataDictDto;
import com.wizinno.jas.exercise.domain.ExerciseProcessMapper;
import com.wizinno.jas.exercise.domain.model.ExerciseProcess;
import com.wizinno.jas.exercise.domain.model.ExerciseRecord;
import com.wizinno.jas.exercise.service.ExerciseProcessService;
import com.wizinno.jas.exercise.service.ExerciseRecordService;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessDoctorDto;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessDto;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessPatientDto;
import com.wizinno.jas.exercise.service.dto.ExerciseRecordDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2017/8/14.
 */
@Service
public class ExerciseProcessServiceImpl implements ExerciseProcessService {

    @Autowired
    private ExerciseProcessMapper exerciseProcessMapper;
    @Autowired
    private DataDictService dataDictService;
    @Autowired
    private ExerciseRecordService exerciseRecordService;

    @Override
    public void createExerciseProcess(ExerciseProcessDto exerciseProcessDto) {
        ExerciseProcess exerciseProcess = new ExerciseProcess();
        BeanUtils.copyProperties(exerciseProcessDto,exerciseProcess);
        exerciseProcessMapper.insert(exerciseProcess);

    }

    @Override
    public List<ExerciseProcessDoctorDto> getDoctorsByPatientId(Long id) {
        List<ExerciseProcessDoctorDto> dtos=exerciseProcessMapper.getDoctorsByPatientId(id);
        if (dtos.size() > 0){
            for (ExerciseProcessDoctorDto dto:dtos){
                if (dto.getBrith() != null){
                    int age = AgeByBirthUtil.getAge(dto.getBrith());
                    dto.setAge(age);
                }

            }
        }
        return dtos;
    }

    @Override
    public List<ExerciseProcessDto> getExerciseProcessByPatientId(Long patientId) {
        List<ExerciseProcessDto> eps=exerciseProcessMapper.getExerciseProcessByPatientId(patientId);


        return eps;
    }

    @Override
    public void updateExerciseProcess(ExerciseProcessDto exerciseProcessDto) {
        ExerciseProcess exerciseProcess = new ExerciseProcess();
        BeanUtils.copyProperties(exerciseProcessDto,exerciseProcess);
        exerciseProcessMapper.updateByPrimaryKey(exerciseProcess);
    }

    @Override
    public void deleteExerciseProcess(Long epId) {
        exerciseProcessMapper.deleteByPrimaryKey(epId);
    }

    @Override
    public ExerciseProcessDto getEPById(Long epId) {
        ExerciseProcess exerciseProcess = exerciseProcessMapper.selectByPrimaryKey(epId);
        if (exerciseProcess == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        ExerciseProcessDto exerciseProcessDto = new ExerciseProcessDto();
        BeanUtils.copyProperties(exerciseProcess,exerciseProcessDto);

        return exerciseProcessDto;
    }

    @Override
    public List<ExerciseProcessPatientDto> getPatientsByPatientId(Long patientId) {

        List<ExerciseProcessPatientDto> dto= exerciseProcessMapper.getPatientsByPatientId(patientId);
        return null;
    }

    @Override
    public List<ExerciseProcessDto> getEPByPIdAndDId(Long patientId, Long id) {

        List<ExerciseProcessDto> dtos=exerciseProcessMapper.getEPByPIdAndDId(patientId,id);

        return dtos;
    }

    @Override
    public List<ExerciseProcessPatientDto> getPatientByPatientId(Map map) {
        List<ExerciseProcessPatientDto> exerciseProcessPatientDtoList = exerciseProcessMapper.getPatientByPatientId(map);
        if(exerciseProcessPatientDtoList.size()>0){
            List<ArthroDto> dataDictDtoListA = dataDictService.getContentToListByKey(ParamKey.Arthro.toString());
            List<DataDictDto> dataDictDtoListD = dataDictService.getContentToListByKey(ParamKey.Direction.toString());
            List<DataDictDto> dataDictDtoListP = dataDictService.getContentToListByKey(ParamKey.Postion.toString());
            for(ExerciseProcessPatientDto exerciseProcessPatientDto:exerciseProcessPatientDtoList) {
                StringBuffer stringBuffer = new StringBuffer();
                if (null != exerciseProcessPatientDto.getBirthDate()) {
                    try {
                        exerciseProcessPatientDto.setAge(DateUtil.getAge(exerciseProcessPatientDto.getBirthDate()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (null != exerciseProcessPatientDto.getPostion()) {
                    for (DataDictDto dataDictDtoP : dataDictDtoListP) {
                        if (dataDictDtoP.getCode().equals(exerciseProcessPatientDto.getPostion())) {
                            stringBuffer.append(dataDictDtoP.getName());
                        }
                    }
                }
                if (null != exerciseProcessPatientDto.getArthro()) {
                    for (ArthroDto dataDictDtoA : dataDictDtoListA) {
                        if (dataDictDtoA.getCode().equals(exerciseProcessPatientDto.getArthro())) {
                            stringBuffer.append(dataDictDtoA.getName());
                        }
                    }
                }
                if (null != exerciseProcessPatientDto.getDirection()) {
                    for (DataDictDto dataDictDtoD : dataDictDtoListD) {
                        if (dataDictDtoD.getCode().equals(exerciseProcessPatientDto.getDirection())) {
                            stringBuffer.append("-");
                            stringBuffer.append(dataDictDtoD.getName());
                        }
                    }
                }
                exerciseProcessPatientDto.setExerciseProcessDesc(stringBuffer.toString());
            }
        }
        return exerciseProcessPatientDtoList;
    }

    @Override
    public List<ExerciseProcessDto> findExerciseProcessList(ExerciseProcessDto exerciseProcessDto) {
        List<ExerciseProcessDto>exerciseProcessDtoList  = exerciseProcessMapper.findExerciseProcessList(exerciseProcessDto);
        if(exerciseProcessDtoList.size()>0 && exerciseProcessDtoList!=null){
            List<ArthroDto> dataDictDtoListA = dataDictService.getContentToListByKey(ParamKey.Arthro.toString());//根据code值查出字典中的名称
            List<DataDictDto> dataDictDtoListD = dataDictService.getContentToListByKey(ParamKey.Direction.toString());
            List<DataDictDto> dataDictDtoListP = dataDictService.getContentToListByKey(ParamKey.Postion.toString());
            for(ExerciseProcessDto ep:exerciseProcessDtoList) {
                StringBuffer stringBuffer = new StringBuffer();
                if (null != ep.getStartTime() && null!=ep.getEndTime()) {//计算除训练周期
                    try {
                        Date startTime = new Date(String.valueOf(ep.getStartTime()));
                        Date endTime = new Date(String.valueOf(ep.getEndTime()));
                        Integer days = (int)((endTime.getTime() - startTime.getTime())/86400000);
                        ep.setTrainingCycle(days);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (null != ep.getPostion()) {
                    for (DataDictDto dataDictDtoP : dataDictDtoListP) {
                        if (dataDictDtoP.getCode().equals(ep.getPostion())) {
                            stringBuffer.append(dataDictDtoP.getName());
                            ep.setPostionDesc(dataDictDtoP.getName());

                        }
                    }
                }
                if (null != ep.getArthro()) {
                    for (ArthroDto dataDictDtoA : dataDictDtoListA) {
                        if (dataDictDtoA.getCode().equals(ep.getArthro())) {
                            stringBuffer.append(dataDictDtoA.getName());
                            ep.setArthroDesc(dataDictDtoA.getName());
                        }
                    }
                }
                if (null != ep.getDirection()) {
                    for (DataDictDto dataDictDtoD : dataDictDtoListD) {
                        if (dataDictDtoD.getCode().equals(ep.getDirection())) {
                            stringBuffer.append("-");
                            stringBuffer.append(dataDictDtoD.getName());
                            ep.setDirectionDesc(dataDictDtoD.getName());
                        }
                    }
                }
                ExerciseRecordDto exerciseRecordDto = new ExerciseRecordDto();
                exerciseRecordDto.setPatientId(ep.getPatientId());
                exerciseRecordDto.setArthro(ep.getArthro());
                exerciseRecordDto.setDirection(ep.getDirection());
                exerciseRecordDto.setPostion(ep.getPostion());
                List<ExerciseRecordDto>exerciseRecordDtoList =  exerciseRecordService.getExerciseCount(exerciseRecordDto);
                if(exerciseRecordDtoList.size()>0){
                    ep.setExerciseRecordCount(exerciseRecordDtoList.size());
                }else{
                    ep.setExerciseRecordCount(0);
                }
                ep.setExerciseProcessName(stringBuffer.toString());
            }
        }
        return exerciseProcessDtoList;
    }
}
