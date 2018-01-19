package com.wizinno.jas.wechat.api;

import com.wizinno.jas.doMessage.DoMessage;
import com.wizinno.jas.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * User:
 * Date:
 * Time:
 */
@RestController
@RequestMapping("/weixin")
public class CoreService {

    @Autowired
    DoMessage doMessage;
    /**
     * 校验信息是否是从微信服务器发过来的。
     *
     * @param
     * @param out
     */
    @RequestMapping(value={"/core"},method = { RequestMethod.GET })
    public void doget(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        System.out.println("不是微信服务器发来的请求!");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        } else {
            System.out.println("不是微信服务器发来的请求!");
        }
        out.flush();
        out.close();
    }

    /**
     * 获取二维码的id
     * 处理微信推送事件
     * @param request
     */
    @RequestMapping(value={"/core"},method = { RequestMethod.POST})
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String message=doMessage.processRequest(request);
        try {
           PrintWriter out= response.getWriter();
            out.print(message);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
