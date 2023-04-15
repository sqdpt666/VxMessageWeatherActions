package com.pt.vx.service.weather;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pt.vx.config.WeatherConfig;
import com.pt.vx.pojo.BaseWeather;
import com.pt.vx.pojo.Weather;
import com.pt.vx.pojo.WeatherFuture;
import com.pt.vx.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 和风天气
 * API地址：https://dev.qweather.com/docs/api/
 */
@Slf4j
public class HeFengWeatherService implements WeatherService{

    private static final String API = "https://devapi.qweather.com/v7/weather/%s";
    private static final String CITY = "https://geoapi.qweather.com/v2/city/lookup?";

    @Override
    public List<Weather> getWeather(String address, String city) throws Exception {
        List<Weather> weatherList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        map.put("key", WeatherConfig.weatherSourceKey);
        map.put("location",getCityCode(address,city));
        String result = HttpUtil.get(String.format(API, getWeatherType(WeatherConfig.getWeatherType)), map);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if("200".equals(jsonObject.getStr("code"))){
            JSONObject now = jsonObject.getJSONObject("now");
            if(!now.isEmpty()){
                Weather weather = new Weather();
                weather.setDate(now.getStr("obsTime"));
                weather.setType(0);
                weather.setInfo(now.getStr("text"));
                weather.setTemperature(now.getStr("temp"));
                weather.setPower(now.getStr("windScale"));
                weather.setWind(now.getStr("windDir"));
            }


            JSONArray daily = jsonObject.getJSONArray("daily");
            if(!daily.isEmpty()){
                for (int i = 0; i < daily.size(); i++) {
                    JSONObject day = daily.getJSONObject(i);

                    Weather weatherDay = new Weather();
                    weatherDay.setType(1);
                    weatherDay.setDate(day.getStr("fxDate"));
                    weatherDay.setInfo(day.getStr("textDay"));
                    weatherDay.setTemperature(day.getStr("tempMax"));
                    weatherDay.setPower(day.getStr("windScaleDay"));
                    weatherDay.setWind(day.getStr("windDirDay"));
                    weatherDay.setSunUp(day.getStr("sunrise"));
                    weatherDay.setSunDown(day.getStr("sunset"));
                    weatherList.add(weatherDay);


                    Weather weatherNight = new Weather();
                    weatherNight.setType(2);
                    weatherNight.setDate(day.getStr("fxDate"));
                    weatherNight.setInfo(day.getStr("textNight"));
                    weatherNight.setTemperature(day.getStr("tempMin"));
                    weatherNight.setPower(day.getStr("windScaleNight"));
                    weatherNight.setWind(day.getStr("windDirNight"));
                    weatherNight.setSunUp(day.getStr("sunrise"));
                    weatherNight.setSunDown(day.getStr("sunset"));
                    weatherList.add(weatherNight);
                }
            }
            return weatherList;
        }
       throw new Exception(result);
    }

    @Override
    public BaseWeather getWeatherNow(String address, String city) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("key", WeatherConfig.weatherSourceKey);
        map.put("location",getCityCode(address,city));
        String result = HttpUtil.get(String.format(API, getWeatherType(WeatherConfig.getWeatherType)), map);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if("200".equals(jsonObject.getStr("code"))){
            JSONObject now = jsonObject.getJSONObject("now");
            if(!now.isEmpty()){
                BaseWeather weather = new BaseWeather();

                weather.setInfo(now.getStr("text"));
                weather.setTemperature(now.getStr("temp"));
                weather.setPower(now.getStr("windScale"));
                weather.setWind(now.getStr("windDir"));
                return weather;
            }
        }
        throw new Exception(result);
    }

    @Override
    public List<WeatherFuture> getWeatherFuture(String address, String city) throws Exception {
        List<WeatherFuture> weatherList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        map.put("key", WeatherConfig.weatherSourceKey);
        map.put("location",getCityCode(address,city));
        String result = HttpUtil.get(String.format(API, getWeatherType(WeatherConfig.getWeatherType)), map);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if("200".equals(jsonObject.getStr("code"))){

            JSONArray daily = jsonObject.getJSONArray("daily");
            if(!daily.isEmpty()){
                for (int i = 0; i < daily.size(); i++) {
                    JSONObject day = daily.getJSONObject(i);
                    WeatherFuture future = new WeatherFuture();
                    BaseWeather weatherDay = new BaseWeather();


                    weatherDay.setInfo(day.getStr("textDay"));
                    weatherDay.setTemperature(day.getStr("tempMax"));
                    weatherDay.setPower(day.getStr("windScaleDay"));
                    weatherDay.setWind(day.getStr("windDirDay"));

                    BaseWeather weatherNight = new BaseWeather();
                    weatherNight.setInfo(day.getStr("textNight"));
                    weatherNight.setTemperature(day.getStr("tempMin"));
                    weatherNight.setPower(day.getStr("windScaleNight"));
                    weatherNight.setWind(day.getStr("windDirNight"));

                    future.setDate(day.getStr("fxDate"));
                    future.setSunUp(day.getStr("sunrise"));
                    future.setSunDown(day.getStr("sunset"));
                    future.setWeatherNight(weatherNight);
                    future.setWeatherDay(weatherDay);
                    weatherList.add(future);
                }
            }
            return weatherList;
        }
        throw new Exception(result);
    }

    @Override
    public String getWeatherType(Integer type) {
        if(Objects.equals(type,0)){
            return "now";
        }else {
            return "7d";
        }

    }

    @Override
    public String getCityCode(String address, String city) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("key",WeatherConfig.weatherSourceKey);
        map.put("location",city);
        map.put("range","cn");
        String result = HttpUtil.get(CITY, map);
        JSONObject jsonObject = JSONUtil.parseObj(result);

        if("200".equals(jsonObject.getStr("code"))){
            JSONArray locations = jsonObject.getJSONArray("location");
            if(!locations.isEmpty()){
                JSONObject cityLocation = locations.getJSONObject(0);
                return cityLocation.getStr("id");
            }
        }

        throw new Exception(result);
    }




}
