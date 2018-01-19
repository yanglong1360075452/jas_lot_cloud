package com.wizinno.jas.user.service;


import com.wizinno.jas.common.data.PageData;
import com.wizinno.jas.user.service.dto.OperationLogDto;
import com.wizinno.jas.user.service.dto.UserDoctorsDto;
import com.wizinno.jas.user.service.dto.UserDto;

import java.util.List;


/**
 * Created by LiuMei on 2017-07-26.
 */
public interface OperationLogService {
    int createLog(OperationLogDto operationLogDto);

    List<OperationLogDto> findAll(OperationLogDto operationLogDto);

    void deleteOperationLogDtoById(Long id);
    OperationLogDto getOperationLogDtoById(Long id);

}
