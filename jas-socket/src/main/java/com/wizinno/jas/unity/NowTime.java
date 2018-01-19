package com.wizinno.jas.unity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/29.
 */
public class NowTime {
    public static String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
       // new Date()为获取当前系统时间
        String time=df.format(new Date());
        System.out.print(time);
        return time;
    }

    public static void main(String[] args) {
        NowTime.getTime();
    }

}
