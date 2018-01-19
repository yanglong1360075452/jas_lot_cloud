package com.wizinno.jas.device.service;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.device.domain.DeviceMapper;
import com.wizinno.jas.device.domain.DeviceRecordMapper;
import com.wizinno.jas.device.domain.model.Device;
import com.wizinno.jas.device.domain.model.DeviceRecord;
import com.wizinno.jas.device.service.dto.DeviceDto;
import com.wizinno.jas.device.service.dto.DeviceRecordDto;
import com.wizinno.jas.device.util.Device_dto;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDto;
import com.wizinno.jas.user.service.dto.UserPatientDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceRecordServiceImpl implements  DeviceRecordService {
   @Autowired
   private DeviceRecordMapper deviceRecordMapper;
   @Autowired
   private PatientService patientService;
   @Autowired
   private UserService userService;
    @Override
    public ResponseVO insertDeviceRecord(DeviceRecordDto deviceRecordDto) {
        DeviceRecord deviceRecord = new DeviceRecord();
        BeanUtils.copyProperties(deviceRecordDto,deviceRecord);
        return new ResponseVO(deviceRecordMapper.insert(deviceRecord));
    }

    @Override
    public ResponseVO updateDeviceRecord(DeviceRecordDto deviceRecordDto) {
        DeviceRecord deviceRecord = new DeviceRecord();
        BeanUtils.copyProperties(deviceRecordDto,deviceRecord);
        return new ResponseVO(deviceRecordMapper.updateByPrimaryKey(deviceRecord));
    }

    @Override
    public List<DeviceRecordDto> getDeviceRecordListByDeviceId(Long deviceId) {
        List<DeviceRecordDto>deviceRecordDtoList = deviceRecordMapper.getDeviceRecordListByDeviceId(deviceId);
        if(null !=deviceRecordDtoList && deviceRecordDtoList.size()>0){
            for (DeviceRecordDto deviceRecordDto:deviceRecordDtoList){
                UserPatientDto userPatientDto = patientService.getPatientByPatientId(deviceRecordDto.getPatientId());
                if(null !=userPatientDto){
                    deviceRecordDto.setUserName(userPatientDto.getRealName());
                }
            }
        }
        return deviceRecordDtoList;
    }
}
