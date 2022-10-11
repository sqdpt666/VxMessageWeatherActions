package com.pt.vx.utils;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Author: PengTao
 * @Date: 2022/10/4 23:35
 **/
public class HttpUtil {

    private static final Logger logger = Logger.getLogger("HttpUtil");

    public static String get(String url, Map<String,Object> paramsMap, Charset charset,int timeout){
        String result;
        try {
            result =   cn.hutool.http.HttpUtil.createGet(url).form(paramsMap).charset(charset).timeout(timeout).execute().body();
        }catch (Exception e){
            logger.info("连接超时，开始第二次尝试。请求地址为"+url);
            if(StrUtil.isNotBlank(e.getMessage()) && e.getMessage().contains("Connection timed out")){
                result =   cn.hutool.http.HttpUtil.createGet(url).form(paramsMap).charset(charset).timeout(timeout).execute().body();
            }else {
                throw e;
            }

        }
        return result;
    }

    public static String get(String url, Charset charset,int timeout){
       return get(url,new HashMap<>(),charset,timeout);
    }

    public static String get(String url){
        return get(url, StandardCharsets.UTF_8,60000);
    }
    public static String get(String url, Map<String,Object> paramsMap){
        return get(url, paramsMap,StandardCharsets.UTF_8,60000);
    }

    public static String post(String url, Map<String,Object> paramsMap, Charset charset, int timeout){
        String params =  JSONUtil.toJsonStr(paramsMap);
        return  post(url,params,charset,timeout);
    }

    public static String post(String url,String params, Charset charset, int timeout){
        String result;
        try {
            result =   cn.hutool.http.HttpUtil.createPost(url)
                    .body(params,"application/json")
                    .charset(charset)
                    .timeout(timeout)
                    .execute()
                    .body();
        }catch (Exception e){
            logger.info("连接超时，开始第二次尝试。请求地址为"+url);
            if(StrUtil.isNotBlank(e.getMessage()) && e.getMessage().contains("Connection timed out")){
                result =   cn.hutool.http.HttpUtil.createPost(url)
                        .body(params,"application/json")
                        .charset(charset)
                        .timeout(timeout)
                        .execute()
                        .body();
            }else {
                throw e;
            }
        }
        return result;
    }
    public static String post(String url,String params){
      return post(url,params, StandardCharsets.UTF_8,60000);
    }

}
