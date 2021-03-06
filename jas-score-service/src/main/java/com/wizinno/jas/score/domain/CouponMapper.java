package com.wizinno.jas.score.domain;

import com.wizinno.jas.score.domain.model.Coupon;
import com.wizinno.jas.score.service.dto.CouponDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CouponMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon
     *
     * @mbggenerated Fri Aug 18 14:29:41 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon
     *
     * @mbggenerated Fri Aug 18 14:29:41 CST 2017
     */
    int insert(Coupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon
     *
     * @mbggenerated Fri Aug 18 14:29:41 CST 2017
     */
    Coupon selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon
     *
     * @mbggenerated Fri Aug 18 14:29:41 CST 2017
     */
    List<CouponDto> selectAll();

    List<CouponDto>getCouponByStatus(@Param("status") String status,@Param("userName") String userName, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coupon
     *
     * @mbggenerated Fri Aug 18 14:29:41 CST 2017
     */
    int updateByPrimaryKey(Coupon record);
}