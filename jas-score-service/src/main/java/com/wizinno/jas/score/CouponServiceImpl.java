package com.wizinno.jas.score;
import com.wizinno.jas.score.domain.CouponMapper;
import com.wizinno.jas.score.domain.ScoreMapper;
import com.wizinno.jas.score.domain.model.Coupon;
import com.wizinno.jas.score.domain.model.Score;
import com.wizinno.jas.score.service.CouponService;
import com.wizinno.jas.score.service.ScoreService;
import com.wizinno.jas.score.service.dto.CouponDto;
import com.wizinno.jas.score.service.dto.ExchangeRecordDto;
import com.wizinno.jas.score.service.dto.ScoreDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;
    @Override
    public CouponDto getCouponDtoById(Long id) {
        Coupon coupon = new Coupon();
        CouponDto couponDto = new CouponDto();
        coupon = couponMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(coupon,couponDto);
        return couponDto;
    }

    @Override
    public void createCoupon(CouponDto couponDto) {
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponDto,coupon);
        couponMapper.insert(coupon);
    }

    @Override
    public int updateCoupon(CouponDto couponDto) {
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponDto,coupon);
        return couponMapper.updateByPrimaryKey(coupon);
    }

    @Override
    public List<CouponDto> getCouponList() {
        return couponMapper.selectAll();
    }

    @Override
    public List<CouponDto> getCouponByStatus(String status,String userName, Date startTime, Date endTime) {
        return couponMapper.getCouponByStatus(status,userName,startTime,endTime);
    }
}
