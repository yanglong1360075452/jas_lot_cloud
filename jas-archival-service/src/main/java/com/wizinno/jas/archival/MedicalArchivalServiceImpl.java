package com.wizinno.jas.archival;

import com.wizinno.jas.archival.domain.MedicalArchivalMapper;
import com.wizinno.jas.archival.domain.model.MedicalArchival;
import com.wizinno.jas.archival.service.MedicalArchivalService;
import com.wizinno.jas.archival.service.dto.MedicalArchivalDto;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.dict.service.DataDictService;
import com.wizinno.jas.dict.service.data.ParamKey;
import com.wizinno.jas.dict.service.dto.DataDictDto;
import com.wizinno.jas.dict.service.dto.HospitalDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/8/15.
 */
@Service("medicalArchivalService")
public class MedicalArchivalServiceImpl implements MedicalArchivalService{

    @Autowired
    private MedicalArchivalMapper medicalArchivalMapper;
    @Autowired
    private DataDictService dataDictService;
    @Override
    public MedicalArchivalDto createMArchival(MedicalArchivalDto medicalArchivalDto) {
        MedicalArchival medicalArchival = new MedicalArchival();
        BeanUtils.copyProperties(medicalArchivalDto,medicalArchival);
        medicalArchivalMapper.insert(medicalArchival);
        MedicalArchival medicalArchival1 = medicalArchivalMapper.selectByPrimaryKey(medicalArchivalMapper.last());
        MedicalArchivalDto medicalArchivalDto1 = new MedicalArchivalDto();
        BeanUtils.copyProperties(medicalArchival1,medicalArchivalDto1);

       return  medicalArchivalDto1;
    }

    @Override
    public List<MedicalArchivalDto> getMArchivalByPatientId(Long id) {
        List<MedicalArchival> medicalArchivals=medicalArchivalMapper.getMArchivalByPatientId(id);
        List<MedicalArchivalDto> dtos = new ArrayList<>();
        MedicalArchivalDto medicalArchivalDto=null;
        if (medicalArchivals.size() > 0){
            List<DataDictDto> dataDictDtoListI = dataDictService.getContentToListByKey(ParamKey.IllnessState.toString());//病情说明
            List<DataDictDto> dataDictDtoListN = dataDictService.getContentToListByKey(ParamKey.NactivityFactor.toString());//活动不足因素
            List<DataDictDto> dataDictDtoListB = dataDictService.getContentToListByKey(ParamKey.BiseaseCourse.toString());//病程
            List<DataDictDto> dataDictDtoListD = dataDictService.getContentToListByKey(ParamKey.Departments.toString());//手术科室
            for (MedicalArchival medicalArchival:medicalArchivals){
                medicalArchivalDto=new MedicalArchivalDto();
                BeanUtils.copyProperties(medicalArchival,medicalArchivalDto);
                dtos.add(medicalArchivalDto);
            }
            for(MedicalArchivalDto md:dtos){
                if(md.getIllnessState()!=null){
                    for(DataDictDto dataDictDtoI:dataDictDtoListI){
                        if(dataDictDtoI.getCode().equals(md.getIllnessState())){
                            md.setIllnessStateDesc(dataDictDtoI.getName());
                        }
                    }
                }
                if(md.getActivity()!=null){
                    for(DataDictDto dataDictDtoN:dataDictDtoListN){
                        if(dataDictDtoN.getCode().equals(md.getActivity())){
                            md.setActivityDesc(dataDictDtoN.getName());
                        }
                    }
                }
                if(md.getProcess()!=null){
                    for(DataDictDto dataDictDtoB:dataDictDtoListB){
                        if(dataDictDtoB.getCode().equals(md.getProcess())){
                            md.setProcessDesc(dataDictDtoB.getName());
                        }
                    }
                }
                if(md.getDepartment()!=null){
                    for(DataDictDto dataDictDtoD:dataDictDtoListD){
                        if(dataDictDtoD.getCode().equals(md.getDepartment())){
                            md.setDepartmentDesc(dataDictDtoD.getName());
                        }
                    }
                }
            }
        }
        return dtos;
    }

    @Override
    public MedicalArchivalDto getMArchivalDetailsByMAId(Long maId) {

        MedicalArchival medicalArchival = medicalArchivalMapper.selectByPrimaryKey(maId);
        if (medicalArchival == null){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        MedicalArchivalDto md = new MedicalArchivalDto();
        BeanUtils.copyProperties(medicalArchival,md);
        List<DataDictDto> dataDictDtoListI = dataDictService.getContentToListByKey(ParamKey.IllnessState.toString());
        List<DataDictDto> dataDictDtoListN = dataDictService.getContentToListByKey(ParamKey.NactivityFactor.toString());
        List<DataDictDto> dataDictDtoListB = dataDictService.getContentToListByKey(ParamKey.BiseaseCourse.toString());
        List<DataDictDto> dataDictDtoListD = dataDictService.getContentToListByKey(ParamKey.Departments.toString());
        List<HospitalDto> dictDataH =  dataDictService.getContentToListByKey(ParamKey.Hospital.toString());//手术医院
            if(md.getIllnessState()!=null){
                for(DataDictDto dataDictDtoI:dataDictDtoListI){
                    if(dataDictDtoI.getCode().equals(md.getIllnessState())){
                        md.setIllnessStateDesc(dataDictDtoI.getName());
                    }
                }
            }
            if(md.getActivity()!=null){
                for(DataDictDto dataDictDtoN:dataDictDtoListN){
                    if(dataDictDtoN.getCode().equals(md.getActivity())){
                        md.setActivityDesc(dataDictDtoN.getName());
                    }
                }
            }
            if(md.getProcess()!=null){
                for(DataDictDto dataDictDtoB:dataDictDtoListB){
                    if(dataDictDtoB.getCode().equals(md.getProcess())){
                        md.setProcessDesc(dataDictDtoB.getName());
                    }
                }
            }
            if(md.getDepartment()!=null) {
                for (DataDictDto dataDictDtoD : dataDictDtoListD) {
                    if (dataDictDtoD.getCode().equals(md.getDepartment())) {
                        md.setDepartmentDesc(dataDictDtoD.getName());
                    }
                }
            }
        if(md.getDepartment()!=null){
            for(HospitalDto hospitalDto:dictDataH){
                if(hospitalDto.getCode().equals(md.getHospital())){
                    md.setHospitalDesc(hospitalDto.getName());
                }
            }
        }
        return md;
    }

    @Override
    public void updateMArchival(MedicalArchivalDto medicalArchivalDto) {
        MedicalArchival medicalArchival = new MedicalArchival();
        BeanUtils.copyProperties(medicalArchivalDto,medicalArchival);
        medicalArchivalMapper.updateByPrimaryKey(medicalArchival);
    }

    @Override
    public void deleteMArchival(Long maId) {
        medicalArchivalMapper.deleteByPrimaryKey(maId);
    }

    @Override
    public MedicalArchivalDto getMArchivalByMAId(Long maId) {
        MedicalArchival medicalArchival = medicalArchivalMapper.selectByPrimaryKey(maId);
        MedicalArchivalDto medicalArchivalDto = new MedicalArchivalDto();
        BeanUtils.copyProperties(medicalArchival,medicalArchivalDto);

        return medicalArchivalDto;
    }
}
