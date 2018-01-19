package com.wizinno.jas.archival.service;

import com.wizinno.jas.archival.service.dto.ClinicalDataDto;

import java.util.List;

/**
 * Created by HP on 2017/8/16.
 */
public interface ClinicalDataService {
    void createClinical(ClinicalDataDto clinicalDataDto);

    List<ClinicalDataDto> getImgByMaId(Long maId);

    ClinicalDataDto getImgById(Long id);

    void updateImg(ClinicalDataDto dto);

    void deleteImg(Long id);
}
