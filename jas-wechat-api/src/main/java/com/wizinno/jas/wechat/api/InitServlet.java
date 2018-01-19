package com.wizinno.jas.wechat.api;
import com.wizinno.jas.common.data.CommonButton;
import com.wizinno.jas.common.data.Menu;
import com.wizinno.jas.common.wechat.AccessToken;
import com.wizinno.jas.common.wechat.WeChatUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by Administrator on 2016/12/29.
 */
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        access_token();
    }
    public  static AccessToken access_token(){
        AccessToken accessToken= WeChatUtil.getAccessToken();
        System.out.println("查看token值"+accessToken.getToken());

        if (null != accessToken) {
            // 调用接口创建菜单
            int result = WeChatUtil.createMenu(getMenu(), accessToken.getToken());

            // 判断菜单创建结果
            if (0 == result) System.out.println("菜单创建成功！");
            else
                System.out.println(("菜单创建失败，错误码：" + result));
        }
        return accessToken;
    }

    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("病人");
        btn11.setType("view");
        btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9ff41a08eb9923b0&redirect_uri=http%3a%2f%2foa.wizinno.com%2fjas-server%2f%23%2fapp%2fmain&response_type=code&scope=snsapi_userinfo&state=user_type:0#wechat_redirect");
        CommonButton btn12 = new CommonButton();
        btn12.setName("医生");
        btn12.setType("view");
        btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9ff41a08eb9923b0&redirect_uri=http%3a%2f%2foa.wizinno.com%2fjas-server%2f%23%2fapp%2fmain&response_type=code&scope=snsapi_userinfo&state=user_type:1#wechat_redirect");


        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new CommonButton[] {btn12,btn11});
        return menu;
    }
}
