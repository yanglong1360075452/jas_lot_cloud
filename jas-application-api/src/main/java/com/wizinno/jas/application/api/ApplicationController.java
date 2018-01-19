package com.wizinno.jas.application.api;
import com.wizinno.jas.application.api.VO.ApplicationVO;
import com.wizinno.jas.application.service.ApplicationService;
import com.wizinno.jas.application.service.data.ApplicationStatus;
import com.wizinno.jas.application.service.dto.ApplicationDto;
import com.wizinno.jas.application.service.dto.ApplicationPatientDto;
import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
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
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */
@RestController
@RequestMapping("/api/wechat/application")
public class ApplicationController extends BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
   private ApplicationService applicationService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;


    //病人添加康复师
    @RequestMapping(value = "/{doctorId}",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO createApplication(@PathVariable("doctorId")Long doctorId){

        Date date = new Date();
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        PatientDto Pdto = patientService.getPatientByUserID(user.getId());
        ApplicationVO applicationVO = new ApplicationVO();

        if (Pdto == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }else {
            applicationVO.setPatientId(Pdto.getId());
        }
        if (doctorId == null || doctorId < 0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        applicationVO.setDoctorId(doctorId);
        applicationVO.setStatus(ApplicationStatus.NoVerify.toCode());
        applicationVO.setCreatetime(date);

       ApplicationDto dto= new ApplicationDto();
        BeanUtils.copyProperties(applicationVO,dto);

        applicationService.createApplication(dto);

        return new ResponseVO();
    }


    //康复师查询患者
    @RequestMapping(method = RequestMethod.GET)
    public ResponseVO getApplications(@RequestParam("filter") String filter){

        Date date = new Date();
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        DoctorDto doctor = doctorService.getDoctorByUserId(user.getId());
        if (doctor == null){
            throw new CustomException(CustomExceptionCode.UserNotExist);
        }

        List<ApplicationPatientDto> apds=applicationService.getApplications(doctor.getId(),filter);


        return new ResponseVO(apds);
    }

    //康复师同意或拒绝
    @RequestMapping(value = "/{patientId}",method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO verify(@PathVariable("patientId")Long patientId,@RequestParam("type")Integer type){

        Date date = new Date();
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        DoctorDto doctor = doctorService.getDoctorByUserId(user.getId());
        if (doctor == null){
            throw new CustomException(CustomExceptionCode.UserNotExist);
        }

            ApplicationDto dto=applicationService.getApplicationByPIdAndDId(patientId,doctor.getId());
        if (type == 2){
            dto.setStatus(ApplicationStatus.Verify.toCode());
        }
        if (type == 3){
            dto.setStatus(ApplicationStatus.reject.toCode());
        }

            applicationService.updateApplication(dto);

        return new ResponseVO();
    }



}
