package com.wizinno.jas.device.service;

import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.device.service.dto.DeviceDto;

import java.util.List;

public interface DeviceService {
    //通过设备sn查询设备
    DeviceDto getDeviceBySn(String sn);

    //查询所有设备
    List<DeviceDto> getAllDevices();
    //后台根据条件查询设备
    List<DeviceDto> getAllDevices(DeviceDto deviceDto);
    //将设备二维码更新到数据库
    ResponseVO updateCode();

    //查询我的设备
    ResponseVO getDevice(Long userId);
    List<DeviceDto> getDeviceList(Long userId);
    //查询设备详情
    ResponseVO  getDeviceDetail(Long deviceId);

    //绑定设备在设备表里插入用户id
    ResponseVO insertUserId(DeviceDto deviceDto);

    //解除绑定在设备表里的用户id=null
    ResponseVO removeUserId(Long deviceId);

    //将二维码更新到设备表

    void updataCode (DeviceDto deviceDto);
    //后台添加设备
    void insertDevice(DeviceDto deviceDto);





}
