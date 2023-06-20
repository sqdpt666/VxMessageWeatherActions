package com.pt.vx.config;

import com.pt.vx.pojo.WeatherOtherInfo;

import java.util.ArrayList;
import java.util.List;

public class MainConfig {
    /**
     * 消息模式
     * 0 正常模式
     * 1 主人模式
     */
    public static Integer messageMode = 0;



    /**
     * 关键字消息分裂
     * 如果一个{{xxx.DATA}}消息过长，则自动分裂成多条
     * 如：{{xxx.DATA}}分裂成{{xxx.DATA}}、{{xxxSplit.DATA}}、{{xxxSplit1.DATA}}
     * 会在后面添加后缀Split
     * true：开启
     * false：关闭
     */
    public static boolean keyMessageSplit = true;

    /**
     * 消息达到分裂的条件
     * 默认每20个字分裂一次
     */
    public static int splitMessageLength = 20;

    /**
     * 分裂使用的标记符号
     * {{xxxSplit.DATA}}
     */
    public static String splitMessageFlag = "Split";


    /**
     * 周显示为中文
     * true 显示为中文
     * false 显示为数字
     */
    public static boolean chineseWeek = true;

    /**
     * 历史上今天发送的事情的数目
     * 可选范围是1-15
     * 如果<1 会等于1
     * 如果>15 会等于15
     */
    public static int historyTodayCount = 3;

    /**
     * 开启API随机模式
     * 将随机获取一个API
     * 开启后，除了天气和生日外，其他API消息将失效，只有randomInfo会收到消息
     * 只有开启了的API才会参与随机
     * true 开启
     * false 关闭
     */
    public static boolean randomApiMessageMode = true;



    /**
     * 随机消息字体颜色
     * true 开启
     * false 关闭
     */
    public static boolean randomMessageColorMode = false;

    /**
     * 参与随机的颜色
     * 内容为颜色HEX码（不知道可以百度）
     */
    public static  String[] randomColors = {"#FFCCCC", "#33A1C9", "#DC143C","#FF0000","#6B8E23","#236B8E","#FF7F00"};

    /**
     * 额外消息模式
     * 额外消息有：纪念日提示额外信息、天气对应额外信息
     * 0：不展示额外消息
     * 1：只展示一种，优先展示纪念日
     * 2：全部展示
     */
    public static Integer otherInfoMode = 1;

    public static List<WeatherOtherInfo>  weatherOtherInfos = new ArrayList<>();

    /**
     * 请求API失败重试次数
     */
    public static int httpRetryCount  = 3;

    /**
     * 请求API最大等待时间，单位毫秒
     */
    public static int httpRetryTime = 5000;


    /**
     * 参数一：0代表温度相关，1代表关键字相关
     * 参数二：如果是温度，则是大于小于等于（>、<、=）的摄氏度，如果是关键字，则是要识别的关键字
     * 参数三：如果条件成立，需要输出的话
     * 如果要更多的话，只需要复制 weatherOtherInfos.add(new WeatherOtherInfo(参数一,"参数二","参数三"));
     */
    static {

        weatherOtherInfos.add(new WeatherOtherInfo(0,"<10","天气天气有点小冷，注意保暖哦~"));
        weatherOtherInfos.add(new WeatherOtherInfo(0,">30","天气热起来咯，不要穿太多咯~"));
        weatherOtherInfos.add(new WeatherOtherInfo(1,"雨","出门记得带伞哦！"));


    }


}
