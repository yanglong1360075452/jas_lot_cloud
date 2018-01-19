package com.wizinno.jas.wechat.api;

import com.wizinno.jas.common.config.Config;
import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.common.util.JwtUtil;
import com.wizinno.jas.common.util.MessageService;
import com.wizinno.jas.common.util.Util;
import com.wizinno.jas.score.service.ScoreService;
import com.wizinno.jas.score.service.dto.ScoreDto;
import com.wizinno.jas.user.service.DoctorService;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.data.UserTypeEnum;
import com.wizinno.jas.user.service.dto.DoctorDto;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDto;
import com.wizinno.jas.wechat.api.vo.RegisterVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuMei on 2017-07-26.
 */
@RestController
@RequestMapping("/api/wechat")
public class RegisterController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ScoreService scoreService;


    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/authCode/{phone}",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO sendCode(@PathVariable(value = "phone")String phone){
/*
        if (!Util.phoneNumber(phone)){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        try {
            String s = MessageService.sendMsg(phone);
            if (s.equals("success")) {
                log.info("发送验证码给" + phone + "成功");
            }else {
                log.info("发送验证码给" + phone + "失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        return new ResponseVO();


    }

    /**
     * 用户注册
     * @param
     * @return
     * @throws CustomException
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO register(@RequestBody RegisterVO registerVO) throws CustomException {
        String openId = registerVO.getOpenId();
        String realName = registerVO.getRealName();
        String phone = registerVO.getPhone();
        String captcha = registerVO.getCaptcha();
        Integer type = registerVO.getType();

        if (phone != null) {
            if (captcha == null) {
               throw new CustomException(CustomExceptionCode.ErrorParam);
           }
            if (captcha.equals("8888")){
                log.info(phone+"验证码验证成功");
            }else {
                throw new CustomException(CustomExceptionCode.CaptchaError);
            }
/*           try {
                   String str = MessageService.checkMsg(phone,captcha);
                   if("success".equals(str)){
                       log.info(phone+"验证码验证成功");
                   }else{
                        log.info(phone+"验证码验证失败");
                        throw new CustomException(CustomExceptionCode.CaptchaError);
                    }
           } catch (IOException e) {
               throw new CustomException(CustomExceptionCode.CaptchaError);
            }*/
       }

        if(StringUtils.isBlank(openId) || StringUtils.isBlank(realName)){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Date now = new Date();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(registerVO,userDto);
        userDto.setCreateTime(now);
        userDto.setUpdateTime(now);
        UserDto userOpenId = userService.getUserByOpenId(registerVO.getOpenId());
        Map<String,String> result = new HashMap<>();
        if (userOpenId == null){
            int i = userService.saveUser(userDto);
            if (i == 1){
                DoctorDto doctorDto = new DoctorDto();
                UserDto user = userService.getUserByOpenId(userDto.getOpenId());
                userType(type,user);
                //如果注册类型为1 则是医生注册
                if(type==1){
                   // doctorDto.setUserId(user.getId());
                   // doctorDto.setStatus(0);
                    result.put("status","0");
                   // doctorService.saveDoctor(doctorDto);
                }
                //用户注册添加积分，并修改用户总积分
                Integer sc= 0;
                Integer sco = 0;
                ScoreDto scoreDto = new ScoreDto();
                scoreDto.setScoreChange(1);
                scoreDto.setUserId(user.getId());
                sco = user.getScoreValue();//用户的总积分
                sc  = sco+1;
                scoreDto.setChangeValue("+1");
                user.setScoreValue(sc);
                scoreDto.setSurplusScore(Long.valueOf(sc));
                scoreDto.setCreateTime(new Date());
                userService.updateUser(user);
                scoreService.createScore(scoreDto);
            }
        }else {
                userType(type,userOpenId);
        }

        Map<String,String> info = new HashMap<>();
        info.put("openId",openId);
        info.put("phone",registerVO.getPhone());
        String subject = JwtUtil.generalSubject(info);
        String token = "";
        try {
            token = jwtUtil.createJWT(Config.jwtId, subject, Config.jwtExpire);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("token", token);
        return new ResponseVO(result);
    }

    private void userType(Integer type, UserDto userDto){
        PatientDto patientDto=null;
        DoctorDto doctorDto=null;
        if(type == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if (type.equals(UserTypeEnum.patient.toCode())){
            if (patientService.getPatientByUserID(userDto.getId()) == null){
                patientDto=new PatientDto();
                patientDto.setUserId(userDto.getId());
                patientService.savePatient(patientDto);
            }else {
                throw new CustomException(CustomExceptionCode.PatientExistence);
            }

        }
        if (type.equals(UserTypeEnum.Doctor.toCode())){
            if (doctorService.getDoctorByUserId(userDto.getId()) == null){
                doctorDto = new DoctorDto();
                doctorDto.setUserId(userDto.getId());
                doctorDto.setStatus(0);
                doctorService.saveDoctor(doctorDto);
            }else {
                throw new CustomException(CustomExceptionCode.DoctorExistence);
            }

        }

    }
}

