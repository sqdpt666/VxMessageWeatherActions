package com.pt.vx.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.pt.vx.domain.weather.*;

import java.util.Objects;
import java.util.logging.Logger;


public class WeatherUtil {

    private static final String KEY = "你的KEY";

    private static final Logger log = Logger.getAnonymousLogger();

    public static final String TYPE_LIVE = "base";
    public static final String TYPE_ALL = "all";

    public static WeatherResponseDto getWeather(String address, String city,String type){
        Code code = getCode(address, city);
        if(code == null){
            log.warning("获取区域编码失败，区域为空");
            return null;
        }
        String adCode = code.getAdcode();
        return getWeather(adCode, type);
    }

    public static WeatherResponseDto getWeather(String cityCode,String type){
        String weatherURL = "https://restapi.amap.com/v3/weather/weatherInfo?%s";
        WeatherRequestDto dto=new WeatherRequestDto();
        dto.setCity(cityCode);
        dto.setKey(KEY);
        dto.setExtensions(type);
        String params = JSONUtil.toJsonStr(dto);
        String result = HttpUtil.get(String.format(weatherURL, params));
        WeatherResponseDto weatherResponseDto =  JSONUtil.toBean(result, WeatherResponseDto.class);
        if(Objects.equals(0,weatherResponseDto.getStatus()) ||  !"10000".equals(weatherResponseDto.getInfocode())){
            log.warning("获取天气失败");
            return null;
        }
        return weatherResponseDto;
    }

    public static Code getCode(String address, String city){
        String CodeURL = "https://restapi.amap.com/v3/geocode/geo?%s";
        CodeRequestDto dto=new CodeRequestDto();
        dto.setKey(KEY);
        dto.setAddress(address);
        dto.setCity(city);
        String params = JSONUtil.toJsonStr(dto);
        String result = HttpUtil.get(String.format(CodeURL, params));
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
