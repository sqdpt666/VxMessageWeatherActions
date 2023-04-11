package com.pt.vx.service.weather;

import com.pt.vx.pojo.BaseWeather;
import com.pt.vx.pojo.Weather;
import com.pt.vx.pojo.WeatherFuture;

import java.util.List;

/**
 * 天气接口，所有天气服务实现该接口
 */
public interface WeatherService {

     /**
      *
      * @param address 地址
      * @param city  城市
      * @return 天气
      * @throws Exception 异常消息为返回的结果
      */
     List<Weather> getWeather(String address, String city) throws Exception;

     BaseWeather getWeatherNow(String address, String city) throws Exception;

     List<WeatherFuture> getWeatherFuture(String address, String city) throws Exception;

     /**
      *
      * @param type 天气类型
      * @return 对于天气源的天气类型
      */
     String getWeatherType(Integer type);

     /**
      *
      * @param address 地址
      * @param city  城市
      * @return 城市编码
      * @throws Exception 异常消息为返回的结果
      */
     String getCityCode(String address,String city) throws Exception;
}
