package com.wizinno.jas.score.service;


import com.wizinno.jas.score.service.dto.CouponDto;
import com.wizinno.jas.score.service.dto.ScoreDto;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */

public interface CouponService {
   CouponDto getCouponDtoById(Long id);
   void createCoupon(CouponDto couponDto);
   int updateCoupon(CouponDto couponDto);
   List<CouponDto>getCouponList();
   //后台调用方法
   List<CouponDto>getCouponByStatus(String status,String userName, Date startTime, Date endTime);

}
