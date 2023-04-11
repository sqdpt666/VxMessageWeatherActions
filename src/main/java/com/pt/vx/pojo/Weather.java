package com.pt.vx.pojo;

import lombok.Data;

@Data
public class Weather {

    /**
     * 日期
     */
    private String date;
    /**
     * 周
     */

    private String week;

    /**
     * 天气类型
     * 0 现在
     * 1 白天
     * 2 晚上
     */
    private Integer type;

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

    /**
     * 日出
     */
    private String sunUp;

    /**
     * 日落
     */
    private String sunDown;



}
