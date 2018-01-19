package com.wizinno.jas.controller;
import com.wizinno.jas.common.data.PageData;
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
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDto;
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
@RequestMapping("manageWebapp/api/manage/device")
public class DevicesController {
    private static  final String getCodeUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceRecordService deviceRecordService;
    @Autowired
    private PatientService patientService;
    /**
     * 根据设备sn生成二维码，并把二维码地址保存到设备表
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getCode (){
        String codeUrl;
        String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
        AccessToken accessToken= WeChatUtil.getAccessToken();
        List<DeviceDto> deviceDtos=deviceService.getAllDevices();
        if(deviceDtos!=null&&deviceDtos.size()>0){
            for(int i=0;i<deviceDtos.size();i++){
               DeviceDto deviceDto= deviceDtos.get(i);
                String str=deviceDto.getSn();
                String postData="{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\":\""+str+"\"}}}";
                JSONObject jsonTicket= WechatJsonObject.getJson(url,accessToken.getToken(),postData);
                String ticket=jsonTicket.getString("ticket");
                codeUrl=getCodeUrl.replace("TICKET",ticket);
                //将图片保存到服务器
                try {
                    URL u=new URL( codeUrl);
                    InputStream in = u.openStream();
                    String path="D:\\apache-tomcat-9.0\\img";
                    File sf=new File(path);
                    String realpath=sf.getPath()+"\\"+str+".jpg";
                    FileOutputStream fs=new FileOutputStream(realpath);
                    byte[] buf=new byte[1024];
                    int r;
                    while((r=in.read(buf))>0)
                        fs.write(buf,0,r);
                    in.close();
                    fs.close();
                    //将二维码地址保存到设备表
                    deviceDto.setCodeUrl(realpath);
                    deviceService.updataCode(deviceDto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //后台查询所有设备列表
    @RequestMapping(value = "findDeviceList",method = RequestMethod.GET)
    public ResponseVO getDeviceList(@RequestParam(value = "deviceName",required = false)String deviceName,
                                    @RequestParam(value = "patientName",required = false)String patientName,
                                    @RequestParam(value = "status",required = false)Integer status){
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setConfig(deviceName);
        deviceDto.setPatientName(patientName);
        deviceDto.setStatus(status);
        List<DeviceDto> deviceDtoList = deviceService.getAllDevices(deviceDto);
        return new ResponseVO(deviceDtoList);
    }
    //后台查询用户绑定的设备列表
    @RequestMapping(value = "getDeviceList/{id}",method = RequestMethod.GET)
    public ResponseVO getDeviceListById(@PathVariable(value="id")Long id){
        List<DeviceDto> deviceDtoList = deviceService.getDeviceList(id);
        return new ResponseVO(deviceDtoList);
    }
    //后台查询用户绑定的设备详情
    @RequestMapping(value = "getDeviceDetail/{id}",method = RequestMethod.GET)
    public ResponseVO getDeviceDetail(@PathVariable(value="id")Long id){
        return new ResponseVO(deviceService.getDeviceDetail(id));
    }

    //后台查询设备履历列表
    @RequestMapping(value = "getDeviceRecordList/{deviceId}",method = RequestMethod.GET)
    public ResponseVO getDeviceRecordList(@PathVariable(value="deviceId")Long deviceId){
        return new ResponseVO(deviceRecordService.getDeviceRecordListByDeviceId(deviceId));
    }

    //后台修改设备信息
    @RequestMapping(value = "updateDevice",method = RequestMethod.PUT)
    public ResponseVO updateDevice(@RequestBody DeviceDto deviceDto){
        if(deviceDto.getStatus() == 1){
            PatientDto patientDto = patientService.getPatientByUserID(deviceDto.getUserId());
            if(null !=patientDto) {
                DeviceRecordDto deviceRecordDto = new DeviceRecordDto();
                deviceRecordDto.setDeviceId(deviceDto.getId());
                deviceRecordDto.setPatientId(patientDto.getId());
                deviceRecordDto.setEvent("解绑设备");
                deviceRecordDto.setCreateTime(new Date());
                deviceRecordService.insertDeviceRecord(deviceRecordDto);
            }
            deviceService.updataCode(deviceDto);
            deviceService.removeUserId(deviceDto.getId());
        }else{
            deviceService.updataCode(deviceDto);
        }
        return new ResponseVO();
    }

    //后台批量导入设备
    @RequestMapping(value = "importDevice",method = RequestMethod.POST)
    public ResponseVO importDevice(@RequestBody DeviceDto[] deviceDto){
        if(deviceDto.length>0 && deviceDto!=null){
            for(int i=0;i<deviceDto.length;i++){
                if(null !=deviceDto[i].getSn() && !"".equals(deviceDto[i].getSn())) {
                    deviceDto[i].setCreateTime(new Date());
                    deviceService.insertDevice(deviceDto[i]);
                }
            }
        }else{
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        return new ResponseVO();
    }



}
