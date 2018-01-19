package com.wizinno.jas.exercise.api;

import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.exercise.api.vo.ReportVO;
import com.wizinno.jas.exercise.service.ExerciseProcessService;
import com.wizinno.jas.exercise.service.ExerciseRecordService;
import com.wizinno.jas.exercise.service.ReportService;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessDto;
import com.wizinno.jas.exercise.service.dto.ReportDto;
import com.wizinno.jas.user.service.DoctorService;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.DoctorDto;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by HP on 2017/8/21.
 */

@RestController
@RequestMapping("/api/wechat/report")
public class ReportController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ExerciseProcessService exerciseProcessService;

    @Autowired
    private PatientService patientService;

    //根据训练计划创建报告
    @RequestMapping(value = "/{epId}",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO createReport(@PathVariable("epId")Long epId,@RequestBody ReportVO reportVO){
        if (epId < 0 || epId == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        ExerciseProcessDto epd = exerciseProcessService.getEPById(epId);
        if (epd == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }

        Date date = new Date();
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        DoctorDto doctor = doctorService.getDoctorByUserId(user.getId());
        if (doctor == null){
            throw new CustomException(CustomExceptionCode.UserNotExist);
        }


        Date startTime = reportVO.getStartTime();
        Date endTime = reportVO.getEndTime();
        Integer verdict = reportVO.getVerdict();
        reportVO.setDoctorId(doctor.getId());
         reportVO.setEpId(epId);
        if (startTime == null || endTime == null || verdict == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        ReportDto reportDto = new ReportDto();

        BeanUtils.copyProperties(reportVO,reportDto);
        reportDto.setCreateTime(date);
        reportDto.setUpdateTime(date);
        reportService.createReport(reportDto);

        epd.setReport(1);
        exerciseProcessService.updateExerciseProcess(epd);
        return new ResponseVO();

    }


    //根据训练计划查询报告
    @RequestMapping(value = "/{epId}",method = RequestMethod.GET)
    public ResponseVO getReports(@PathVariable("epId") Long epId){
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        PatientDto patient = patientService.getPatientByUserID(user.getId());
        if (patient != null){
            ExerciseProcessDto epd = exerciseProcessService.getEPById(epId);
            if (epd == null){
                throw new CustomException(CustomExceptionCode.ErrorParam);
            }
            epd.setReport(0);
            exerciseProcessService.updateExerciseProcess(epd);
        }

        if (epId < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        List<ReportDto> dtos=reportService.getReports(epId);
        if(dtos.size()>0) {
            for (int i = 0; i < dtos.size(); i++) {
                dtos.get(i).setVerdictDes(dtos.get(i).getVerdict());
            }
        }
        return new ResponseVO(dtos);
    }


}
