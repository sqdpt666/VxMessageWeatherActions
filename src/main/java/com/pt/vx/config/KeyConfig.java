package com.pt.vx.config;
/**
 * 内置的模板
 * 在微信模板里添加{{xxx.DATA}}
 *
 * weatherDay（1，2，3，4） 白天天气
 * temperatureDay（1，2，3，4）白天温度
 * windDay（1，2，3，4）白天风向
 * powerDay（1，2，3，4）白天风力
 * weatherNight（1，2，3，4） 晚上天气
 * temperatureNight（1，2，3，4） 晚上温度
 * windNight（1，2，3，4） 晚上风向
 * powerNight（1，2，3，4） 晚上风力
 *
 * 其他：（1、2、3、4......每100字一个，
 *
 */
public class KeyConfig {
   //1. 信息相关
    public static final String KEY_USER_NAME = "userName"; //昵称
    public static final String KEY_BIRTHDAY = "birthDay"; //日期计算 默认第一个为自己生日，第二个为对象生日，第三个为纪念日
    public static final String KEY_OTHER_INFO = "otherInfo"; //额外提示

   //2. 天气相关

    //2.1 天气预报，每个字段后面可以带上数字，表示后面的天数的天气预报（不带数字表示今天），比如:明天的白天天气{{weatherDay1.DATA}},
    public static final String KEY_DATE = "date"; //日期
    public static final String KEY_WEEK = "week"; //星期
    public static final String KEY_WEATHER_DAY = "weatherDay"; //白天天气
    public static final String KEY_TEMPERATURE_DAY = "temperatureDay"; //白天温度
    public static final String KEY_WIND_DAY = "windDay"; //白天风向
    public static final String KEY_POWER_DAY = "powerDay"; //白天风力
    public static final String KEY_WEATHER_NIGHT = "weatherNight"; //晚上天气
    public static final String KEY_TEMPERATURE_NIGHT = "temperatureNight"; //晚上温度
    public static final String KEY_WIND_NIGHT = "windNight"; //晚上风向
    public static final String KEY_POWER_NIGHT = "powerNight"; //晚上风力
    //2.2 实时天气
    public static final String KEY_WEATHER_NOW = "weatherNow"; //现在天气
    public static final String KEY_TEMPERATURE_NOW = "temperatureNow"; //现在温度
    public static final String KEY_WIND_NOW = "windNow"; //现在风向
    public static final String KEY_POWER_NOW = "powerNow"; //现在风力
    public static final String KEY_HUMIDITY_NOW = "humidityNow"; //现在湿度


   //3. 额外信息类型
    public static final String KEY_RANDOM_READ = "randomRead"; //精彩短句
    public static final String KEY_WORLD_READ = "worldRead"; //世界新闻
    public static final String KEY_TIAN_GOU = "tianGou"; //舔狗日记
    public static final String KEY_QING_HUA = "qingHua"; //情话
    public static final String KEY_ENGLISH = "english"; //励志英语
    public static final String KEY_HISTORY_TODAY = "historyToday"; //历史的今天
    public static final String KEY_HOROSCOPE = "horoscope"; //星座解析
    public static final String KEY_RANDOM_INFO = "randomInfo"; //随机额外类型消息
    public static final String KEY_WO_ZAI_REN_JIAN = "wozairenjian"; //我在人间凑日子散文
    public static final String KEY_MI_YU = "miyu"; //谜语
    public static final String KEY_POETRY = "poetry"; //诗句
    public static final String KEY_DONG_MAN = "dongman"; //动漫



}
