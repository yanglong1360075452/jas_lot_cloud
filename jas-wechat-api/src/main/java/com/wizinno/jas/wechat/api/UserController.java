package com.wizinno.jas.wechat.api;
import com.wizinno.jas.application.service.ApplicationService;
import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.common.util.FileUtil;
import com.wizinno.jas.common.util.WechatGetImgUtil;
import com.wizinno.jas.common.wechat.AccessToken;
import com.wizinno.jas.common.wechat.WeChatUtil;
import com.wizinno.jas.user.service.DoctorService;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuMei on 2017-07-26.
 */
@RestController
@RequestMapping("/api/wechat/user/")
public class UserController extends BaseController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private PatientService patientService;


    String path = "D:";// 文件路径



    /**
     *完善用户信息
     * @param code
     * @param userDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseVO updateUser(@RequestParam(value = "code",required = false) Integer code,@RequestBody UserDto userDto) throws Exception {
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        ResponseEntity responseEntity = null;
        String imgUrl="";
        String realName=null;
        if(null == user){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        if(!"".equals(userDto.getHeadPortrait()) && null !=userDto.getHeadPortrait()) {
            AccessToken accessToken = WeChatUtil.getAccessToken();
            new File(path+user.getHeadPortrait()).delete();
            try {
                imgUrl= WechatGetImgUtil.saveImageToDisk(accessToken.getToken(), userDto.getHeadPortrait(), userDto.getHeadPortrait(), path+"//headImg//");
                user.setHeadPortrait(imgUrl);
                File file = new File(imgUrl);
                byte[] bytes = FileUtils.readFileToByteArray(file);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //user.setHeadPortrait("D:"+path + userDto.getHeadPortrait() + ".jpg");
        }
        //user.setHeadPortrait(userDto.getHeadPortrait());
        user.setBirthDate(userDto.getBirthDate());
        user.setSex(userDto.getSex());
        if(null !=userDto.getRealName()){
            user.setRealName(userDto.getRealName());
        }
        //如果医院code为空则是病人，否则是医生
        DoctorDto doctorDto = null;
        if(code != null){
            doctorDto = doctorService.getDoctorByUserId(user.getId());
            if(null != doctorDto){
                if(null !=userDto.getProfessiona()){
                    doctorDto.setProfessiona(userDto.getProfessiona());
                }
                if(null !=userDto.getDescription()){
                    doctorDto.setDescription(userDto.getDescription());
                }
                doctorDto.setHospital(code);
                if(doctorDto.getStatus()!=1){
                    doctorDto.setStatus(0);//状态0为未审核，1同意，2拒绝
                }
                doctorService.updateDoctor(doctorDto);
            }
        }
        userService.updateUser(user);
        user.setResponseEntity(responseEntity);
        return new ResponseVO(user);
    }
    //修改手机号第一步、判断验证码是否有效
    @RequestMapping(value = "/vaildCaptcha", method = RequestMethod.GET)
    public ResponseVO vaildCaptcha(@RequestParam(value = "captcha") String captcha) throws CustomException {
        Map<String,String> result = new HashMap<>();
        if(captcha.equals("8888")){
            result.put("result","0");
        }else{
            result.put("result","验证码错误");
        }
        return new ResponseVO(result);
    }
    //修改手机号第二步、判断手机号是否唯一、验证码是否正确
    @RequestMapping(value = "/updatePhone", method = RequestMethod.GET)
    public ResponseVO updatePhone(@RequestParam(value = "phone") String phone,@RequestParam(value = "captcha") String captcha) throws CustomException {
        Map<String,String> result = new HashMap<>();
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        if(null == user){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        UserDto userDto = userService.getUserByPhone(phone);
        if(null == userDto){
            result.put("result","0");
            if(captcha.equals("8888")){
                user.setPhone(phone);
                result.put("result","修改成功");
                userService.updateUser(user);
            }else{
                result.put("result","验证码错误");
            }
        }else{
            result.put("result","该手机号已存在");
        }
        return new ResponseVO(result);
    }




    /**
     *根据ID查询用户信息
     * @return
     */
    @RequestMapping(value="/userDetail" ,method = RequestMethod.GET )
    @ResponseBody
    public ResponseVO userDetail(@RequestParam(value="userId",required = false)Long userId) throws CustomException {
        ResponseEntity responseEntity = null;
        UserDto user = new UserDto();
        if(null == userId){
            String openId = (String) request.getAttribute("openId");
            user = userService.getUserByOpenId(openId);
            //String headImg = WechatGetImgUtil.GetImageStrFromPath(user.getHeadPortrait());
            if(!"".equals(user.getHeadPortrait())) {
                File file = new File(path+user.getHeadPortrait());
                byte[] bytes = new byte[0];
                try {
                    bytes = FileUtils.readFileToByteArray(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
                user.setResponseEntity(responseEntity);
            }
        }else{
            user = userService.getUserByUserId(userId);
            if(null == user){
                throw new CustomException(CustomExceptionCode.RecordNotExist);
            }
            if(!"".equals(user.getHeadPortrait())) {
                File file = new File(path+user.getHeadPortrait());
                byte[] bytes = new byte[0];
                try {
                    bytes = FileUtils.readFileToByteArray(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
                user.setResponseEntity(responseEntity);
            }
        }
        return new ResponseVO(user);
    }

    /**
     *根据ID查询医生信息
     * @return
     */
    @RequestMapping(value="/doctorDetail" ,method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO doctorDetail() throws CustomException {
        ResponseEntity responseEntity = null;
        String openId = (String)request.getAttribute("openId");
        UserDto userDto = userService.getUserByOpenId(openId);
        if(null == userDto){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        UserDoctorsDto userDoctorsDto = userService.selectUdoctorById(userDto.getId());
        File file = new File(userDto.getHeadPortrait());
        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
        userDoctorsDto.setResponseEntity(responseEntity);
        return new ResponseVO(userDoctorsDto);
    }


    //通过医院选择康复师
    @RequestMapping(value = "/doctor/{code}",method = RequestMethod.GET)
    public ResponseVO getDoctorsByHospital(@PathVariable(value = "code")Integer code){

        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        PatientDto patientDto = patientService.getPatientByUserID(user.getId());

        if (patientDto == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
            if (code <= 0){
                throw new CustomException(CustomExceptionCode.ErrorParam);
            }
          List<UserDoctorDto>  doctorDtos=doctorService.getDoctorsByHospital(code);
//
//            for (UserDoctorDto dto:doctorDtos){
//                UserDoctorDto dto1= doctorService.getDoctorAndPatientStatus(dto.getDoctorId(),patientDto.getId());
//                if (dto1 != null){
//                    dto.setStatus(dto1.getStatus());
//                }
//
//            }
        return new ResponseVO(doctorDtos);

    }



    //通过病人id获取病人信息
    @RequestMapping(value = "/{patientId}",method = RequestMethod.GET)
    public ResponseVO getPatientByPatientId(@PathVariable("patientId")Long patientId){
        ResponseEntity responseEntity = null;
        UserPatientDto dto=patientService.getPatientByPatientId(patientId);
        File file = new File(path + dto.getHeadPortrait());
        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        responseEntity = new ResponseEntity(bytes, httpHeaders, HttpStatus.CREATED);
        dto.setResponseEntity(responseEntity);
        return new ResponseVO(dto);
    }
    //审核康复师
    @RequestMapping(value = "upp",method = RequestMethod.GET)
    public ResponseVO upp(@RequestParam(value = "userId")Long userId,@RequestParam(value = "status")int status){
        DoctorDto doctorDto = doctorService.getDoctorByUserId(userId);
        if(null != doctorDto){
            doctorDto.setStatus(status);
            doctorService.updateDoctor(doctorDto);
        }
        return new ResponseVO();
    }


    //删除用户
    //审核康复师
    @RequestMapping(value = "del",method = RequestMethod.GET)
    public ResponseVO upp(@RequestParam(value = "userId")Long userId){
        DoctorDto doctorDto = doctorService.getDoctorByUserId(userId);
        if(null != doctorDto){
            doctorService.deleteDoctorById(doctorDto.getId());
            userService.deleteUserById(userId);
        }
        PatientDto patientDto = patientService.getPatientByUserID(userId);
        if(null != patientDto){
            patientService.deletePatientById(patientDto.getId());
            userService.deleteUserById(userId);
        }
        return new ResponseVO();
    }

}
