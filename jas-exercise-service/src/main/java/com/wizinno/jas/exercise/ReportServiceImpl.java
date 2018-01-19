package com.wizinno.jas.exercise;

import com.wizinno.jas.exercise.domain.ReportMapper;
import com.wizinno.jas.exercise.domain.model.Report;
import com.wizinno.jas.exercise.service.ReportService;
import com.wizinno.jas.exercise.service.dto.ReportDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/8/21.
 */
@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public void createReport(ReportDto reportDto) {

        Report report = new Report();
        BeanUtils.copyProperties(reportDto,report);
        reportMapper.insert(report);
    }

    @Override
    public List<ReportDto> getReports(Long epId) {
        List<Report> reports=reportMapper.getReportByEpId(epId);
        List<ReportDto> dtos = new ArrayList<>();
        ReportDto reportDto = null;
        for (Report report:reports){
            reportDto = new ReportDto();
            BeanUtils.copyProperties(report,reportDto);
            dtos.add(reportDto);
        }
        return dtos;
    }

    @Override
    public List<ReportDto> findReportListByEpId(Long epId) {
        return reportMapper.findReportListByEpId(epId);
    }
}
