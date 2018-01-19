package com.wizinno.jas.exercise.api;
import com.wizinno.jas.application.service.ApplicationService;
import com.wizinno.jas.application.service.data.ApplicationStatus;
import com.wizinno.jas.application.service.dto.ApplicationDto;
import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.dict.domain.model.DataDict;
import com.wizinno.jas.dict.service.DataDictService;
import com.wizinno.jas.dict.service.data.ParamKey;
import com.wizinno.jas.dict.service.dto.DataDictDto;
import com.wizinno.jas.dict.service.dto.HospitalDto;
import com.wizinno.jas.exercise.api.vo.ExerciseProcessVO;
import com.wizinno.jas.exercise.service.ExerciseProcessService;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessDoctorDto;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessDto;
import com.wizinno.jas.exercise.service.dto.ExerciseProcessPatientDto;
import com.wizinno.jas.user.service.DoctorService;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.DoctorDto;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDoctorDto;
import com.wizinno.jas.user.service.dto.UserDto;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by HP on 2017/8/14.
 */
@RestController
@RequestMapping("/api/wechat/exerciseProcess")
public class ExerciseProcessController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ExerciseProcessService exerciseProcessService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private DataDictService dictDataService;

    String path = "D:";// 文件路径
    //创建训练计划
    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO createExerciseProcess(@RequestParam(value = "patientId",required = false)Long patientId,
                                            @RequestBody ExerciseProcessVO exerciseProcessVO){
        Date date = new Date();
        PatientDto patientDto =null;
        if (patientId == null){
            String openId = (String) request.getAttribute("openId");
            UserDto user = userService.getUserByOpenId(openId);
             patientDto = patientService.getPatientByUserID(user.getId());
            if (patientDto == null){
                throw new CustomException(CustomExceptionCode.ErrorParam);
            }
            exerciseProcessVO.setCreateBy(patientDto.getId());
            exerciseProcessVO.setUpdateBy(patientDto.getId());
            exerciseProcessVO.setPatientId(patientDto.getId());
        }else {
            exerciseProcessVO.setPatientId(patientId);
            patientDto = patientService.getPatientByUserID(patientId);
            exerciseProcessVO.setCreateBy(exerciseProcessVO.getDoctorId());
            exerciseProcessVO.setUpdateBy(exerciseProcessVO.getDoctorId());
        }
        Date startTime = exerciseProcessVO.getStartTime();
        Date endTime = exerciseProcessVO.getEndTime();
        Integer arthro = exerciseProcessVO.getArthro();
        Integer postion = exerciseProcessVO.getPostion();
        Integer direction = exerciseProcessVO.getDirection();
        Long doctorId = exerciseProcessVO.getDoctorId();
        if (startTime == null || endTime == null || arthro == null || postion == null || doctorId == null || direction == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        exerciseProcessVO.setCreateTime(date);
        exerciseProcessVO.setUpdateTime(date);
        ExerciseProcessDto exerciseProcessDto = new ExerciseProcessDto();
        exerciseProcessDto.setRecord(0);
        exerciseProcessDto.setReport(0);
        exerciseProcessDto.setIsComfirmed(ApplicationStatus.NoVerify.toCode());
        BeanUtils.copyProperties(exerciseProcessVO,exerciseProcessDto);
        exerciseProcessService.createExerciseProcess(exerciseProcessDto);
        return new ResponseVO();

    }

/*    @RequestMapping(method=RequestMethod.GET)
    public ResponseVO getExerciseProcess(@RequestParam("patientId")Long patientId){
        List<ExerciseProcessDto> dtos=null;
        if (patientId != null){
            dtos=exerciseProcessService.getExerciseProcessByPatientId(patientId);
        }else {
            String openId = (String) request.getAttribute("openId");
            UserDto user = userService.getUserByOpenId(openId);
            PatientDto patientDto = patientService.getPatientByUserID(user.getId());
            dtos=exerciseProcessService.getExerciseProcessByPatientId(patientDto.getId());
        }
        return new ResponseVO();
    }*/

    //获取病人对应的康复师和训练计划
    @RequestMapping(method =RequestMethod.GET)
    public ResponseVO getDoctorsByPatientId(@RequestParam("doctorId")Long doctorId){

            String openId = (String) request.getAttribute("openId");
            UserDto user = userService.getUserByOpenId(openId);
            PatientDto patientDto = patientService.getPatientByUserID(user.getId());
            if (patientDto == null){
                throw new CustomException(CustomExceptionCode.ErrorParam);
            }

        List<ExerciseProcessDto>  dtos=exerciseProcessService.getEPByPIdAndDId(patientDto.getId(),doctorId);
        return new ResponseVO(dtos);

    }

    //更改康复计划
    @RequestMapping(method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO updateExerciseProcess(@RequestParam("epId")Long epId,@RequestBody ExerciseProcessVO exerciseProcessVO){
        Date date = new Date();
        ExerciseProcessDto dto=exerciseProcessService.getEPById(epId);

        Date startTime = exerciseProcessVO.getStartTime();
        Date endTime = exerciseProcessVO.getEndTime();
        Integer arthro = exerciseProcessVO.getArthro();
        Integer postion = exerciseProcessVO.getPostion();
        Integer direction = exerciseProcessVO.getDirection();
        Long doctorId = exerciseProcessVO.getDoctorId();

        String note = exerciseProcessVO.getNote();
        if (startTime == null || endTime == null || arthro == null || postion == null || doctorId == null || direction == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if (note != null){
            dto.setNote(note);
        }
        dto.setArthro(arthro);
        dto.setDirection(direction);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setPostion(postion);
        dto.setDoctorId(doctorId);
        dto.setUpdateBy(dto.getPatientId());
        dto.setUpdateTime(date);
        if(dto.getIsComfirmed() == 0){
            dto.setIsComfirmed(ApplicationStatus.NoVerify.toCode());
        }
        exerciseProcessService.updateExerciseProcess(dto);
        return new ResponseVO();

    }

    //删除康复计划
    @RequestMapping(value = "/{epId}",method = RequestMethod.DELETE)
    public ResponseVO deleteExerciseProcess(@PathVariable("epId")Long epId){
        if (epId < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        exerciseProcessService.deleteExerciseProcess(epId);
        return new ResponseVO();

    }


    //这个医生获取患者的康复计划
    @RequestMapping(value ="/{patientId}",method = RequestMethod.GET)
    public ResponseVO getPatientsByPatientId(@PathVariable("patientId")Long patientId){
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        DoctorDto doctor = doctorService.getDoctorByUserId(user.getId());
        if (doctor == null){
            throw new CustomException(CustomExceptionCode.UserNotExist);
        }
        List<ExerciseProcessDto> ExerciseProcessDto=exerciseProcessService.getEPByPIdAndDId(patientId,doctor.getId());
        List<ExerciseProcessDto> ExerciseProcessDtoList = new ArrayList<ExerciseProcessDto>();
        for(ExerciseProcessDto exerciseProcessDto:ExerciseProcessDto){
            if(null !=exerciseProcessDto.getIsComfirmed() && 1==exerciseProcessDto.getIsComfirmed()) {
                exerciseProcessDto.setRealName(user.getRealName());
                ExerciseProcessDtoList.add(exerciseProcessDto);
            }
        }
        return new ResponseVO(ExerciseProcessDtoList);

    }

    //这个医生获取所有患者
        @RequestMapping(value ="getPatientByPatientId/{type}",method = RequestMethod.GET)
    public ResponseVO getPatientByPatientId(@PathVariable("type")String type,@RequestParam("filter")String filter){
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        ResponseEntity responseEntity = null;
        Map<String ,Object>map = new HashMap<>();
        DoctorDto doctor = doctorService.getDoctorByUserId(user.getId());
        if (doctor == null){
            throw new CustomException(CustomExceptionCode.UserNotExist);
        }
        map.put("filter",filter);
        map.put("doctorId",doctor.getId());
        map.put("isComfirmed",type);
        List<ExerciseProcessPatientDto> exerciseProcessPatientDtoList=exerciseProcessService.getPatientByPatientId(map);
        for(ExerciseProcessPatientDto epdd:exerciseProcessPatientDtoList) {
            File file = new File(path + epdd.getHeadPortrait());
            byte[] bytes = new byte[0];
            try {
                bytes = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
            epdd.setResponseEntity(responseEntity);
        }
        return new ResponseVO(exerciseProcessPatientDtoList);

    }

    //同意或拒绝患者申请
    @RequestMapping(value = "/updateIsComfirmed",method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO updateIsComfirmed(@RequestParam("epId")Long epId,@RequestParam("isComfirmed")Long isComfirmed){
        Date date = new Date();
        ExerciseProcessDto dto=exerciseProcessService.getEPById(epId);
        //根据医生和病人id查询康复计划
//        List<ExerciseProcessDto> ExerciseProcessDto=exerciseProcessService.getEPByPIdAndDId(dto.getPatientId(),dto.getDoctorId());
//        for(ExerciseProcessDto exerciseProcessDto:ExerciseProcessDto){
//            exerciseProcessDto.setRealName(user.getRealName());
//        }
        dto.setComfirmedTime(date);
        if (isComfirmed == 1){
            dto.setIsComfirmed(ApplicationStatus.Verify.toCode());
        }
        if (isComfirmed == -1){
            dto.setIsComfirmed(ApplicationStatus.reject.toCode());
        }

        exerciseProcessService.updateExerciseProcess(dto);
        return new ResponseVO();

    }


    //通过训练计划获取医生
    @RequestMapping(value = "/doctors",method = RequestMethod.GET)
    public ResponseVO getDoctorsByEP(){
        ResponseEntity responseEntity = null;
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        PatientDto patientDto = patientService.getPatientByUserID(user.getId());
        if (patientDto == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        List<ExerciseProcessDoctorDto> doctorsByPatientId = exerciseProcessService.getDoctorsByPatientId(patientDto.getId());
        List<ExerciseProcessDoctorDto> exerciseProcessDoctorDtos = new ArrayList<ExerciseProcessDoctorDto>();
        List<HospitalDto> dictDataH = new ArrayList<HospitalDto>();
        List<DataDictDto> dataDictList = new ArrayList<DataDictDto>();
        for (ExerciseProcessDoctorDto epdd:doctorsByPatientId){
            if(epdd.getIsComfirmed()==1) {
                if(null !=epdd.getHospital()){
                    dictDataH = dictDataService.getContentToListByKey(ParamKey.Hospital.toString());
                    if(null !=dictDataH){
                        for(HospitalDto hospitalDto:dictDataH){
                            if(hospitalDto.getCode().equals(epdd.getHospital())){
                                epdd.setHospitalDesc(hospitalDto.getName());
                            }
                        }
                    }
                }
                if(null !=epdd.getDepartment()){
                    dataDictList = dictDataService.getContentToListByKey("departments");
                    if(null !=dictDataH){
                        for(DataDictDto dataDict:dataDictList){
                            if(dataDict.getCode().equals(epdd.getDepartment())){
                                epdd.setDepartmentDesc(dataDict.getName());
                            }
                        }
                    }
                }
                File file = new File(path + epdd.getHeadPortRait());
                byte[] bytes = new byte[0];
                try {
                    bytes = FileUtils.readFileToByteArray(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
                epdd.setResponseEntity(responseEntity);
                exerciseProcessDoctorDtos.add(epdd);
            }
        }
        return new ResponseVO(exerciseProcessDoctorDtos);

    }

    //病人获取训练计划列表
    @RequestMapping(value = "/eps",method = RequestMethod.GET)
    public ResponseVO getEPs(){
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        PatientDto patientDto = patientService.getPatientByUserID(user.getId());
        if (patientDto == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        List<ExerciseProcessDto> dtos = new ArrayList<ExerciseProcessDto>();
        dtos = exerciseProcessService.getExerciseProcessByPatientId(patientDto.getId());
        //List<ExerciseProcessDto> exerciseProcessDtos = new ArrayList<ExerciseProcessDto>();
        for (ExerciseProcessDto dto:dtos){
            UserDoctorDto doctorDto=doctorService.getDoctorByDId(dto.getDoctorId());
            //UserDoctorDto userDoctorDto = doctorService.getDoctorAndPatientStatus(dto.getDoctorId(), patientDto.getId());
            if(null != doctorDto) {
                dto.setRealName(doctorDto.getRealName());
               // dto.setStatus(userDoctorDto.getStatus());
                //exerciseProcessDtos.add(dto);
            }
        }
        return new ResponseVO(dtos);
    }

    //获取所有康复计划列表
    @RequestMapping(value = "/exerciseProcessList",method = RequestMethod.GET)
    public ResponseVO getexerciseProcessList(){
        return new ResponseVO();
    }



}
