package com.wizinno.jas.device.service;

import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.device.service.dto.DeviceDto;
import com.wizinno.jas.device.service.dto.DeviceRecordDto;

import javax.xml.ws.Response;
import java.util.List;

public interface DeviceRecordService {
    //添加设备履历
    ResponseVO insertDeviceRecord(DeviceRecordDto deviceRecordDto);
    //修改设备履历信息
    ResponseVO updateDeviceRecord(DeviceRecordDto deviceRecordDto);
    //根据设备Id查询设备履历列表
    List<DeviceRecordDto> getDeviceRecordListByDeviceId(Long deviceId);

}
