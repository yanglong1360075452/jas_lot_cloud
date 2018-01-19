package com.wizinno.jas.common.wechat;

import com.wizinno.jas.common.config.Config;
import org.apache.log4j.Logger;

/**
 * Created by LiuMei on 2017-08-11.
 */
public class WeChatTask {
    private static Logger logger = Logger.getLogger(WeChatTask.class);

    /**
     * 获取access token jsapi ticket并保存
     */
    public static void getTokenAndTicket() {
        try {
            AccessToken accessToken = WeChatUtil.getAccessToken();
            String token = accessToken.getToken();
            Config.p.put("wechat.access.token", token);
            String jsapiTicket = WeChatUtil.getJsapiTicket(token);
            Config.p.put("wechat.jsapi.ticket", jsapiTicket);
        } catch (Exception e) {
            logger.error(e, e);
        }
    }
}
