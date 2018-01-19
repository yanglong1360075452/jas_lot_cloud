package com.wizinno.jas.doMessage;
import com.google.gson.Gson;
import com.wizinno.jas.device.service.DeviceRecordService;
import com.wizinno.jas.device.service.DeviceService;
import com.wizinno.jas.device.service.dto.DeviceDto;
import com.wizinno.jas.device.service.dto.DeviceRecordDto;
import com.wizinno.jas.message.resp.TextMessage;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDto;
import com.wizinno.jas.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * User:
 * Date:
 * Time:
 */
@Service
public class DoMessage {
    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    @Autowired
   private UserService userService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DeviceRecordService deviceRecordService;
    public String processRequest(HttpServletRequest request) {

        String respMessage = null;
        try {
            Gson gson=new Gson();
            String respContent = null;
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            String fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            String createTime=requestMap.get("CreateTime");
            String msgType=requestMap.get("MsgType");
            String event=requestMap.get("Event");
            String eventKey=  requestMap.get("EventKey");
            String picurl=requestMap.get("PicUrl");
            if(eventKey!=null){
                if(eventKey.length()>7){
                    if(eventKey.substring(0,8).equals("qrscene_")){
                        eventKey=eventKey.substring(8,eventKey.length());
                    }
                }
            }

            System.out.println("这里 "+eventKey);
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);
            if(event!=null){
                if(event.equals("subscribe")||event.equals("SCAN")){
//                    respContent="训练前扫一扫，才能上传您的数据哦。";
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                    //保存数据到中间表wxDetialData表
                    if(!eventKey.equals("")){
                        //fromUserName为openid
                        UserDto user = userService.getUserByOpenId(fromUserName);
                        if(null != user) {
                            //将用户id保存设备表
                            DeviceDto deviceDto = deviceService.getDeviceBySn(eventKey);
                            if(null !=deviceDto){
                                deviceDto.setUserId(user.getId());
                                deviceDto.setUpdateTime(new Date());
                                deviceService.updataCode(deviceDto);
                                //添加设备履历
                                PatientDto patientDto = patientService.getPatientByUserID(user.getId());
                                if(null !=patientDto) {
                                    DeviceRecordDto deviceRecordDto = new DeviceRecordDto();
                                    deviceRecordDto.setDeviceId(deviceDto.getId());
                                    deviceRecordDto.setPatientId(patientDto.getId());
                                    deviceRecordDto.setEvent("绑定设备");
                                    deviceRecordDto.setCreateTime(new Date());
                                    deviceRecordService.insertDeviceRecord(deviceRecordDto);
                                }
                            }
                        }else{
                            respContent="请先注册病人用户，才能绑定设备";
                        }
                    }else{
                        respContent="关注我们的公众号才能绑定设备哦";
                        textMessage.setContent(respContent);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }
}
