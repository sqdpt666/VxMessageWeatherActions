package com.pt.vx.utils;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.pt.vx.config.MainConfig;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Author: PengTao
 * @Date: 2022/10/4 23:35
 **/
@Slf4j
public class HttpUtil {

    private static final Logger logger = Logger.getLogger("HttpUtil");

    /**
     *
     * @param url get请求地址
     * @param paramsMap 请求参数
     * @param charset 字符集
     * @param timeout 超时时间
     * @param recount 重试次数
     * @return 结果
     */
    public static String get(String url, Map<String,Object> paramsMap, Charset charset,int timeout,int recount){
        try {
            return cn.hutool.http.HttpUtil.createGet(url).form(paramsMap).charset(charset).timeout(timeout).execute().body();
        }catch (Exception e){
            if(recount > 0){
               return get(url,paramsMap,charset,timeout,--recount);
            }else {
               log.error("请求失败，地址为：{}，参数为：{},原因为：{}",url,JSONUtil.toJsonStr(paramsMap),e.getMessage(),e);
            }
        }
        return null;
    }

    public static String get(String url, Map<String,Object> paramsMap, Charset charset,int timeout){
        return get(url,paramsMap,charset,timeout,MainConfig.httpRetryCount);
    }

    public static String get(String url, Charset charset,int timeout){
       return get(url,new HashMap<>(),charset,timeout);
    }

    public static String get(String url, Map<String,Object> paramsMap){
        return get(url, paramsMap,StandardCharsets.UTF_8,MainConfig.httpRetryTime);
    }

    public static String get(String url){
        return get(url, new HashMap<>());
    }

    /**
     *
     * @param url post请求地址
     * @param params 请求参数
     * @param charset 字符集
     * @param timeout 超时时间
     * @param recount 重试次数
     * @return 结果
     */
    public static String post(String url, String params, Charset charset, int timeout,int recount){
        try {
            return cn.hutool.http.HttpUtil.createPost(url)
                    .body(params, "application/json")
                    .charset(charset)
                    .timeout(timeout)
                    .execute()
                    .body();
        }
        catch (Exception e){
            if(recount > 0){
                return post(url,params,charset,timeout,--recount);
            }else {
                log.error("请求失败，地址为：{}，参数为：{},原因为：{}",url,JSONUtil.toJsonStr(params),e.getMessage(),e);
            }
        }
        return null;
    }


    public static String post(String url,String params, Charset charset, int timeout){
        return post(url,params,charset,timeout,MainConfig.httpRetryCount);
    }

    public static String post(String url,String params){
      return post(url,params, StandardCharsets.UTF_8,MainConfig.httpRetryTime);
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("info","一家航空公司把自己定位为“低价的快乐航空”，那么以下做法不合适的是");
        String params = JSONUtil.toJsonStr(map);
        String body = cn.hutool.http.HttpUtil.createPost("https://cx.icodef.com/v2/answer")
                .body(params, "application/x-www-form-urlencoded")
                .header("Authorization", "TPuQNKXNfTFTDCId")
                .charset(StandardCharsets.UTF_8)
                .timeout(50000)
                .execute()
                .body();

        System.out.println(body);
    }

}
