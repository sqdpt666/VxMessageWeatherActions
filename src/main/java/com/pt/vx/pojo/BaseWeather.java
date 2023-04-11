package com.pt.vx.pojo;

import lombok.Data;

@Data
public class BaseWeather {

    /**
     * 天气描述
     */
    private String info;


    /**
     * 温度
     */
    private String temperature;



    /**
     * 湿度
     */
    private String humidity;


    /**
     * 风力
     */
    private String power;

    /**
     * 风向
     */
    private String wind;
}
