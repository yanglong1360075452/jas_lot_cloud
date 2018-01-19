package com.wizinno.jas.common.wechat;

import com.wizinno.jas.common.config.Config;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by LiuMei on 2017-08-11.
 * <p>
 * 生成JS-SDK权限验证的签名
 */
public class JSSDKConfig {

    public static HashMap<String, String> sign(String url) throws Exception {
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String jsapi_ticket = Config.getConfig("wechat.jsapi.ticket");
        if (StringUtils.isBlank(jsapi_ticket)) {
            WeChatTask.getTokenAndTicket();
            jsapi_ticket = Config.getConfig("wechat.jsapi.ticket");
        }
        // 注意这里参数名必须全部小写，且必须有序
        String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string1.getBytes("UTF-8"));
        String signature = byteToHex(crypt.digest());
        HashMap<String, String> jssdk = new HashMap<>();
        jssdk.put("appId", Config.wechatAppId);
        jssdk.put("timestamp", timestamp);
        jssdk.put("nonceStr", nonce_str);
        jssdk.put("signature", signature);
        return jssdk;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
