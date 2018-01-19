package com.wizinno.jas.score.api;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.exception.CustomExceptionCode;
import com.wizinno.jas.score.service.CouponService;
import com.wizinno.jas.score.service.ExchangeRecordService;
import com.wizinno.jas.score.service.ScoreService;
import com.wizinno.jas.score.service.dto.CouponDto;
import com.wizinno.jas.score.service.dto.ERcouponDto;
import com.wizinno.jas.score.service.dto.ExchangeRecordDto;
import com.wizinno.jas.score.service.dto.ScoreDto;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.UserDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2017/8/14.
 */
@RestController
@RequestMapping("/api/wechat/score")
public class ScoreController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExchangeRecordService exchangeRecordService;
    @Autowired
    private CouponService couponService;
    /*
    *Method:查询所有优惠券列表
    *
    */
    @RequestMapping(value="getCouponList",method = RequestMethod.GET)
    public ResponseVO getCouponList(){
        List<CouponDto>list = couponService.getCouponList();
        if(null == list || list.size()<0){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        return new ResponseVO(list);
    }

    /*
   *Method:查询我的优惠券列表
   *
   */
    @RequestMapping(value="getCouponListByUserId",method = RequestMethod.GET)
    public ResponseVO getCouponListByuserId(){
        String openId = (String)request.getAttribute("openId");
        UserDto userDto = userService.getUserByOpenId(openId);
        if(null == userDto){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        List<ERcouponDto>list = exchangeRecordService.getCouponListByUserId(userDto.getId());
        if(null == list || list.size()<0){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        return new ResponseVO(list);
    }

    /*
  *Method:查询我的历史优惠券列表
  *
  */
    @RequestMapping(value="getHistoryCouponList",method = RequestMethod.GET)
    public ResponseVO getHistoryCouponList(){
        String openId = (String)request.getAttribute("openId");
        UserDto userDto = userService.getUserByOpenId(openId);
        if(null == userDto){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        List<ERcouponDto>list = exchangeRecordService.getHistoryCouponList(userDto.getId());
        if(null == list || list.size()<0){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        return new ResponseVO(list);
    }

    /*
    *Method:根据用户Id查询积分明细
    *
    */
    @RequestMapping(value="getScoreDetail",method = RequestMethod.GET)
    public ResponseVO getScoreDetail(){
        String openId = (String)request.getAttribute("openId");
        UserDto userDto = userService.getUserByOpenId(openId);
        if(null == userDto){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        List<ScoreDto> scroreDtoList = scoreService.getScoreListByUserId(userDto.getId());
        if(scroreDtoList.size()>0){
            for(int i=0;i<scroreDtoList.size();i++){
                scroreDtoList.get(i).setType(scroreDtoList.get(i).getScoreChange());
            }
        }
        return new ResponseVO(scroreDtoList);
    }
    /*
    *Method:用户兑换记录查询
    *
    */
    @RequestMapping(value="getChangeRecordList",method = RequestMethod.GET)
    public ResponseVO getChangeRecordList(){
        String openId = (String)request.getAttribute("openId");
        UserDto userDto = userService.getUserByOpenId(openId);
        if(null == userDto){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        List<ERcouponDto> eRcouponDtoList = exchangeRecordService.getChangeRecordList(userDto.getId());
        return new ResponseVO(eRcouponDtoList);
    }

    /*
    *Method:创建积分数据 减少优惠券数量、创建兑换优惠券数据
    *param:socreChange 积分变化操作
    */
    @RequestMapping(value="createScore",method = RequestMethod.GET)
    public ResponseVO createScore(@RequestParam(value="scoreChange")Integer scoreChange ,@RequestParam(value="couponId",required = false)Long couponId){
        if(null == scoreChange){
            throw new CustomException(CustomExceptionCode.ErrorParam);
        }
        Map<String,String> result = new HashMap<>();
        String openId = (String)request.getAttribute("openId");
        UserDto userDto = userService.getUserByOpenId(openId);
        if(null == userDto){
            throw new CustomException(CustomExceptionCode.RecordNotExist);
        }
        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setScoreChange(scoreChange);
        scoreDto.setUserId(userDto.getId());
        Integer sc= 0;
        Integer sco = 0;
        sco = userDto.getScoreValue();//用户的总积分
        if(scoreChange == 2 && null!=couponId){
            CouponDto couponDto = couponService.getCouponDtoById(couponId);
            scoreDto.setChangeValue("-"+String.valueOf(couponDto.getRedeemScore()).toString());
            if(sco<couponDto.getRedeemScore()){
                result.put("result","你的积分不足以兑换该优惠券");
            }else{
                sc = sco-couponDto.getRedeemScore();
                userDto.setScoreValue(sc);
                scoreDto.setSurplusScore(Long.valueOf(sc));
                scoreDto.setCreateTime(new Date());
                ExchangeRecordDto exchangeRecordDto = new ExchangeRecordDto();
                exchangeRecordDto.setUserId(userDto.getId());
                exchangeRecordDto.setCouponId(couponDto.getId());
                exchangeRecordDto.setStatus(0);
                exchangeRecordDto.setCreateTime(new Date());
                couponDto.setCouponCount(couponDto.getCouponCount()-1);
                couponService.updateCoupon(couponDto);
                userService.updateUser(userDto);
                scoreService.createScore(scoreDto);
                exchangeRecordService.createExchangeRecordDto(exchangeRecordDto);
                result.put("result","兑换成功");
            }
        }else if(scoreChange != 2){
            if(scoreChange == 1){
               sc  = sco+1;
               scoreDto.setChangeValue("+1");
            }else if(scoreChange == 3){
                sc  = sco+2;
                scoreDto.setChangeValue("+2");
            }else if(scoreChange == 4){
                sc  = sco+3;
                scoreDto.setChangeValue("+3");
            }
            userDto.setScoreValue(sc);
            scoreDto.setSurplusScore(Long.valueOf(sc));
            scoreDto.setCreateTime(new Date());
            userService.updateUser(userDto);
            scoreService.createScore(scoreDto);
        }
    return new ResponseVO(result);
    }

}
