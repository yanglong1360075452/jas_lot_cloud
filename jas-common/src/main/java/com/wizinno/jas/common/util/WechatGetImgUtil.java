package com.wizinno.jas.common.util;

import com.wizinno.jas.common.wechat.AccessToken;
import com.wizinno.jas.common.wechat.WeChatUtil;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiuMei on 2017-05-09.
 */
public class WechatGetImgUtil {
    /**
     *
     * 根据文件id下载文件
     *
     *
     *
     * @param mediaId
     *
     *            媒体id
     *
     * @throws Exception
     */
    public static String path = "D://headImg//";// 文件路径
    public static InputStream getInputStream(String accessToken, String mediaId) {
        InputStream is = null;
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="
                + accessToken + "&media_id=" + mediaId;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;

    }
    /**
     * 获取用户默认头像
     *
     * @param accessToken 接口访问凭证
     * @param openId 用户标识
     * @return WeixinUserInfo
     */
    public static JSONObject getUserInfo(String accessToken, String openId) {
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        String img = "";
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                // 用户头像
                img = jsonObject.getString("headimgurl");
            } catch (Exception e) {

            }
        }
        return jsonObject;
    }


    /**
     *
     * 获取下载图片信息（jpg）
     *
     *
     *
     * @param mediaId
     *
     *            文件的id
     *
     * @throws Exception
     */

    public static String saveImageToDisk(String accessToken, String mediaId, String picName, String picPath)
            throws Exception {
        InputStream inputStream = getInputStream(accessToken, mediaId);
        String imgUrl="";
        byte[] data = new byte[10240];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(picPath+picName+".jpg");
            imgUrl = "/headImg/"+picName+".jpg";
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imgUrl;
    }


    /**
     *
     * 保存用户病历档案图片（jpg）
     *
     *
     *
     * @param mediaId
     *
     *            文件的id
     *
     * @throws Exception
     */

    public static String savePatientImageToDisk(String accessToken, String mediaId, String picName, String picPath)
            throws Exception {
        InputStream inputStream = getInputStream(accessToken, mediaId);
        String imgUrl="";
        byte[] data = new byte[10240];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(picPath+picName+".jpg");
            imgUrl = "/clinicalImg/"+picName+".jpg";
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imgUrl;
    }


    /**
     * 保存用户头像（jpg）
     * @param imgUrl
     * @throws Exception
     */

    public static String saveImage(String imgUrl) throws Exception {
        //new一个URL对象
        URL url = new URL(imgUrl);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        Date date = new Date();
        long time = date.getTime();
        File imageFile = new File(path+time+".jpg");
        String img = "/headImg/"+time+".jpg";//返回图片保存本地后的路径
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
        return img;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }



}
