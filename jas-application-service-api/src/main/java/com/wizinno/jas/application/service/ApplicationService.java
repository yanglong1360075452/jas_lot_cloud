package com.wizinno.jas.application.service;
import com.wizinno.jas.application.service.dto.ApplicationDto;
import com.wizinno.jas.application.service.dto.ApplicationPatientDto;

import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */

public interface ApplicationService {

    List getContentToListByKey(String key);
    int saveApplication(ApplicationDto application);

    List<ApplicationPatientDto> getApplications(Long id,String filter);

    void createApplication(ApplicationDto dto);

    ApplicationDto getApplicationByPIdAndDId(Long patientId, Long id);

    void updateApplication(ApplicationDto dto);
}
