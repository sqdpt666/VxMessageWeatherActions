package com.pt.vx.config;
import java.time.LocalDate;

import com.pt.vx.domain.BirthDay;
import com.pt.vx.domain.User;

import java.util.ArrayList;
import java.util.List;

public class AllConfig {
    public static final String VxAppId = "wxfbe388e4291d61db";
    public static final String VxAppSecret = "5b215f3d9243d5d0bb666859401f4502";
    public static final String WeatherKey = "3d0bd550f59f98e72fb33257a0f5b434";
    private static void init(){
        //如果要多个人的话，就复制这个一遍，然后填写里面的内容。这里默认两个人,大伙应该是两个人吧（笑）
        userList.add(getUser(
                "osnZg5-Yikkpkc2BPdbKxPOPwX0E",//扫码关注你的测试号以后，测试平台会出现TA的微信号
                "亲爱的老婆",//咋称呼这个人
                new BirthDay(2002,1,5,true),  //这个人的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                new BirthDay(2002,3,12,true), //这个人对象的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                LocalDate.of(2022,3,30),//这个人的纪念日起点，比如说在一起的那一天
                "山东省德州市德城区",//这个人的详细地址
                "德州",//这个人在的城市
                "lbeCnWiQNfjdvVloeBNNX6_ayRrN7T35d6VjhboqDSs"));//要给这个人发送的模板ID

        userList.add(getUser(
                "这个人扫码后的微信号",
                "这个人的名字",
                new BirthDay(1999,2,15,true),  //这个人的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                new BirthDay(1999,8,11,false), //这个人对象的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                LocalDate.of(2020,7,8),//这个人的纪念日
                "江苏省南京市玄武区",//这个人的详细地址
                "南京",//这个人在的城市
                "微信消息模板ID"));//要给这个人发送的模板ID

        
    }

    public static final List<User> userList = new ArrayList<>();
    static {
        init();
    }


    private static User getUser(String Vx, String username,
                                BirthDay birthday,
                                BirthDay careDay,
                                LocalDate loveDay,
                                String address, String city, String templateId){
        User user=new User();
        user.setVx(Vx);
        user.setUserName(username);
        user.setCareDay(careDay);
        user.setBirthDay(birthday);
        user.setLoveDay(loveDay);
        user.setAddress(address);
        user.setCity(city);
        user.setTemplateId(templateId);
        return user;
    }



}
