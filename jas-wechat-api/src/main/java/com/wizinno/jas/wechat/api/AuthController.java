package com.wizinno.jas.wechat.api;

import com.wizinno.jas.common.config.Config;
import com.wizinno.jas.common.controller.BaseController;
import com.wizinno.jas.common.data.ResponseVO;
import com.wizinno.jas.common.exception.CustomException;
import com.wizinno.jas.common.util.CommonUtil;
import com.wizinno.jas.common.util.JwtUtil;
import com.wizinno.jas.common.util.WechatGetImgUtil;
import com.wizinno.jas.common.wechat.AccessToken;
import com.wizinno.jas.common.wechat.JSSDKConfig;
import com.wizinno.jas.common.wechat.WeChatUtil;
import com.wizinno.jas.user.service.DoctorService;
import com.wizinno.jas.user.service.UserService;
import com.wizinno.jas.user.service.dto.DoctorDto;
import com.wizinno.jas.user.service.dto.UserDto;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuMei on 2017-07-26.
 */
@RestController
@RequestMapping("/api/wechat/auth/")
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private DoctorService doctorService;
    public static String path = "D:";
    /**
     * 获取微信JSSDK的配置参数
     * @param url
     * @return
     * @throws CustomException
     */
    @RequestMapping(value = "js",method = RequestMethod.GET)
    public ResponseVO sign(@RequestParam("url") String url) throws CustomException {
        Object result = null;
        try {
            result = JSSDKConfig.sign(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseVO(result);
    }

    /**
     * 网页授权
     * 用户入口
     * 未绑定返回openId
     * 已绑定返回token
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/user/{code}",method = RequestMethod.GET)
    public ResponseVO getAccessToken(@PathVariable("code") String code) throws CustomException {
        AccessToken accessToken = WeChatUtil.getWebAuthAccessToken(code);
        AccessToken accessToken1 = WeChatUtil.getAccessToken();
        String openId = accessToken.getOpenId();
        //String openId="asd"+code;
        UserDto user = userService.getUserByOpenId(openId);//将open改为code
        Map<String, String> result = new HashedMap();
        Map<String, Object> result1 = new HashedMap();
        if (user != null) {
            Map<String,String> info = new HashMap<>();
            info.put("openId",openId);
            info.put("phone",user.getPhone());
            String subject = JwtUtil.generalSubject(info);
            String token = "";
            try {
                token = jwtUtil.createJWT(Config.jwtId, subject, Config.jwtExpire);
                JSONObject jsonObject = WechatGetImgUtil.getUserInfo(accessToken1.getToken(),openId);
                String img = jsonObject.get("headimgurl").toString();
                result1.put("img", img);
               if("".equals(user.getHeadPortrait())||null==user.getHeadPortrait()) {
                    String imgUrl = WechatGetImgUtil.saveImage(img);
                    user.setHeadPortrait(imgUrl);
                    userService.updateUser(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            DoctorDto doctorDto = null;
            doctorDto = doctorService.getDoctorByUserId(user.getId());
            if(null !=doctorDto){
                result1.put("status", doctorDto.getStatus());
            }
            result1.put("token", token);
        } else {
            result1.put("openId", openId);
        }
        return new ResponseVO(result1);
    }

    /**
     * 网页授权
     * 用户入口
     * 未绑定返回openId
     * 已绑定返回token
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/users/{code}",method = RequestMethod.GET)
    public ResponseVO getAccessTokens(@PathVariable("code") String code) throws CustomException {
        //AccessToken accessToken = WeChatUtil.getWebAuthAccessToken(code);
        //String openId = accessToken.getOpenId();
        //String img = getUserInfo(accessToken.getToken(),openId);
        String openId=code;
        UserDto user = userService.getUserByOpenId(openId);//将open改为code
        Map<String, Object> result = new HashedMap();
        if (user != null) {
            Map<String,String> info = new HashMap<>();
            info.put("openId",openId);
            info.put("phone",user.getPhone());
            String subject = JwtUtil.generalSubject(info);
            String token = "";
            try {
                token = jwtUtil.createJWT(Config.jwtId, subject, Config.jwtExpire);
            } catch (Exception e) {
                e.printStackTrace();
            }
            DoctorDto doctorDto = null;
            doctorDto = doctorService.getDoctorByUserId(user.getId());
            if(null !=doctorDto){
                result.put("status", doctorDto.getStatus());
            }
            result.put("token", token);
        } else {
            result.put("openId", openId);
        }
        //result.put("img",img);
        return new ResponseVO(result);
    }


    /**
     * 得到微信用户的头像
     *
     * @return
     */
    @RequestMapping(value = "/user/getWechatHeadImg/{openId}",method = RequestMethod.GET)
    public ResponseVO getWechatHeadImg(@PathVariable("openId") String openId) throws CustomException {
        AccessToken accessToken = WeChatUtil.getAccessToken();
        UserDto user = userService.getUserByOpenId(openId);
        Map<String, Object> result = new HashedMap();
        if (user != null) {
            String token = "";
            try {
                JSONObject jsonObject = WechatGetImgUtil.getUserInfo(accessToken.getToken(), openId);
                String img = jsonObject.get("headimgurl").toString();
                result.put("img", img);
                if ("".equals(user.getHeadPortrait()) || null == user.getHeadPortrait()) {
                    String imgUrl = WechatGetImgUtil.saveImage(img);
                    user.setHeadPortrait(imgUrl);
                    userService.updateUser(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseVO(result);
    }
}
