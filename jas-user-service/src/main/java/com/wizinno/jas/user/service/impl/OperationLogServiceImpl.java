package com.wizinno.jas.user.service.impl;


import com.wizinno.jas.operationLog.domain.OperationLogMapper;
import com.wizinno.jas.operationLog.domain.model.OperationLog;
import com.wizinno.jas.user.service.OperationLogService;
import com.wizinno.jas.user.service.dto.OperationLogDto;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuMei on 2017-07-26.
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public int createLog(OperationLogDto operationLogDto) {
        OperationLog operationLog = new OperationLog();
        org.springframework.beans.BeanUtils.copyProperties(operationLogDto,operationLog);
        return operationLogMapper.insert(operationLog);
    }

    @Override
    public List<OperationLogDto> findAll(OperationLogDto operationLogDto) {
        //OperationLog operationLog = new OperationLog();
        //org.springframework.beans.BeanUtils.copyProperties(operationLogDto,operationLog);
        List<OperationLogDto>operationLogList = operationLogMapper.findAll(operationLogDto);
        return operationLogList;
    }

    @Override
    public void deleteOperationLogDtoById(Long id) {

    }

    @Override
    public OperationLogDto getOperationLogDtoById(Long id) {
        OperationLogDto operationLogDto = new OperationLogDto();
        OperationLog operationLog = operationLogMapper.selectByPrimaryKey(id);
        if(operationLog !=null){
            org.springframework.beans.BeanUtils.copyProperties(operationLog,operationLogDto);
        }
        return operationLogDto;
    }
}
