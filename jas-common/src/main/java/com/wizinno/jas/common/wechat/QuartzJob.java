package com.wizinno.jas.common.wechat;

import org.apache.log4j.Logger;

/**
 * Created by LiuMei on 2017-08-11.
 */
public class QuartzJob {

    private static Logger logger = Logger.getLogger(QuartzJob.class);

    /**
     * 任务执行获取token
     */
    public void getToken() {
        try {
            WeChatTask.getTokenAndTicket();
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

}
