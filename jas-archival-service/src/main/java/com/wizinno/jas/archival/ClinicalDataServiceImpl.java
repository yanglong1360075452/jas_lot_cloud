package com.wizinno.jas.archival;

import com.wizinno.jas.archival.api.vo.ClinicalDataVO;
import com.wizinno.jas.archival.domain.ClinicalDataMapper;
import com.wizinno.jas.archival.domain.model.ClinicalData;
import com.wizinno.jas.archival.service.ClinicalDataService;
import com.wizinno.jas.archival.service.dto.ClinicalDataDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/8/16.
 */
@Service
public class ClinicalDataServiceImpl implements ClinicalDataService {

    @Autowired
    private ClinicalDataMapper  clinicalDataMapper;

    @Override
    public void createClinical(ClinicalDataDto clinicalDataDto) {
        ClinicalData clinicalData = new ClinicalData();
        BeanUtils.copyProperties(clinicalDataDto,clinicalData);
        clinicalDataMapper.insert(clinicalData);

    }

    @Override
    public List<ClinicalDataDto> getImgByMaId(Long maId) {
        List<ClinicalData> data=clinicalDataMapper.getImgByMaId(maId);
        List<ClinicalDataDto> lists = new ArrayList<>();
        ClinicalDataDto clinicalDataDto = null;
        for (ClinicalData cd:data){
            clinicalDataDto = new ClinicalDataDto();
            BeanUtils.copyProperties(cd,clinicalDataDto);
            lists.add(clinicalDataDto);
        }

        return lists;
    }

    @Override
    public ClinicalDataDto getImgById(Long id) {

        ClinicalData clinicalData = clinicalDataMapper.selectByPrimaryKey(id);
        ClinicalDataDto clinicalDataDto = new ClinicalDataDto();
        BeanUtils.copyProperties(clinicalData,clinicalDataDto);

        return clinicalDataDto;
    }

    @Override
    public void updateImg(ClinicalDataDto dto) {
        ClinicalData clinicalData = new ClinicalData();
        BeanUtils.copyProperties(dto,clinicalData);
        clinicalDataMapper.updateByPrimaryKey(clinicalData);
    }

    @Override
    public void deleteImg(Long id) {
         clinicalDataMapper.deleteByPrimaryKey(id);
    }
}
