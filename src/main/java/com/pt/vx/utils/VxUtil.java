package com.pt.vx.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.pt.vx.domain.TokenInfo;
import com.sun.deploy.util.StringUtils;

import java.util.logging.Logger;


public class VxUtil {

    private static final String AppID = "你的AppID";

    private static final String appSecret= "你的appSecret";

    private static final Logger log = Logger.getAnonymousLogger();

    public static TokenInfo getToken(){
        String TOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        String result = HttpUtil.get(String.format(TOKEN_URL, AppID, appSecret));
        return JSONUtil.toBean(result,TokenInfo.class);
    }


    public static void sendMessage(String message){
        if("你的AppID".equals(AppID) || "你的appSecret".equals("你的appSecret")){
            log.warning("请先填写好你的AppID和你的appSecret");
            return;
        }
        String PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
        TokenInfo token = getToken();
        String result =  HttpUtil.post(String.format(PUSH_URL,token.getAccess_token()),message);
        log.info(result);
    }

}
