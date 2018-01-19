package com.wizinno.jas.controller;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.common.wechat.AccessToken;
import com.wizinno.jas.common.wechat.WeChatUtil;
import com.wizinno.jas.common.wechat.WechatJsonObject;
import com.wizinno.jas.device.service.DeviceRecordService;
import com.wizinno.jas.device.service.DeviceService;
import com.wizinno.jas.device.service.dto.DeviceDto;
import com.wizinno.jas.device.service.dto.DeviceRecordDto;
import com.wizinno.jas.exercise.service.ExerciseProcessService;
import com.wizinno.jas.exercise.service.ExerciseRecordService;
import com.wizinno.jas.exercise.service.ReportService;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessDto;
import com.wizinno.jas.exercise.service.dto.ExerciseRecordDto;
import com.wizinno.jas.exercise.service.dto.ReportDto;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.dto.PatientDto;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("manageWebapp/api/manage/exerciseProcess")
public class ExerciseProcessManageController {
    private static  final String getCodeUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceRecordService deviceRecordService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ExerciseProcessService exerciseProcessService;
    @Autowired
    private ExerciseRecordService exerciseRecordService;
    @Autowired
    private ReportService reportService;
    //后台查询康复计划列表
    @RequestMapping(value = "getExerciseProcessList",method = RequestMethod.GET)
    public ResponseVO getExerciseProcessList(@RequestParam(value = "patientName",required = false)String patientName,
                                             @RequestParam(value = "doctorName",required = false)String doctorName,
                                             @RequestParam(value = "startTime",required = false)Long startTime,
                                             @RequestParam(value = "endTime",required = false)Long endTime){
        ExerciseProcessDto exerciseProcessDto = new ExerciseProcessDto();
        exerciseProcessDto.setPatientName(patientName);
        exerciseProcessDto.setDoctorName(doctorName);
        if(null !=startTime && null !=endTime) {
            exerciseProcessDto.setStartTime(new Date(startTime));
            exerciseProcessDto.setEndTime(new Date(endTime));
        }
        List<ExerciseProcessDto>exerciseProcessList = exerciseProcessService.findExerciseProcessList(exerciseProcessDto);
        return new ResponseVO(exerciseProcessList);
    }
    //后台根据康复计划id查看康复计划详情
    @RequestMapping(value="getExerciseProcess/{id}",method = RequestMethod.GET)
    public ResponseVO getExerciseProcessById(@PathVariable("id")Long id){
        ExerciseProcessDto exerciseProcessDto = new ExerciseProcessDto();
        exerciseProcessDto.setId(id);
        List<ExerciseProcessDto> dto=exerciseProcessService.findExerciseProcessList(exerciseProcessDto);
        return new ResponseVO(dto);
    }

    //后台根据康复计划id查看评估记录
    @RequestMapping(value="getReportListById/{id}",method = RequestMethod.GET)
    public ResponseVO getReportListById(@PathVariable("id")Long id){
        List<ReportDto>reportDtoList = reportService.findReportListByEpId(id);
        return new ResponseVO(reportDtoList);
    }

    //后台查询训练记录列表
    @RequestMapping(value="findExerciseRecordList",method = RequestMethod.GET)
    public ResponseVO findExerciseRecordList(@RequestParam(value = "patientName",required = false)String patientName,
                                             @RequestParam(value = "arthro",required = false)Integer arthro,
                                             @RequestParam(value = "postion",required = false)Integer postion,
                                             @RequestParam(value = "direction",required = false)Integer direction,
                                             @RequestParam(value = "startTime",required = false)Long startTime,
                                             @RequestParam(value = "endTime",required = false)Long endTime){
        ExerciseRecordDto exerciseRecordDto = new ExerciseRecordDto();
        exerciseRecordDto.setPatientName(patientName);
        exerciseRecordDto.setArthro(arthro);
        exerciseRecordDto.setPostion(postion);
        exerciseRecordDto.setDirection(direction);
        if(null !=startTime && null !=endTime) {
            exerciseRecordDto.setStartTime(new Date(startTime));
            exerciseRecordDto.setEndTime(new Date(endTime));
        }
        List<ExerciseRecordDto>exerciseRecordDtoList = exerciseRecordService.findExerciseRecordList(exerciseRecordDto);
        return new ResponseVO(exerciseRecordDtoList);
    }





}
