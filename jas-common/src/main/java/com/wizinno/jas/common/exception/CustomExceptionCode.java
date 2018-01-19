package com.wizinno.jas.common.exception;

import java.util.HashMap;
import java.util.Map;

public class CustomExceptionCode {
    public static final Integer AccessDenied = 1;
    public static final Integer AuthFailed = 2;
    public static final Integer UsernameOrPasswordError = 3;
    public static final Integer ErrorParam = 4;
    public static final Integer CaptchaError=5;
    public static final Integer DoctorExistence=6;
    public static final Integer PatientExistence=7;
    public static final Integer RecordNotExist = 8;
    public static final Integer UserNotExist = 9;
    public static final Integer DNotExist = 10;



    private final static Map<Integer, String> errorMap = new HashMap<>();


    static {
        errorMap.put(1, "权限不足");
        errorMap.put(2, "认证失败");
        errorMap.put(3, "用户名或密码错误");
        errorMap.put(4, "参数错误");
        errorMap.put(5, "验证码错误");
        errorMap.put(6, "医生已存在");
        errorMap.put(7, "病人已存在");
        errorMap.put(8, "记录不存在");
        errorMap.put(9, "用户不存在");
        errorMap.put(10,"改设备不存在");
    }

    public static String getReasonByCode(Integer code, String defaultReason){
        if(errorMap.containsKey(code)){
            return errorMap.get(code);
        }else{
            return defaultReason;
        }
    }
}
