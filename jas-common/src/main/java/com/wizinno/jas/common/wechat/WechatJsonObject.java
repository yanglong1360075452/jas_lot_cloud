package com.wizinno.jas.common.wechat;

import com.wizinno.jas.common.data.EnumMethod;
import net.sf.json.JSONObject;

public class WechatJsonObject {
    public static JSONObject getJson(String url, String token, String postData){
        String reCodeUrl= url.replace("TOKEN",token);
        JSONObject jsonObject=WeChatUtil.httpRequest(reCodeUrl, EnumMethod.POST.name(),postData);
        return jsonObject;

    }
}
