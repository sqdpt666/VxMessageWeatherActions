package com.pt.vx.service;


import cn.hutool.json.JSONUtil;
import com.pt.vx.domain.DataInfo;
import com.pt.vx.domain.VxMessageDto;
import com.pt.vx.domain.weather.Cast;
import com.pt.vx.domain.weather.WeatherForecastDto;
import com.pt.vx.domain.weather.WeatherResponseDto;
import com.pt.vx.utils.DateUtil;
import com.pt.vx.utils.VxUtil;
import com.pt.vx.utils.WeatherUtil;

import java.util.HashMap;
import java.util.List;

public class MessageService {


    /**
     *
     * 当前模板为：可以复制到你的模板里面
     *
     * 	{{userName.DATA}}，
     * 	今天是我们在一起的{{holdDay.DATA}}天
     * 	你的生日还有{{yourBirthDay.DATA}}天
     * 	我的生日还有{{myBirthDay.DATA}}天
     * 	距离我们的下一次纪念还有{{loveDay.DATA}}天
     * 	今天白天{{weatherDay.DATA}}，温度{{temperatureDay.DATA}}℃
     * 	今天晚上{{weatherNight.DATA}}，温度{{temperatureNight.DATA}}℃
     *
     * 	最后，开心每一天！
     */
    public void sendMessage(){
        VxMessageDto dto = new VxMessageDto();
        dto.setTemplate_id("修改成你的模板ID");  //修改成你的模板ID
        dto.setTouser("修改成你的用户ID"); //修改成你的用户ID
        HashMap<String, DataInfo> map = new HashMap<>();
        setMap(map,"userName","改成她的名字","#FFCCCC"); //改成她的名字
        setWeather(map,"江苏省南京市玄武区红山街道", "南京", WeatherUtil.TYPE_ALL); //改成她的地址与城市
        setAndSend(dto,map);
    }

    public void sendMessage(String id,String uid,String name,String address,String city){
        VxMessageDto dto = new VxMessageDto();
        dto.setTemplate_id(id);
        dto.setTouser(uid);
        HashMap<String, DataInfo> map = new HashMap<>();
        setMap(map,"userName",name,"#FFCCCC");
        setWeather(map,address, city, WeatherUtil.TYPE_ALL);
        setAndSend(dto,map);
    }

    private void setWeather(HashMap<String, DataInfo> map,String address,String city,String type){
        WeatherResponseDto weather = WeatherUtil.getWeather(address,city,type);
        if(weather != null){
            WeatherForecastDto forecast = weather.getForecasts().get(0);
            List<Cast> casts = forecast.getCasts();
            Cast cast = casts.get(0);//获取天气预报（实际上可以获取到5天的天气预报，0代表今天，之后为1，2，3，4）
            if(cast != null){
                setMap(map,"weatherDay",cast.getDayweather(),"#33A1C9");
                setMap(map,"temperatureDay",cast.getDaytemp() ,"#33A1C9");
                setMap(map,"weatherNight",cast.getNightweather(),"#33A1C9");
                setMap(map,"temperatureNight",cast.getNighttemp() ,"#33A1C9");
                return;
            }
        }
        setMap(map,"weatherDay","未知","#33A1C9");
        setMap(map,"temperatureDay","未知" ,"#33A1C9");
        setMap(map,"weatherNight","未知","#33A1C9");
        setMap(map,"temperatureNight","未知" ,"#33A1C9");
    }

    /**
     * 通用的日期信息
     */
    private void setAndSend(VxMessageDto dto,HashMap<String, DataInfo> map){
        setMap(map,"holdDay", DateUtil.passDay(2020,7,8),"#FFCCCC"); //改成你在一起的时间
        setMap(map,"yourBirthDay",DateUtil.getNextBirthDay(8,11),"#FFCCCC"); //改成她的生日
        setMap(map,"myBirthDay", DateUtil.getNextChineseBirthDay(2,15),"#FFCCCC"); //改成你的生日
        setMap(map,"loveDay",DateUtil.getNextBirthDay(7,8),"#FFCCCC"); //改成你在一起的时间
        dto.setData(map);
        String message = JSONUtil.toJsonStr(dto);
        VxUtil.sendMessage(message);
    }


    /**
     *
     * @param key 模板的每项key
     * @param value 展示的内容
     * @param color 展示的颜色
     */
    private void setMap(HashMap<String, DataInfo> map,String key,String value,String color){
        DataInfo dataInfo=new DataInfo();
        dataInfo.setColor(color);
        dataInfo.setValue(value);
        map.put(key,dataInfo);
    }





}
