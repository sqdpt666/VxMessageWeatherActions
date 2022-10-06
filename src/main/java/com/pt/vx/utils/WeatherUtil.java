package com.pt.vx.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.pt.vx.config.AllConfig;
import com.pt.vx.domain.weather.*;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;


public class WeatherUtil {


    private static final Logger log = Logger.getAnonymousLogger();

    private static final  String WEATHER_URL = "https://restapi.amap.com/v3/weather/weatherInfo";

    private static final  String CODE_URL = "https://restapi.amap.com/v3/geocode/geo";

    public static final String TYPE_LIVE = "base";
    public static final String TYPE_ALL = "all";

    public static WeatherResponseDto getWeather(String address, String city,String type){
        Code code = getCode(address, city);
        if(code == null){
            log.warning("获取区域编码失败，区域为空。<<<pt提示：请检查高德地图KEY是否填写好，或者用户的地址信息是否填写好>>>");
            return null;
        }
        String adCode = code.getAdcode();
        return getWeather(adCode, type);
    }

    public static WeatherResponseDto getWeather(String cityCode,String type){

        HashMap<String,Object> map = new HashMap<>();
        map.put("key", AllConfig.WeatherKey);
        map.put("city",cityCode);
        map.put("extensions",type);
        String result = HttpUtil.get(WEATHER_URL,map);
        WeatherResponseDto weatherResponseDto =  JSONUtil.toBean(result, WeatherResponseDto.class);
        if(Objects.equals(0,weatherResponseDto.getStatus()) ||  !"10000".equals(weatherResponseDto.getInfocode())){
            log.warning("获取天气失败："+result+"<<<pt提示：请检查高德地图KEY是否填写好，或者用户地址是否填写好>>>");
            return null;
        }
        return weatherResponseDto;
    }

    public static Code getCode(String address, String city){
        if(StringUtils.isAnyBlank(address,city)){
            return null;
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("key",AllConfig.WeatherKey);
        map.put("address",address);
        map.put("city",city);
        String result = HttpUtil.get(CODE_URL,map);
        CodeResponseDto codeResponseDto = JSONUtil.toBean(result, CodeResponseDto.class);
        if(Objects.equals(0,codeResponseDto.getStatus()) ){
            log.warning("获取区域编码失败:"+codeResponseDto.getInfo());
            return null;
        }
        if(CollectionUtil.isEmpty(codeResponseDto.getGeocodes())){
            log.warning("获取区域编码失败，区域为空");
            return null;
        }
        return codeResponseDto.getGeocodes().stream().findAny().orElse(null);
    }

}
