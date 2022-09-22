package com.pt.vx.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.pt.vx.config.AllConfig;
import com.pt.vx.domain.TokenInfo;


import java.util.logging.Logger;


public class VxUtil {



    private static final Logger log = Logger.getAnonymousLogger();

    public static TokenInfo getToken(){
        String TOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        String result = HttpUtil.get(String.format(TOKEN_URL, AllConfig.VxAppId, AllConfig.VxAppSecret));
        log.info("获取微信token："+result);
        return JSONUtil.toBean(result,TokenInfo.class);
    }


    public static void sendMessage(String message){
        if("微信的APPID".equals(AllConfig.VxAppId) || "微信的密钥".equals(AllConfig.VxAppSecret)){
            log.warning("请先填写好你的AppID和你的appSecret");
            return;
        }
        String PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
        TokenInfo token = getToken();
        String result =  HttpUtil.post(String.format(PUSH_URL,token.getAccess_token()),message);
        log.info(String.format("发送消息:%s ，结果为：%s", message,result));
    }

}
