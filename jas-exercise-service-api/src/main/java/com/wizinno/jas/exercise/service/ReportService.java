package com.wizinno.jas.exercise.service;

import com.wizinno.jas.exercise.service.dto.ReportDto;

import java.util.List;

/**
 * Created by HP on 2017/8/21.
 */
public interface ReportService {

    void createReport(ReportDto reportDto);

    List<ReportDto> getReports(Long epId);
    List<ReportDto> findReportListByEpId(Long epId);
}
