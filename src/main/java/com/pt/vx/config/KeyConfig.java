package com.pt.vx.config;

import com.pt.vx.pojo.KeyDTO;

/**
 * 所有内置的模板
 * new KeyDTO("模板key", "颜色", 是否开启);
 * 在微信模板里添加 {{模板key.DATA}}
 */
public class KeyConfig {
    /**
     * 昵称，在模板里面就是{{userName.DATA}}
     */
    public static final KeyDTO KEY_USER_NAME = new KeyDTO("userName", "#FFFFFF", true);

    /**
     * 日期计算
     */
    public static final KeyDTO KEY_BIRTHDAY = new KeyDTO("birthDay","#FFFFFF",true);
    /**
     * 额外提示
     */
    public static final KeyDTO KEY_OTHER_INFO = new KeyDTO("otherInfo","#FFFFFF",true);

    /**
     * 日期
     */
    public static final KeyDTO KEY_DATE = new KeyDTO("date","#FFFFFF",true);
    /**
     * 星期
     */
    public static final KeyDTO KEY_WEEK = new KeyDTO("week","#FFFFFF",true);
    /**
     * 白天天气
     */
    public static final KeyDTO KEY_WEATHER_DAY = new KeyDTO("weatherDay","#FFFFFF",true);
    /**
     * 白天温度
     */
    public static final KeyDTO KEY_TEMPERATURE_DAY = new KeyDTO("temperatureDay","#FFFFFF",true);
    /**
     * 白天风向
     */
    public static final KeyDTO KEY_WIND_DAY = new KeyDTO("windDay","#FFFFFF",true);
    /**
     * 白天风力
     */
    public static final KeyDTO KEY_POWER_DAY = new KeyDTO("powerDay","#FFFFFF",true);

    /**
     * 晚上天气
     */
    public static final KeyDTO KEY_WEATHER_NIGHT = new KeyDTO("weatherNight","#FFFFFF",true);
    /**
     * 晚上温度
     */
    public static final KeyDTO KEY_TEMPERATURE_NIGHT = new KeyDTO("temperatureNight","#FFFFFF",true);

    /**
     * 晚上风向
     */
    public static final KeyDTO KEY_WIND_NIGHT = new KeyDTO("windNight","#FFFFFF",true);

    /**
     * 晚上风力
     */
    public static final KeyDTO KEY_POWER_NIGHT = new KeyDTO("powerNight","#FFFFFF",true);
    /**
     * 现在天气
     */
    public static final KeyDTO KEY_WEATHER_NOW = new KeyDTO("weatherNow","#FFFFFF",true);
    /**
     * 实时天气
     */
    public static final KeyDTO KEY_TEMPERATURE_NOW = new KeyDTO("temperatureNow","#FFFFFF",true);
    /**
     * 现在风向
     */
    public static final KeyDTO KEY_WIND_NOW = new KeyDTO("windNow","#FFFFFF",true);
    /**
     * 现在风力
     */
    public static final KeyDTO KEY_POWER_NOW = new KeyDTO("powerNow","#FFFFFF",true);
    /**
     * 现在湿度
     */
    public static final KeyDTO KEY_HUMIDITY_NOW = new KeyDTO("humidityNow","#FFFFFF",true);
    /**
     * 日出时间
     * 只有和风天气才有
     */
    public static final KeyDTO KEY_SUN_RISE = new KeyDTO("sunrise","#FFFFFF",true);

    /**
     * 日落时间
     * 只有和风天气才有
     */
    public static final KeyDTO KEY_SUN_SET = new KeyDTO("sunset","#FFFFFF",true);

    /**
     * 随机API消息
     */
    public static final KeyDTO KEY_RANDOM_INFO = new KeyDTO("randomInfo","#FFFFFF",true);

    /**
     * 情话
     */
    public static final KeyDTO KEY_QING_HUA = new KeyDTO("qingHua","#FFFFFF",true);

    /**
     * 随机段子
     */
    public static final KeyDTO KEY_DUAN_ZI = new KeyDTO("duanZi","#FFFFFF",true);

    /**
     * 随机毒鸡汤
     */
    public static final KeyDTO KEY_DU_JI_TANG = new KeyDTO("duJiTang","#FFFFFF",true);

    /**
     * 随机短句
     */
    public static final KeyDTO KEY_SENTENCE= new KeyDTO("sentence","#FFFFFF",true);

    /**
     * 谜语
     */
    public static final KeyDTO KEY_MI_YU= new KeyDTO("miyu","#FFFFFF",true);

    /**
     * 星座解析
     * 用户信息填写了BirthDay的话，则会自动解析星座
     */
    public static final KeyDTO KEY_HOROSCOPE= new KeyDTO("horoscope","#FFFFFF",true);

    /**
     * 历史的今天发生的3件事情
     */
    public static final KeyDTO KEY_HISTORY_TODAY= new KeyDTO("historyToday","#FFFFFF",true);

    /**
     * 新冠
     */
    public static final KeyDTO KEY_XIN_GUAN= new KeyDTO("xinGuan","#FFFFFF",true);




}
