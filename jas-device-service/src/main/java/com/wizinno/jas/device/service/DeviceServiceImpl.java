package com.wizinno.jas.device.service;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.common.wechat.AccessToken;
import com.wizinno.jas.common.wechat.WeChatUtil;
import com.wizinno.jas.common.wechat.WechatJsonObject;
import com.wizinno.jas.device.domain.DeviceMapper;
import com.wizinno.jas.device.domain.model.Device;
import com.wizinno.jas.device.service.dto.DeviceDto;
import com.wizinno.jas.device.util.Device_dto;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements  DeviceService {
    @Autowired
   private DeviceMapper deviceMapper;
    @Override
    public DeviceDto getDeviceBySn(String sn) {
        DeviceDto deviceDto=new DeviceDto();
       Device device= deviceMapper.getDeviceBySn(sn);
       if(device==null){
           throw new CustomException(CustomExceptionCode.DNotExist);
       }
       BeanUtils.copyProperties(device,deviceDto);
        return deviceDto;
    }

    @Override
    public List<DeviceDto> getAllDevices() {
        DeviceDto deviceDto=null;
        List<Device> devices=deviceMapper.selectAll();
        return Device_dto.device_dto(devices);
    }

    @Override
    public List<DeviceDto> getAllDevices(DeviceDto deviceDto) {
        return deviceMapper.selectAlls(deviceDto);
    }

    @Override
    public ResponseVO updateCode() {

    return null;
    }

    @Override
    public ResponseVO getDevice(Long userId) {
       List<Device> list= deviceMapper.getDevice(userId);
        return new ResponseVO(Device_dto.device_dto(list));
    }

    @Override
    public List<DeviceDto> getDeviceList(Long userId) {
        List<Device> list= deviceMapper.getDevice(userId);
        List<DeviceDto>deviceDtoList = new ArrayList<DeviceDto>();
        for(Device device:list){
            DeviceDto deviceDto = new DeviceDto();
            BeanUtils.copyProperties(device,deviceDto);
            deviceDtoList.add(deviceDto);
        }
        return deviceDtoList;
    }


    @Override
    public ResponseVO getDeviceDetail(Long deviceId) {
        DeviceDto deviceDto=new DeviceDto();
       Device device= deviceMapper.getDeviceDetail(deviceId);
       if(device!=null){
           BeanUtils.copyProperties(device,deviceDto);
       }
      return new ResponseVO(deviceDto);

    }

    @Override
    public ResponseVO insertUserId(DeviceDto deviceDto) {
        Device device=new Device();
        BeanUtils.copyProperties(deviceDto,device);
        deviceMapper.updateByPrimaryKey(device);
        return new ResponseVO();
    }

    @Override
    public ResponseVO removeUserId(Long deviceId) {
        deviceMapper.removeUserId(deviceId);
        return new ResponseVO();
    }

    @Override
    public void updataCode(DeviceDto deviceDto) {
        Device device=new Device();
        BeanUtils.copyProperties(deviceDto,device);
        deviceMapper.updateByPrimaryKey(device);
    }

    @Override
    public void insertDevice(DeviceDto deviceDto) {
        Device device = new Device();
        BeanUtils.copyProperties(deviceDto,device);
        deviceMapper.insert(device);
    }
}
