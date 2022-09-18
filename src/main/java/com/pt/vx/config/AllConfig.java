package com.pt.vx.config;
import java.time.LocalDate;

import com.pt.vx.domain.BirthDay;
import com.pt.vx.domain.User;

import java.util.ArrayList;
import java.util.List;

public class AllConfig {
    public static final String VxAppId = "wx3143a73dd3efbb81";
    public static final String VxAppSecret = "3eb7987b1989e9d4d9e43b2d9259576c";
    public static final String WeatherKey = "cfd6f2b39459d5c22c3a5ca9debd6633";
    private static void init(){
//        //如果要多个人的话，就复制这个一遍，然后填写里面的内容。这里默认两个人,大伙应该是两个人吧（笑）
//        userList.add(getUser(
//                "这个人扫码后的微信号",//扫码关注你的测试号以后，测试平台会出现TA的微信号
//                "这个人的称呼",//咋称呼这个人
//                new BirthDay(1999,8,11,false),  //这个人的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
//                new BirthDay(1999,2,15,true), //这个人对象的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
//                LocalDate.of(2020,7,8),//这个人的纪念日起点，比如说在一起的那一天
//                "江苏省南京市玄武区",//这个人的详细地址
//                "南京",//这个人在的城市
//                "微信消息模板ID"));//要给这个人发送的模板ID

        userList.add(getUser(
                "oR4d76B3wGME7IBiemT4xsxBoZZQ",
                "Sweet",
                new BirthDay(1999,2,15,true),  //这个人的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                new BirthDay(1999,8,11,false), //这个人对象的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                LocalDate.of(2020,7,8),//这个人的纪念日
                "江苏省南京市玄武区",//这个人的详细地址
                "南京",//这个人在的城市
                "7enRtc32Bi1oROZ-GjCA6fRvL2j68ghyd-Rr5_zt-jA"));//要给这个人发送的模板ID

        
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
