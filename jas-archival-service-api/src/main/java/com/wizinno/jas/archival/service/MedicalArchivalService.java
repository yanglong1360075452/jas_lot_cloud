package com.wizinno.jas.archival.service;

import com.wizinno.jas.archival.service.dto.MedicalArchivalDto;

import java.util.List;

/**
 * Created by HP on 2017/8/15.
 */
public interface MedicalArchivalService {

    MedicalArchivalDto createMArchival(MedicalArchivalDto medicalArchivalDto);

    List<MedicalArchivalDto> getMArchivalByPatientId(Long id);

    MedicalArchivalDto getMArchivalDetailsByMAId(Long maId);

    void updateMArchival(MedicalArchivalDto medicalArchivalDto);

    void deleteMArchival(Long maId);

    MedicalArchivalDto getMArchivalByMAId(Long maId);
}
