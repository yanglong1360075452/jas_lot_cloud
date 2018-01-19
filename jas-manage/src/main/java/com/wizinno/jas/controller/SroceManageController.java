package com.wizinno.jas.controller;

import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.PageData;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.device.service.dto.DeviceDto;
import com.wizinno.jas.dict.service.DataDictService;
import com.wizinno.jas.dict.service.data.ParamKey;
import com.wizinno.jas.dict.service.dto.HospitalDto;
import com.wizinno.jas.score.service.CouponService;
import com.wizinno.jas.score.service.ExchangeRecordService;
import com.wizinno.jas.score.service.ScoreService;
import com.wizinno.jas.score.service.dto.CouponDto;
import com.wizinno.jas.score.service.dto.ExchangeRecordDto;
import com.wizinno.jas.score.service.dto.UserScoreDto;
import com.wizinno.jas.user.service.DoctorService;
import com.wizinno.jas.user.service.PatientService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.DoctorDto;
import com.wizinno.jas.user.service.dto.PatientDto;
import com.wizinno.jas.user.service.dto.UserDoctorsDto;
import com.wizinno.jas.user.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-07-26.
 */
@RestController
@RequestMapping("manageWebapp/api/manage/score")
public class SroceManageController extends BaseController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private ExchangeRecordService exchangeRecordService;
    //用户积分查询
    @RequestMapping(value = "scoresList",method = RequestMethod.GET)
    public ResponseVO getScoresList(@RequestParam(value = "userName",required = false)String userName,
                                            @RequestParam(value = "startTime",required = false)Long startTime,
                                            @RequestParam(value = "endTime",required = false)Long endTime){
        UserScoreDto userScoreDto = new UserScoreDto();
        userScoreDto.setUserName(userName);
        if(null !=startTime && null !=endTime) {
            userScoreDto.setStartTime(new Date(startTime));
            userScoreDto.setEndTime(new Date(endTime));
        }
        return new ResponseVO(scoreService.getScoreList(userScoreDto));
    }

    //用户优惠券查询
    @RequestMapping(value = "couponList",method = RequestMethod.GET)
    public ResponseVO getCouponList(@RequestParam(value = "userName",required = false)String userName,
                                    @RequestParam(value = "status",required = false)String status,
                                    @RequestParam(value = "startTime",required = false)Long startTime,
                                    @RequestParam(value = "endTime",required = false)Long endTime){
        Date startTimeO = null;
        Date endTimeT = null;
        if(null !=startTime && null !=endTime) {
            startTimeO = new Date(startTime);
            endTimeT = new Date(endTime);
        }
        List<CouponDto> couponDtoList = couponService.getCouponByStatus(status,userName,startTimeO,endTimeT);
        return new ResponseVO(couponDtoList);
    }

    //后台批量导入优惠券
    @RequestMapping(value = "importCoupon",method = RequestMethod.POST)
    public ResponseVO importCoupon(@RequestBody CouponDto[] couponDto){
        if(couponDto.length>0 && couponDto!=null){
            for(int i=0;i<couponDto.length;i++){
                couponDto[i].setCreateTime(new Date());
                couponService.createCoupon(couponDto[i]);
//                if(null !=couponDto[i].getSn() && !"".equals(couponDto[i].getSn())) {
//                    deviceDto[i].setCreateTime(new Date());
//                    deviceService.insertDevice(deviceDto[i]);
//                }
            }
        }else{
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        return new ResponseVO();
    }


}
