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
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 高德地图天气
 * API地址：https://lbs.amap.com/api/webservice/guide/api/weatherinfo
 */
@Slf4j
public class GaoDeWeatherService implements WeatherService{


    private  final  String weatherUrl = "https://restapi.amap.com/v3/weather/weatherInfo";

    private  final  String codeUrl = "https://restapi.amap.com/v3/geocode/geo";


    @Override
    public List<Weather> getWeather(String address, String city) throws Exception {
        List<Weather> weatherList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        map.put("key", WeatherConfig.weatherSourceKey);
        map.put("city", getCityCode(address, city));
        map.put("extensions",getWeatherType(WeatherConfig.getWeatherType));
        String result = HttpUtil.get(weatherUrl,map);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if(Objects.equals(0,jsonObject.getInt("status")) ){
            throw new Exception(result);
        }

        //实时天气数据构造
        JSONArray lives = jsonObject.getJSONArray("lives");
        if(!lives.isEmpty()){
            for (int i = 0; i < lives.size(); i++) {
                JSONObject live = lives.getJSONObject(i);
                Weather weather = new Weather();
                weather.setType(0);
                weather.setInfo(live.getStr("weather"));
                weather.setTemperature(live.getStr("temperature"));
                weather.setHumidity(live.getStr("humidity"));
                weather.setPower(live.getStr("windpower"));
                weather.setWind(live.getStr("winddirection"));
                weatherList.add(weather);
            }
        }

        //天气预报数据构造
        JSONArray forecasts = jsonObject.getJSONArray("forecast");
        if(!forecasts.isEmpty()){
            for (int i = 0; i < forecasts.size(); i++) {
                JSONObject forecast = forecasts.getJSONObject(i);
                JSONArray casts = forecast.getJSONArray("casts");
                if(!casts.isEmpty()){
                    for (int j = 0; i < casts.size(); j++) {
                        JSONObject cast = casts.getJSONObject(j);

                        Weather weatherDay = new Weather();
                        weatherDay.setType(1);
                        weatherDay.setDate(cast.getStr("date"));
                        weatherDay.setWeek(cast.getStr("week"));
                        weatherDay.setInfo(cast.getStr("dayweather"));
                        weatherDay.setTemperature(cast.getStr("daytemp"));
                        weatherDay.setPower(cast.getStr("daypower"));
                        weatherDay.setWind(cast.getStr("daywind"));
                        weatherList.add(weatherDay);


                        Weather weatherNight = new Weather();
                        weatherNight.setType(2);
                        weatherNight.setDate(cast.getStr("date"));
                        weatherNight.setWeek(cast.getStr("week"));
                        weatherNight.setInfo(cast.getStr("nightweather"));
                        weatherNight.setTemperature(cast.getStr("nighttemp"));
                        weatherNight.setPower(cast.getStr("nightpower"));
                        weatherNight.setWind(cast.getStr("nightwind"));
                        weatherList.add(weatherNight);
                    }
                }
            }
        }


        return weatherList;
    }

    @Override
    public BaseWeather getWeatherNow(String address, String city) throws Exception {
        BaseWeather weather = new BaseWeather();
        HashMap<String,Object> map = new HashMap<>();
        map.put("key", WeatherConfig.weatherSourceKey);
        map.put("city", getCityCode(address, city));
        map.put("extensions",getWeatherType(WeatherConfig.getWeatherType));
        String result = HttpUtil.get(weatherUrl,map);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if(Objects.equals(0,jsonObject.getInt("status")) ){
            throw new Exception(result);
        }
        //实时天气数据构造
        JSONArray lives = jsonObject.getJSONArray("lives");
        if(!lives.isEmpty()){
            for (int i = 0; i < lives.size(); i++) {
                JSONObject live = lives.getJSONObject(i);
                weather.setInfo(live.getStr("weather"));
                weather.setTemperature(live.getStr("temperature"));
                weather.setHumidity(live.getStr("humidity"));
                weather.setPower(live.getStr("windpower"));
                weather.setWind(live.getStr("winddirection"));
            }
        }

        return weather;
    }

    @Override
    public List<WeatherFuture> getWeatherFuture(String address, String city) throws Exception {
        List<WeatherFuture> weatherList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        map.put("key", WeatherConfig.weatherSourceKey);
        map.put("city", getCityCode(address, city));
        map.put("extensions",getWeatherType(WeatherConfig.getWeatherType));
        String result = HttpUtil.get(weatherUrl,map);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if(Objects.equals(0,jsonObject.getInt("status")) ){
            throw new Exception(result);
        }

        //天气预报数据构造
        JSONArray forecasts = jsonObject.getJSONArray("forecast");
        if(!forecasts.isEmpty()){
            for (int i = 0; i < forecasts.size(); i++) {
                JSONObject forecast = forecasts.getJSONObject(i);
                JSONArray casts = forecast.getJSONArray("casts");
                if(!casts.isEmpty()){
                    for (int j = 0; i < casts.size(); j++) {
                        JSONObject cast = casts.getJSONObject(j);
                        WeatherFuture future = new WeatherFuture();

                        BaseWeather weatherDay = new BaseWeather();
                        weatherDay.setInfo(cast.getStr("dayweather"));
                        weatherDay.setTemperature(cast.getStr("daytemp"));
                        weatherDay.setPower(cast.getStr("daypower"));
                        weatherDay.setWind(cast.getStr("daywind"));

                        BaseWeather weatherNight = new BaseWeather();
                        weatherNight.setInfo(cast.getStr("nightweather"));
                        weatherNight.setTemperature(cast.getStr("nighttemp"));
                        weatherNight.setPower(cast.getStr("nightpower"));
                        weatherNight.setWind(cast.getStr("nightwind"));

                        future.setWeatherDay(weatherDay);
                        future.setWeatherNight(weatherNight);
                        future.setDate(cast.getStr("date"));
                        future.setWeek(cast.getStr("week"));

                    }
                }
            }
        }


        return weatherList;
    }

    @Override
    public String getWeatherType(Integer type){
        if(Objects.equals(type,0)){
            return "base";
        }else {
            return "all";
        }
    }

    @Override
    public String getCityCode(String address, String city) throws Exception {
        if(StringUtils.isAnyBlank(address,city)){
            return null;
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("key", WeatherConfig.weatherSourceKey);
        map.put("address",address);
        map.put("city",city);
        String result = HttpUtil.get(codeUrl,map);
        JSONObject jsonObject = JSONUtil.parseObj(result);

        if( jsonObject.isEmpty() ||  Objects.equals(0,jsonObject.getInt("status")) ){
            throw new Exception(result);
        }

        JSONArray geocodes = jsonObject.getJSONArray("geocodes");
        if(geocodes.isEmpty()){
            throw new Exception(result);
        }

        JSONObject code = geocodes.getJSONObject(0);
        return code.getStr("adcode");
    }


}
