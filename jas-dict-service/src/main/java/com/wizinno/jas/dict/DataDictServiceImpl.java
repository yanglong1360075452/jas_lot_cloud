package com.wizinno.jas.dict;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wizinno.jas.dict.domain.DataDictMapper;
import com.wizinno.jas.dict.service.DataDictService;
import com.wizinno.jas.dict.service.data.ParamKey;
import com.wizinno.jas.dict.service.dto.ArthroDto;
import com.wizinno.jas.dict.service.dto.DataDictDto;
import com.wizinno.jas.dict.service.dto.HospitalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */
@Service
public class DataDictServiceImpl implements DataDictService {

    private Gson gson = new Gson();

    @Autowired
    private DataDictMapper dataDictMapper;

    @Override
    public List getContentToListByKey(String key) {
        String content = dataDictMapper.selectByKey(key);
        if (key.equals(ParamKey.Hospital.toString())){
            return (List) gson.fromJson(content, new TypeToken<List<HospitalDto>>() {
            }.getType());
        }else if(key.equals(ParamKey.Arthro.toString())){
            return (List) gson.fromJson(content, new TypeToken<List<ArthroDto>>() {
            }.getType());
        }else {
            return (List) gson.fromJson(content, new TypeToken<List<DataDictDto>>() {
            }.getType());
        }
    }

}
