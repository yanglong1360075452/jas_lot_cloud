package com.wizinno.jas.score.service;


import com.wizinno.jas.score.service.dto.ERcouponDto;
import com.wizinno.jas.score.service.dto.ExchangeRecordDto;
import com.wizinno.jas.score.service.dto.ScoreDto;

import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */

public interface ExchangeRecordService {
   List<ExchangeRecordDto> getERecordListByUserId(Long userId);
   void createExchangeRecordDto(ExchangeRecordDto exchangeRecordDto);
   int updateExchangeRecordDto(ExchangeRecordDto exchangeRecordDto);
   List<ERcouponDto>getChangeRecordList(Long userId);
   List<ERcouponDto>getCouponListByUserId(Long userId);
   List<ERcouponDto>getHistoryCouponList(Long userId);

}
