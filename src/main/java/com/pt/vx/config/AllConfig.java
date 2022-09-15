package com.pt.vx.config;
import java.time.LocalDate;

import com.pt.vx.domain.BirthDay;
import com.pt.vx.domain.User;

import java.util.ArrayList;
import java.util.List;

public class AllConfig {
    public static final String VxAppId = "微信的APPID";
    public static final String VxAppSecret = "微信的密钥";
    public static final String WeatherKey = "高德地图key";
    private static void init(){
        //如果要多个人的话，就复制这个一遍，然后填写里面的内容。这里默认两个人,大伙应该是两个人吧（笑）
        userList.add(getUser(
                "你自己扫码后的微信号",
                "她的名字",
                new BirthDay(1999,8,11,false),  //最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                new BirthDay(1999,2,15,true), //最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                LocalDate.of(2020,7,8),
                "江苏省南京市玄武区",
                "南京",
                "微信消息模板ID"));

        userList.add(getUser(
                "你自己扫码后的微信号",
                "她的名字",
                new BirthDay(1999,2,15,true),  //最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                new BirthDay(1999,8,11,false), //最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                LocalDate.of(2020,7,8),
                "江苏省南京市玄武区",
                "南京",
                "微信消息模板ID"));

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
