package com.pt.vx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherOtherInfo {

    /**
     * 0 代表温度
     * 1 代表关键字
     */
    private int type;

    /**
     * 如果是温度相关的信息
     * 则写： >xx/<xx/=xx，如：<10,代表小于10摄氏度
     * 如果是关键字相关的信息
     * 则写要识别的关键字，如：雨，代表天气描述中有 雨 这个字
     */
    private String key;

    /**
     * 需要发送的消息
     */
    private String message;

}
