package com.wizinno.jas.wechat.api;

import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.device.service.DeviceRecordService;
import com.wizinno.jas.device.service.DeviceService;
import com.wizinno.jas.device.service.dto.DeviceDto;
import com.wizinno.jas.device.service.dto.DeviceRecordDto;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDto;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/wechat/device/")
public class DeviceController {

@Autowired
   private  DeviceService deviceService;
@Autowired
   private HttpServletRequest request;
@Autowired
   private UserService userService;
@Autowired
    private DeviceRecordService deviceRecordService;
@Autowired
private PatientService patientService;
    /**
     *查看我的设备
     * @return
     */
    @RequestMapping(value="/queryDevice",method = RequestMethod.POST)
    public ResponseVO queryDevice(){
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        return deviceService.getDevice(user.getId());
    }
    /**
     *查看我的设备详情
     * @param  deviceId
     * @return
     */
    @RequestMapping(value="/deviceDetail",method = RequestMethod.POST)
    public ResponseVO deviceDetail (@RequestParam (value="deviceId",required = true)Long deviceId){
        if(deviceId<=0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        return deviceService.getDeviceDetail(deviceId);

    }
    /**
     *绑定设备
     * @param  sn
     * @return
     */
    @RequestMapping(value="/bindDevice",method = RequestMethod.POST)
    public ResponseVO bindDevice(@RequestParam(value="sn",required = true)String sn){
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        if(null == user){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        if(null == sn){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        DeviceDto deviceDto = deviceService.getDeviceBySn(sn);
        if(null == deviceDto){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        deviceDto.setUserId(user.getId());
        deviceDto.setUpdateTime(new Date());
        deviceService.updataCode(deviceDto);
        PatientDto patientDto = patientService.getPatientByUserID(user.getId());
        if(null !=patientDto) {
            DeviceRecordDto deviceRecordDto = new DeviceRecordDto();
            deviceRecordDto.setDeviceId(deviceDto.getId());
            deviceRecordDto.setPatientId(patientDto.getId());
            deviceRecordDto.setEvent("绑定设备");
            deviceRecordDto.setCreateTime(new Date());
            deviceRecordService.insertDeviceRecord(deviceRecordDto);
        }
        return new ResponseVO();
    }
    /**
     *解除绑定设备
     * @param  deviceId
     * @return
     */
    @RequestMapping(value = "/removeDevice",method=RequestMethod.POST)
    public ResponseVO removeDevice(@RequestParam(value="deviceId",required = true)Long deviceId){
        if(deviceId<=0){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        String openId = (String) request.getAttribute("openId");
        UserDto user = userService.getUserByOpenId(openId);
        if(null == user){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        PatientDto patientDto = patientService.getPatientByUserID(user.getId());
        if(null !=patientDto) {
            DeviceRecordDto deviceRecordDto = new DeviceRecordDto();
            deviceRecordDto.setDeviceId(deviceId);
            deviceRecordDto.setPatientId(patientDto.getId());
            deviceRecordDto.setEvent("解绑设备");
            deviceRecordDto.setCreateTime(new Date());
            deviceRecordService.insertDeviceRecord(deviceRecordDto);
        }
        return deviceService.removeUserId(deviceId);
    }
}
