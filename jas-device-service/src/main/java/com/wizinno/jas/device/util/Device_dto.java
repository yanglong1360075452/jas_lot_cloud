package com.wizinno.jas.device.util;

import com.wizinno.jas.device.domain.model.Device;
import com.wizinno.jas.device.service.dto.DeviceDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Device_dto {
   public static List<DeviceDto> device_dto(List<Device> list){
       DeviceDto deviceDto=null;
       List<DeviceDto>lists=new ArrayList<>();
       if(list!=null&&list.size()>0){
           for(Device device:list){
               deviceDto=new DeviceDto();
               BeanUtils.copyProperties(device,deviceDto);
               lists.add(deviceDto);
           }

       }
       return lists;
   }
}
