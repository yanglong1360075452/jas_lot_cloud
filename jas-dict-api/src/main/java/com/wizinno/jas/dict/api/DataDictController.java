package com.wizinno.jas.dict.api;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.dict.service.DataDictService;
import com.wizinno.jas.dict.service.data.ParamKey;
import com.wizinno.jas.dict.service.dto.DataDictDto;
import com.wizinno.jas.dict.service.dto.HospitalDto;
import com.wizinno.jas.dict.service.util.DictOrderUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2017/8/14.
 */
@RestController
@RequestMapping("/api/wechat/dict")
public class DataDictController extends BaseController {

    @Autowired
    private DataDictService dataDictService;

    @RequestMapping(value = "/{param}",method = RequestMethod.GET)
    public ResponseVO getIllnessState(@PathVariable(value = "param")String param){
        ParamKey keyByParam = ParamKey.getKeyByParam(param);
        if(keyByParam == null){
          throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        List<DataDictDto>  datas = dataDictService.getContentToListByKey(keyByParam.toString());

        return new ResponseVO(datas);
    }

    @RequestMapping(value = "/hospitals",method = RequestMethod.GET)
    public ResponseVO getHospital(@RequestParam("filter") String filter){

        List<HospitalDto> datas =  datas = dataDictService.getContentToListByKey(ParamKey.Hospital.toString());
       DictOrderUtil.sort(datas);
        if (filter == null || filter.equals("")) {
            return new ResponseVO(datas);
        }
        List<HospitalDto> filterData = new ArrayList<>();
        for (HospitalDto dsd : datas) {
            if (dsd.getName() != null && dsd.getName().contains(filter)) {
                filterData.add(dsd);
            }
        }

        return new ResponseVO(filterData);
    }


}
