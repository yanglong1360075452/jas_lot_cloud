package com.wizinno.jas.score;
import com.wizinno.jas.score.domain.ExchangeRecordMapper;
import com.wizinno.jas.score.domain.ScoreMapper;
import com.wizinno.jas.score.domain.model.ExchangeRecord;
import com.wizinno.jas.score.domain.model.Score;
import com.wizinno.jas.score.service.ExchangeRecordService;
import com.wizinno.jas.score.service.ScoreService;
import com.wizinno.jas.score.service.dto.ERcouponDto;
import com.wizinno.jas.score.service.dto.ExchangeRecordDto;
import com.wizinno.jas.score.service.dto.ScoreDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */

@Service
public class ExchangeRecordServiceImpl implements ExchangeRecordService {

    @Autowired
    private ExchangeRecordMapper exchangeRecordMapper;
    @Override
    public List<ExchangeRecordDto> getERecordListByUserId(Long userId) {
        return null;
    }

    @Override
    public void createExchangeRecordDto(ExchangeRecordDto exchangeRecordDto) {
        ExchangeRecord exchangeRecord = new ExchangeRecord();
        BeanUtils.copyProperties(exchangeRecordDto,exchangeRecord);
        exchangeRecordMapper.insert(exchangeRecord);
    }

    @Override
    public int updateExchangeRecordDto(ExchangeRecordDto exchangeRecordDto) {
        ExchangeRecord exchangeRecord = new ExchangeRecord();
        BeanUtils.copyProperties(exchangeRecordDto,exchangeRecord);
        return exchangeRecordMapper.updateByPrimaryKey(exchangeRecord);
    }

    @Override
    public List<ERcouponDto> getChangeRecordList(Long userId) {
        List<ERcouponDto> erList = exchangeRecordMapper.getChangeRecordList(userId);
        List<ERcouponDto> erList1 = new ArrayList<ERcouponDto>();
        if(null!=erList && erList.size()>0){
            for(int i=0;i<erList.size();i++){
                erList.get(i).setCouponMoneys("兑换"+subZeroAndDot(String.valueOf(erList.get(i).getCouponMoney()))+"元优惠券");
                erList.get(i).setRedeemScores("-"+erList.get(i).getRedeemScore());
                erList1.add(erList.get(i));
            }
        }
        return erList1;
    }

    @Override
    public List<ERcouponDto> getCouponListByUserId(Long userId) {
        return exchangeRecordMapper.getCouponListByUserId(userId);
    }

    @Override
    public List<ERcouponDto> getHistoryCouponList(Long userId) {
        return exchangeRecordMapper.getHistoryCouponList(userId);
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
