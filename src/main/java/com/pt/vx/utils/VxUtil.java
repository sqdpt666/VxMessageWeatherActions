package com.pt.vx.utils;


import cn.hutool.json.JSONUtil;
import com.pt.vx.config.AllConfig;
import com.pt.vx.domain.TokenInfo;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Logger;


public class VxUtil {



    private static final Logger log = Logger.getAnonymousLogger();

    private static String tokenCache;
    private static LocalDateTime getTime;

    public static TokenInfo getToken(){
        String TOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        String result = HttpUtil.get(String.format(TOKEN_URL, AllConfig.VxAppId, AllConfig.VxAppSecret), StandardCharsets.UTF_8,10000);
        log.info("获取微信token："+result);
        TokenInfo tokenInfo = JSONUtil.toBean(result, TokenInfo.class);
        if(Objects.nonNull(tokenInfo) ){
            String errCode = tokenInfo.getErrcode();
            if("-1".equals(errCode)){
                 log.warning("微信系统繁忙，此时稍候再试<<<pt提示：请等一会会再来>>>");
            }else if("40001".equals(errCode)){
                log.warning("微信调用错误！<<<pt提示：AppSecret错误或者 AppSecret 不属于这个公众号，请检查一下你的微信AppID和appSecret(刷新一下测试平台，看看是否数据是否一致)>>>");
            }else if("40002".equals(errCode)){
                log.warning("微信调用错误！<<<pt提示：请确保grant_type字段值为client_credential");
            }else if("40164".equals(errCode)){
                log.warning("微信调用错误！<<<pt提示：调用接口的 IP 地址不在白名单中，请在接口 IP 白名单中进行设置。>>>");
            }else if("89503".equals(errCode)){
                log.warning("微信调用错误！<<<pt提示：此 IP 调用需要管理员确认,请联系管理员>>>");
            }else if("89501".equals(errCode)){
                log.warning("微信调用错误！<<<pt提示：此 IP 正在等待管理员确认,请联系管理员>>>");
            }else if("89506".equals(errCode)){
                log.warning("微信调用错误！<<<pt提示：24小时内该 IP 被管理员拒绝调用两次，24小时内不可再使用该 IP 调用>>>");
            }else if("89507".equals(errCode)){
                log.warning("微信调用错误！<<<pt提示：1小时内该 IP 被管理员拒绝调用一次，1小时内不可再使用该 IP 调用>>>");
            }
        }
        return tokenInfo;
    }


    public static void sendMessage(String message){
        if("微信的APPID".equals(AllConfig.VxAppId) || "微信的密钥appSecret".equals(AllConfig.VxAppSecret)){
            log.warning("<<<pt提示：请先填写好你的微信AppID和appSecret>>>");
            return;
        }
        String PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
        String accessToken;
        if(Objects.isNull(tokenCache) || isTokenOver()){
            TokenInfo token = getToken();
            if(Objects.isNull(token) || Objects.isNull(token.getAccess_token())){
                return;
            }
            getTime = LocalDateTime.now();
            accessToken = token.getAccess_token();
        }else {
            accessToken = tokenCache;
        }
        String result =  HttpUtil.post(String.format(PUSH_URL,accessToken),message, StandardCharsets.UTF_8,10000);
        log.info(String.format("发送消息:%s ，结果为：%s", message,result));
    }

    private static boolean isTokenOver(){
        if(Objects.isNull(getTime)){
            return false;
        }
       return  getTime.plusSeconds(7000L).isBefore(LocalDateTime.now());
    }

}
