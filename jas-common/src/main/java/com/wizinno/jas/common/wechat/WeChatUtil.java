package com.wizinno.jas.common.wechat;

import com.wizinno.jas.common.config.Config;
import com.wizinno.jas.common.data.Menu;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

/**
 * Created by LiuMei on 2017-05-19.
 */
public class WeChatUtil {

    public static Log logger = LogFactory.getLog(WeChatUtil.class);

    private static final String appId = Config.wechatAppId;
    private static final String appSecret = Config.wechatAppSecret;
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //获取网页授权access_token
    private static final String  WebAuthAccessTokenUrl=
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    private static final String AccessTokenUrl =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private static final String JsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public static String getJsapiTicket(String accessToken){
        String url=JsapiTicketUrl.replace("ACCESS_TOKEN",accessToken);
        JSONObject jsonObject=httpRequest(url, "GET",null);
        String ticket  = null;
        if (null != jsonObject) {
            try {
                ticket = jsonObject.getString("ticket");
            } catch (JSONException e) {
                logger.error(e);
                logger.error("获取jsapi ticket失败 errcode:{"+jsonObject.getInt("errcode")+"}  <br>" +
                        " errmsg:{"+jsonObject.getString("errmsg")+"}");
            }
        }
        return ticket;
    }

    /**
     * 获取接口调用access_token
     *
     * @return
     */
    public static AccessToken getAccessToken(){
        String accessTokenUrl=AccessTokenUrl.replace("APPID",appId).replace("APPSECRET",appSecret);
        JSONObject jsonObject=httpRequest(accessTokenUrl, "GET",null);
        AccessToken accessToken = null;
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                logger.error("获取token失败 errcode:{"+jsonObject.getInt("errcode")+"}  <br>" +
                        " errmsg:{"+jsonObject.getString("errmsg")+"}");
            }
        }
        return accessToken;
    }

    /**
     * 获取网页授权
     * @param code
     * @return
     */
    public static AccessToken getWebAuthAccessToken(String code){
        String reGetAccessTokenUrl=WebAuthAccessTokenUrl.replace("APPID",appId).replace("SECRET",appSecret).replace("CODE",code);
        JSONObject jsonObject=httpRequest(reGetAccessTokenUrl, "GET",null);
        AccessToken accessToken = null;
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setOpenId(jsonObject.getString("openid"));
                accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                accessToken.setScope(jsonObject.getString("scope"));
                System.setProperty("javax.net.debug", "ssl,handshake");
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                logger.error("获取token失败 errcode:{"+jsonObject.getInt("errcode")+"}  <br>" +
                        " errmsg:{"+jsonObject.getString("errmsg")+"}");
            }
        }
        return accessToken;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm =  { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            //if ("GET".equalsIgnoreCase(requestMethod))
            httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            logger.error("https request error:{}"+e);
        }
        return jsonObject;
    }
    /**
     * 创建菜单
     *
     * @param menu
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;

        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                logger.error("创建菜单失败 errcode:{} errmsg:{}"+ jsonObject.getInt("errcode")+ jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
}
