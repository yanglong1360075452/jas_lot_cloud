package com.wizinno.jas.controller;
import com.wizinno.jas.archival.service.ClinicalDataService;
import com.wizinno.jas.archival.service.MedicalArchivalService;
import com.wizinno.jas.archival.service.dto.ClinicalDataDto;
import com.wizinno.jas.archival.service.dto.MedicalArchivalDto;
import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.PageData;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.util.DateUtil;
import com.wizinno.jas.dict.service.DataDictService;
import com.wizinno.jas.dict.service.data.ParamKey;
import com.wizinno.jas.dict.service.dto.DataDictDto;
import com.wizinno.jas.dict.service.dto.HospitalDto;
import com.wizinno.jas.exercise.service.ExerciseProcessService;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessPatientDto;
import com.wizinno.jas.user.service.DoctorService;
import com.wizinno.jas.user.service.OperationLogService;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by LiuMei on 2017-07-26.
 */
@RestController
@RequestMapping("manageWebapp/api/manage")
public class UserManageController extends BaseController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DataDictService dataDictService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private MedicalArchivalService medicalArchivalService;
    @Autowired
    private ExerciseProcessService exerciseProcessService;
    @Autowired
    private ClinicalDataService clinicalDataService;
    @Autowired
    private OperationLogService operationLogService;
    private String path="D:";
    //用户列表查询
    @RequestMapping(value = "users",method = RequestMethod.GET)
    public ResponseVO getPatientByPatientId(@RequestParam(value = "patient",required = false)String patient,
                                            @RequestParam(value = "doctor",required = false)String doctor,
                                            @RequestParam(value = "name",required = false)String name){
        PageData pageData = new PageData();
        if(null ==patient){
            patient="";
        }if(null == doctor){
            doctor = "";
        }if(null == name){
            name = "";
        }
        pageData.setParam(patient);
        pageData.setParamO(doctor);
        pageData.setName(name);
        List<UserDto> userDtoList = userService.findAll(pageData);
        for(UserDto userDto:userDtoList){
            PatientDto patientDto = patientService.getPatientByUserID(userDto.getId());
            if(null !=patientDto){
                userDto.setType("病人");
            }
            DoctorDto doctorDto = doctorService.getDoctorByUserId(userDto.getId());
            if(null !=doctorDto){
                userDto.setType("康复师");
            }
        }
        return new ResponseVO(userDtoList);
    }
    //后台用户详情
    @RequestMapping(value = "user/{id}",method = RequestMethod.GET)
    public ResponseVO user(@PathVariable("id")Long id){
        UserDto userDto = userService.getUserByUserId(id);
        ResponseEntity responseEntity = null;
        if(null !=userDto.getHeadPortrait() && !userDto.getHeadPortrait().equals("")) {
            File file = new File(path + userDto.getHeadPortrait());
            byte[] bytes = new byte[0];
            try {
                bytes = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
            userDto.setResponseEntity(responseEntity);
        }
        DoctorDto doctorDto = doctorService.getDoctorByUserId(userDto.getId());
        if(null !=doctorDto){
            List<HospitalDto> dictDataH = new ArrayList<HospitalDto>();
            dictDataH = dataDictService.getContentToListByKey(ParamKey.Hospital.toString());
            userDto.setDoctorId(doctorDto.getId());
            if(null !=dictDataH){
                for(HospitalDto hospitalDto:dictDataH){
                    if(hospitalDto.getCode().equals(doctorDto.getHospital())){
                        userDto.setHospital(hospitalDto.getName());
                    }
                }
            }
        }
        return new ResponseVO(userDto);
    }

    //后台病人病例查询
    @RequestMapping(value = "getMedicalArchival/{id}",method = RequestMethod.GET)
    public ResponseVO getMedicalArchival(@PathVariable("id")Long id){
        PatientDto patientDto = patientService.getPatientByUserID(id);
        UserDto userDto = userService.getUserByUserId(id);
        List<MedicalArchivalDto> medicalArchivalDtoList = null;
        List<HospitalDto> dictDataH = new ArrayList<HospitalDto>();
        if(null != patientDto){
            medicalArchivalDtoList = medicalArchivalService.getMArchivalByPatientId(patientDto.getId());
            dictDataH = dataDictService.getContentToListByKey(ParamKey.Hospital.toString());
            for (MedicalArchivalDto medicalArchivalDto:medicalArchivalDtoList){
                if(null !=dictDataH){
                    for(HospitalDto hospitalDto:dictDataH){
                        if(hospitalDto.getCode().equals(medicalArchivalDto.getHospital())){
                            medicalArchivalDto.setHospitalDesc(hospitalDto.getName());
                        }
                    }
                }
                medicalArchivalDto.setPatientName(userDto.getRealName());
            }
        }
        return new ResponseVO(medicalArchivalDtoList);
    }

    //后台查询病人病例详情
    @RequestMapping(value = "getMedicalArchivalDetail/{id}",method = RequestMethod.GET)
    public ResponseVO getMedicalArchivalDetail(@PathVariable("id")Long id){
        MedicalArchivalDto medicalArchivalDto = medicalArchivalService.getMArchivalDetailsByMAId(id);
        return new ResponseVO(medicalArchivalDto);
    }

    //后台查询病人病例档案图片
    @RequestMapping(value = "getClinicalDataList/{id}",method = RequestMethod.GET)
    public ResponseVO getClinicalDataList(@PathVariable("id")Long id){
        List<ClinicalDataDto>clinicalDataDtoList = clinicalDataService.getImgByMaId(id);
        return new ResponseVO(clinicalDataDtoList);
    }
    //后台查询病人病例档案图片
    @RequestMapping(value = "getImg",method = RequestMethod.GET)
    public ResponseVO getImg(@RequestParam(value = "src") String src){
        ResponseEntity responseEntity = null;
        if(null !=src && !src.equals("")) {
            File file = new File(path + src);
            byte[] bytes = new byte[0];
            try {
                bytes = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
        }
        return new ResponseVO(responseEntity);
    }

    //查询医生下面的所有病人
    @RequestMapping(value = "getExerciseProcess/{id}",method = RequestMethod.GET)
    public ResponseVO getExerciseProcess(@PathVariable("id")Long id){
        Map<String ,Object>map = new HashMap<>();
        map.put("doctorId",id);
        map.put("filter","");
        map.put("isComfirmed",1);
        List<ExerciseProcessPatientDto> exerciseProcessPatientDtoList=exerciseProcessService.getPatientByPatientId(map);
        return new ResponseVO(exerciseProcessPatientDtoList);
    }

    //查询康复师注册列表
    @RequestMapping(value = "userExamine",method = RequestMethod.GET)
    public ResponseVO getuserExamineList(@RequestParam(value = "hospital",required = false)String hospital,
                                            @RequestParam(value = "phone",required = false)String phone,
                                            @RequestParam(value = "status",required = false)Long status){
        PageData pageData = new PageData();
        if(null !=hospital){
            List<HospitalDto> datas =  datas = dataDictService.getContentToListByKey(ParamKey.Hospital.toString());
            for(HospitalDto hospitalDto:datas){
                if(hospital.equals(hospitalDto.getName())){
                    pageData.setParam(hospitalDto.getCode().toString());
                }
            }
        }
        pageData.setParamO(phone);
        pageData.setParaml(status);
        List<UserDoctorsDto> userDtoList = userService.getUserExamineList(pageData);
        for(UserDoctorsDto userDoctorsDto:userDtoList){
            if(null !=userDoctorsDto.getStatus() && userDoctorsDto.getStatus()==0){
                userDoctorsDto.setStatusDesc("审核中");
            }else if(null !=userDoctorsDto.getStatus() && userDoctorsDto.getStatus()==1){
                userDoctorsDto.setStatusDesc("审核同意");
            }else if(null !=userDoctorsDto.getStatus() && userDoctorsDto.getStatus()==2){
                userDoctorsDto.setStatusDesc("审核拒绝");
            }
        }
        return new ResponseVO(userDtoList);
    }

    //审核康复师
    @RequestMapping(value = "userExaminePassOnoPass",method = RequestMethod.GET)
    public ResponseVO userExaminePassOnoPass(@RequestParam(value = "userId")Long userId,@RequestParam(value = "status")int status){
        DoctorDto doctorDto = doctorService.getDoctorByUserId(userId);
        if(null != doctorDto){
            doctorDto.setStatus(status);
            doctorService.updateDoctor(doctorDto);
        }
        return new ResponseVO();
    }
    //操作日志列表
    @RequestMapping(value = "findOperationLogList",method = RequestMethod.GET)
    public ResponseVO findOperationLogList(@RequestParam(value="userName",required = false)String userName,
                                           @RequestParam(value="ip",required = false)String ip,
                                           @RequestParam(value="startTime",required = false)Long startTime,
                                           @RequestParam(value="endTime",required = false)Long endTime){
        OperationLogDto operationLogDto = new OperationLogDto();
        if(null !=startTime && !"".equals(startTime) && null !=endTime && !"".equals(endTime)) {
            operationLogDto.setStartTime(new Date(startTime));
            operationLogDto.setEndTime(new Date(endTime));
        }
        operationLogDto.setIpAddress(ip);
        operationLogDto.setUserName(userName);
        List<OperationLogDto>operationLogDtos = operationLogService.findAll(operationLogDto);
        return new ResponseVO(operationLogDtos);
    }
    //操作日志列表
    @RequestMapping(value = "getOperationLogById/{id}",method = RequestMethod.GET)
    public ResponseVO findOperationLogList(@PathVariable(value = "id")Long id){
        OperationLogDto operationLogDtos = operationLogService.getOperationLogDtoById(id);
        return new ResponseVO(operationLogDtos);
    }


}
