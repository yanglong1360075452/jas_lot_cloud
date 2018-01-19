package com.wizinno.jas.exercise.api;

import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.exercise.api.vo.ExerciseProcessVO;
import com.wizinno.jas.exercise.api.vo.ExerciseRecordVO;
import com.wizinno.jas.exercise.service.ExerciseProcessService;
import com.wizinno.jas.exercise.service.ExerciseRecordService;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessDto;
import com.wizinno.jas.exercise.service.dto.ExerciseRecordDto;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDto;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by HP on 2017/8/18.
 */

@RestController
@RequestMapping("/api/wechat/exerciseRecord")
public class ExerciseRecordController extends BaseController {

    @Autowired
    private ExerciseRecordService exerciseRecordService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseProcessService exerciseProcessService;

    //保存训练记录并计算sr、开始角度和结束角度
    @RequestMapping(value = "getExerciseRecord",method = RequestMethod.GET)
    public ResponseVO updateExerciseRecord(@RequestParam(value = "epId",required = false)Long epId){
        List<ExerciseRecordDto> exerciseRecordDtoList = null;

        return new ResponseVO(exerciseRecordDtoList);
    }

    //通过康复计划Id获取训练记录
    @RequestMapping(value = "getExerciseRecordByepId",method = RequestMethod.GET)
    public ResponseVO getExerciseRecordByepId(@RequestParam(value = "epId")Long epId){
        ExerciseRecordDto exerciseRecordDto = new ExerciseRecordDto();
        if(null != epId) {
            ExerciseProcessDto exerciseProcessDto = exerciseProcessService.getEPById(epId);
            if (null == exerciseProcessDto) {
                throw new CustomException(CustomExceptionCode.RecordNotExist);
            }
            exerciseRecordDto.setStartTime(exerciseProcessDto.getStartTime());
            exerciseRecordDto.setEndTime(exerciseProcessDto.getEndTime());
            exerciseRecordDto.setPatientId(exerciseProcessDto.getPatientId());
            exerciseRecordDto.setArthro(exerciseProcessDto.getArthro());
            exerciseRecordDto.setPostion(exerciseProcessDto.getPostion());
            exerciseRecordDto.setDirection(exerciseProcessDto.getDirection());
        }
        return new ResponseVO(exerciseRecordService.getExerciseRecord(exerciseRecordDto));
    }

    //通过康复计划Id获取训练记录画 多次 折线图
    @RequestMapping(value = "getExerciseRecordByepIdTime",method = RequestMethod.GET)
    public ResponseVO getExerciseRecordByepIdTime(@RequestParam(value = "epId")Long epId,
                                                  @RequestParam(value = "startTime")Long startTime,
                                                  @RequestParam(value = "endTime")Long endTime){
        List<ExerciseRecordDto> exerciseRecordDtoList = null;
        if(null != epId) {
            ExerciseProcessDto exerciseProcessDto = exerciseProcessService.getEPById(epId);
            if (null == exerciseProcessDto) {
                throw new CustomException(CustomExceptionCode.RecordNotExist);
            }
            ExerciseRecordDto exerciseRecordDto = new ExerciseRecordDto();
            exerciseRecordDto.setStartTime(new Date(startTime));
            exerciseRecordDto.setEndTime(new Date(endTime));
            exerciseRecordDto.setPatientId(exerciseProcessDto.getPatientId());
            exerciseRecordDto.setArthro(exerciseProcessDto.getArthro());
            exerciseRecordDto.setPostion(exerciseProcessDto.getPostion());
            exerciseRecordDto.setDirection(exerciseProcessDto.getDirection());
            exerciseRecordDtoList = exerciseRecordService.getExerciseRecordTimes(exerciseRecordDto);
        }
        return new ResponseVO(exerciseRecordDtoList);
    }

    //通过康复计划Id获取周、月训练记录
    @RequestMapping(value = "getExerciseRecordWeekMonth",method = RequestMethod.GET)
    public ResponseVO getExerciseRecordWeekMonth(@RequestParam(value = "epId")Long epId,
                                                 @RequestParam(value = "startTime")Long startTime,
                                                 @RequestParam(value = "endTime")Long endTime) throws Exception {
        List<ExerciseRecordDto> exerciseRecordDtoList = null;
        JSONArray jsonArray = null;
        if(null != epId) {
            ExerciseProcessDto exerciseProcessDto = exerciseProcessService.getEPById(epId);
            if (null == exerciseProcessDto) {
                throw new CustomException(CustomExceptionCode.RecordNotExist);
            }
            ExerciseRecordDto exerciseRecordDto = new ExerciseRecordDto();
            if(null !=startTime && null!=endTime){
                exerciseRecordDto.setStartTime(new Date(startTime));
                exerciseRecordDto.setEndTime(new Date(endTime));
            }
            exerciseRecordDto.setPatientId(exerciseProcessDto.getPatientId());
            exerciseRecordDto.setArthro(exerciseProcessDto.getArthro());
            exerciseRecordDto.setPostion(exerciseProcessDto.getPostion());
            exerciseRecordDto.setDirection(exerciseProcessDto.getDirection());
            jsonArray = exerciseRecordService.getExerciseRecordWeekMonth(exerciseRecordDto);
        }
        return new ResponseVO(jsonArray);
    }

    //通过康复记录Id获取单次训练记录明细
    @RequestMapping(value = "getExerciseRecordDetail",method = RequestMethod.GET)
    public ResponseVO getExerciseRecordDetail(@RequestParam(value = "erId")Long erId){
       ExerciseRecordDto exerciseRecordDtoList = null;
        if(null != erId) {
            exerciseRecordDtoList = exerciseRecordService.getExerciseRecordById(erId);
        }
        return new ResponseVO(exerciseRecordDtoList);
    }

    //通过康复计划id和开始结束时间得到每天的训练记录
    @RequestMapping(value = "getExerciseRecordDayByTime",method = RequestMethod.GET)
    public ResponseVO getExerciseRecordDayByTime(@RequestParam(value = "epId")Long epId,
                                                 @RequestParam(value = "startTime")Long startTime,
                                                 @RequestParam(value = "endTime")Long endTime){
        List<ExerciseRecordDto> exerciseRecordDtoList = null;
        if(null != epId) {
            ExerciseProcessDto exerciseProcessDto = exerciseProcessService.getEPById(epId);
            if (null == exerciseProcessDto) {
                throw new CustomException(CustomExceptionCode.RecordNotExist);
            }
            ExerciseRecordDto exerciseRecordDto = new ExerciseRecordDto();
            if(null !=startTime && null!=endTime){
                exerciseRecordDto.setStartTime(new Date(startTime));
                exerciseRecordDto.setEndTime(new Date(endTime));
            }
            exerciseRecordDto.setPatientId(exerciseProcessDto.getPatientId());
            exerciseRecordDto.setArthro(exerciseProcessDto.getArthro());
            exerciseRecordDto.setPostion(exerciseProcessDto.getPostion());
            exerciseRecordDto.setDirection(exerciseProcessDto.getDirection());
            exerciseRecordDtoList = exerciseRecordService.getExerciseRecordDayByTime(exerciseRecordDto);
        }
        return new ResponseVO(exerciseRecordDtoList);
    }
}
