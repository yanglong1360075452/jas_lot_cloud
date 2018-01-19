package com.wizinno.jas.application;

import com.wizinno.jas.application.domain.ApplicationMapper;
import com.wizinno.jas.application.domain.model.Application;
import com.wizinno.jas.application.service.ApplicationService;
import com.wizinno.jas.application.service.dto.ApplicationDto;
import com.wizinno.jas.application.service.dto.ApplicationPatientDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {
    /**
     *
     */
    @Autowired
    private ApplicationMapper applicationMapper;


    @Override
    public List getContentToListByKey(String key) {
        return null;
    }

    @Override
    public int saveApplication(ApplicationDto applicationDto) {
        Application application = new Application();
        BeanUtils.copyProperties(applicationDto,application);
        return  applicationMapper.insert(application);
    }

    @Override
    public List<ApplicationPatientDto> getApplications(Long id,String filter) {

        List<ApplicationPatientDto> apds=applicationMapper.getApplications(id,filter);
        return apds;
    }

    @Override
    public void createApplication(ApplicationDto dto) {
        Application application = new Application();
        BeanUtils.copyProperties(dto,application);
        applicationMapper.insert(application);

    }

    @Override
    public ApplicationDto getApplicationByPIdAndDId(Long patientId, Long id) {
        Application application=applicationMapper.getApplicationByPIdAndDId(patientId,id);
        if (application == null){
            return null;
        }else {
            ApplicationDto applicationDto = new ApplicationDto();
            BeanUtils.copyProperties(application,applicationDto);
            return applicationDto;
        }

    }

    @Override
    public void updateApplication(ApplicationDto dto) {
        Application application = new Application();
        BeanUtils.copyProperties(dto,application);
        applicationMapper.updateByPrimaryKey(application);
    }


}
