package com.pt.vx.pojo;

import lombok.Data;

@Data
public class WeatherFuture {

    /**
     * 日期
     */
    private String date;
    /**
     * 周
     */

    private String week;

    /**
     * 白天天气
     */
    BaseWeather weatherDay;

    /**
     * 晚上天气
     */
    BaseWeather weatherNight;


    /**
     * 日出
     */
    private String sunUp;

    /**
     * 日落
     */
    private String sunDown;
}
