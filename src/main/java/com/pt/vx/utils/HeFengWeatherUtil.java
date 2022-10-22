package com.pt.vx.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.pt.vx.config.AllConfig;
import com.pt.vx.domain.hefeng.CityLocation;
import com.pt.vx.domain.hefeng.HfWeatherResult;
import com.pt.vx.domain.hefeng.LocationResult;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class HeFengWeatherUtil {

    private static final String API = "https://devapi.qweather.com/v7/weather/%s";
    private static final String CITY = "https://geoapi.qweather.com/v2/city/lookup?";
    private static final String KEY = AllConfig.WeatherKey_HeFeng;

    public static final String TYPE_NOW ="now";
    public static final String TYPE_DAY = "7d";

    private static final Logger log = Logger.getAnonymousLogger();

    public static String getCityLocation(String city){
        HashMap<String,Object> map = new HashMap<>();
        map.put("key",KEY);
        map.put("location",city);
        map.put("range","cn");
        String result = HttpUtil.get(CITY, map);
        LocationResult locationResult = JSONUtil.toBean(result, LocationResult.class);
        if("200".equals(locationResult.getCode())){
            List<CityLocation> cityLocations = locationResult.getLocation();
            if(CollectionUtil.isNotEmpty(cityLocations)){
                CityLocation cityLocation = cityLocations.get(0);
                log.info("和风天气获取城市信息成功。数据为："+JSONUtil.toJsonStr(cityLocation));
                return cityLocation.getId();
            }
        }else if("401".equals(locationResult.getCode())){
            log.warning("和风天气获取城市信息失败。原因：认证失败，可能使用了错误的KEY");
        }else{
            log.warning("和风天气获取城市信息失败");
        }
        return null;
    }

    public static HfWeatherResult getWeather(String city,String type){
       HashMap<String,Object> map = new HashMap<>();
       map.put("key",KEY);
       map.put("location",getCityLocation(city));
       String result = HttpUtil.get(String.format(API, type), map);
       log.info("获取和风天气结果为："+result);
       return JSONUtil.toBean(result, HfWeatherResult.class);
    }


}
