package com.pt.vx.service.weather;

import com.pt.vx.config.WeatherConfig;
import com.pt.vx.pojo.BaseWeather;
import com.pt.vx.pojo.Weather;
import com.pt.vx.pojo.WeatherFuture;

import java.util.List;
import java.util.Objects;

public class WeatherAdapter implements WeatherService{

    private final WeatherService weatherService;

    public WeatherAdapter(){
        Integer weatherSourceType = WeatherConfig.weatherSourceType;
        if (Objects.equals(0,weatherSourceType)){
            weatherService = new GaoDeWeatherService();
        }else {
            weatherService = new HeFengWeatherService();
        }
    }


    @Override
    public List<Weather> getWeather(String address, String city) throws Exception {

        return weatherService.getWeather(address,city);
    }

    @Override
    public BaseWeather getWeatherNow(String address, String city) throws Exception {
        return weatherService.getWeatherNow(address,city);
    }

    @Override
    public List<WeatherFuture> getWeatherFuture(String address, String city) throws Exception {
        return weatherService.getWeatherFuture(address,city);
    }

    @Override
    public String getWeatherType(Integer type) {
        return weatherService.getWeatherType(type);
    }

    @Override
    public String getCityCode(String address, String city) throws Exception {
        return weatherService.getCityCode(address,city);
    }
}
