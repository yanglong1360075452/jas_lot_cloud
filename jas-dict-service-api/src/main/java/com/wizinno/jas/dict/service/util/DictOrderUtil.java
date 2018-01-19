package com.wizinno.jas.dict.service.util;

import com.wizinno.jas.dict.service.dto.DataDictDto;
import com.wizinno.jas.dict.service.dto.HospitalDto;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by LiuMei on 2017-05-09.
 */
public class DictOrderUtil implements Comparator<HospitalDto> {
    /**
     * 根据字典名字的首字母进行排序
     * @return 排序后的list
     */
    public static List<HospitalDto> sort(List<HospitalDto> list){
        DictOrderUtil comp = new DictOrderUtil();
        Collections.sort(list,comp);
        return list; //返回排序后的列表
    }

    public int compare(HospitalDto dic1, HospitalDto dic2) {
        String dicName1 = dic1.getName().toString();
        String dicName2 = dic2.getName().toString();
        return Collator.getInstance(Locale.CHINESE).compare(dicName1, dicName2);
    }
}

