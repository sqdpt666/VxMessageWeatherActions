package com.pt.vx.domain.weather;

import java.util.List;


public class WeatherResponseDto {

    private Integer status;

    private Integer count;

    private String info;

    private String infocode;

    private List<WeatherLiveDto> lives;

    private WeatherForecastDto forecast;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<WeatherLiveDto> getLives() {
        return lives;
    }

    public void setLives(List<WeatherLiveDto> lives) {
        this.lives = lives;
    }

    public WeatherForecastDto getForecast() {
        return forecast;
    }

    public void setForecast(WeatherForecastDto forecast) {
        this.forecast = forecast;
    }
}
