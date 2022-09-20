package com.pt.vx.service;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.pt.vx.domain.BirthDay;
import com.pt.vx.domain.DataInfo;
import com.pt.vx.domain.User;
import com.pt.vx.domain.VxMessageDto;
import com.pt.vx.domain.weather.Cast;
import com.pt.vx.domain.weather.WeatherForecastDto;
import com.pt.vx.domain.weather.WeatherLiveDto;
import com.pt.vx.domain.weather.WeatherResponseDto;
import com.pt.vx.utils.DateUtil;
import com.pt.vx.utils.VxUtil;
import com.pt.vx.utils.WeatherUtil;

import java.util.*;

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
    public void sendMessage(User user){
        VxMessageDto dto = new VxMessageDto();
        dto.setTemplate_id(user.getTemplateId());
        dto.setTouser(user.getVx());
        HashMap<String, DataInfo> map = new HashMap<>();
        setMap(map,"userName",user.getUserName(),"#FFCCCC");
        setMap(map,"holdDay", DateUtil.passDayOfNow(user.getLoveDay()),"#FFCCCC"); 
        setMap(map,"yourBirthDay",getBirthDay(user.getBirthDay()),"#FFCCCC"); 
        setMap(map,"myBirthDay", getBirthDay(user.getCareDay()),"#FFCCCC");
        setMap(map,"loveDay",DateUtil.getNextBirthDay(user.getLoveDay()),"#FFCCCC");
        setWeather(map,user.getAddress(), user.getCity(), WeatherUtil.TYPE_ALL);
        setOtherInfo(map);
        dto.setData(map);
        String message = JSONUtil.toJsonStr(dto);
        VxUtil.sendMessage(message);
    }
    
    private String getBirthDay(BirthDay birthDay){
        int month = birthDay.getMonth();
        int day = birthDay.getDay();
        return birthDay.isChinese()?
                DateUtil.getNextChineseBirthDay(month,day) :
                DateUtil.getNextBirthDay(month,day);
    }

    private void setOtherInfo(HashMap<String, DataInfo> map){
        String other = "";
        DataInfo weatherDay = map.get("weatherDay");
        DataInfo weatherNight = map.get("weatherNight");
        DataInfo temperatureDay = map.get("temperatureDay");
        DataInfo temperatureNight = map.get("temperatureNight");
        DataInfo yourBirthDay = map.get("yourBirthDay");
        DataInfo loveDay = map.get("loveDay");

        if(Objects.equals(Integer.valueOf(yourBirthDay.getValue()) , 0)){
            other = "happy birthday!!!";
        }else if(Objects.equals(Integer.valueOf(loveDay.getValue()) , 0)){
            other = "周年快乐！！！";
        }else if(weatherDay.getValue().contains("雨")){
            other = "白天出门记得带伞哦~";
        }else if(weatherNight.getValue().contains("雨")){
            other = "晚上出门记得带伞哦~";
        }else if( Integer.parseInt(temperatureDay.getValue()) < 10){
            other = "多穿点衣服哦！";
        }else if( Integer.parseInt(temperatureNight.getValue()) < 10){
            other = "多穿点衣服哦！";
        } else if( Integer.parseInt(temperatureDay.getValue()) < 20){
            other = "注意别着凉啦~";
        }else if( Integer.parseInt(temperatureNight.getValue()) < 20){
            other = "注意别着凉啦~";
        }
        setMap(map,"otherInfo",other,"#DC143C");
    }
    private void setWeather(HashMap<String, DataInfo> map,String address,String city,String type){
        WeatherResponseDto weather = WeatherUtil.getWeather(address,city,type);
        if(weather == null){
            setWeatherDefault(map);
            return;
        }
        if(WeatherUtil.TYPE_ALL.equals(type)){
            WeatherForecastDto forecast = weather.getForecasts().get(0);
            List<Cast> casts = forecast.getCasts();
            for(int i=0; i<casts.size(); i++ ){
                Cast cast = casts.get(i);//获取天气预报（实际上可以获取到5天的天气预报，0代表今天，之后为1，2，3，4）
                String ap = "";
                if(i>0){
                    ap = i+"";
                }
                setMap(map,"weatherDay"+ap,cast.getDayweather(),"#33A1C9");//白天天气
                setMap(map,"temperatureDay"+ap,cast.getDaytemp() ,"#33A1C9");//白天温度
                setMap(map,"weatherNight"+ap,cast.getNightweather(),"#33A1C9");//晚上天气
                setMap(map,"temperatureNight"+ap,cast.getNighttemp() ,"#33A1C9");//晚上温度
                setMap(map,"windDay"+ap,cast.getDayweather(),"#33A1C9");//白天风向
                setMap(map,"windNight"+ap,cast.getDaytemp() ,"#33A1C9");//晚上风向
                setMap(map,"powerDay"+ap,cast.getNightweather(),"#33A1C9");//白天风力
                setMap(map,"windNight"+ap,cast.getNighttemp() ,"#33A1C9");//晚上风力
                setMap(map,"date"+ap,cast.getNightweather(),"#33A1C9");//日期
                setMap(map,"week"+ap,cast.getNighttemp() ,"#33A1C9");//星期几
            }
        }else if(WeatherUtil.TYPE_LIVE.equals(type)){
            List<WeatherLiveDto> lives = weather.getLives();
            if(CollUtil.isNotEmpty(lives)){
                WeatherLiveDto liveDto = lives.get(0);
                setMap(map,"weatherNow",liveDto.getWeather(),"#33A1C9");//现在天气
                setMap(map,"temperatureNow",liveDto.getTemperature(),"#33A1C9");//现在温度
                setMap(map,"windNow",liveDto.getWinddirection(),"#33A1C9");//现在风向
                setMap(map,"powerNow",liveDto.getProvince(),"#33A1C9");//现在风力
                setMap(map,"humidityNow",liveDto.getHumidity(),"#33A1C9");//现在湿度
                setMap(map,"date",liveDto.getReporttime(),"#33A1C9");//现在时间
            }
        }
    }
    private void setWeatherDefault(HashMap<String, DataInfo> map){
        for(int i=0; i<5; i++){
            String ap = "";
            if(i>0){
                ap = i+"";
            }
            setMap(map,"weatherDay"+ap,"未知","#33A1C9");//白天天气
            setMap(map,"temperatureDay"+ap,"未知","#33A1C9");//白天温度
            setMap(map,"weatherNight"+ap,"未知","#33A1C9");//晚上天气
            setMap(map,"temperatureNight"+ap,"未知","#33A1C9");//晚上温度
            setMap(map,"windDay"+ap,"未知","#33A1C9");//白天风向
            setMap(map,"windNight"+ap,"未知","#33A1C9");//晚上风向
            setMap(map,"powerDay"+ap,"未知","#33A1C9");//白天风力
            setMap(map,"windNight"+ap,"未知" ,"#33A1C9");//晚上风力
            setMap(map,"date"+ap,"未知","#33A1C9");//日期
            setMap(map,"week"+ap,"未知" ,"#33A1C9");//星期几
        }
        setMap(map,"weatherNow","未知","#33A1C9");//现在天气
        setMap(map,"temperatureNow","未知","#33A1C9");//现在温度
        setMap(map,"windNow","未知","#33A1C9");//现在风向
        setMap(map,"powerNow","未知","#33A1C9");//现在风力
        setMap(map,"humidityNow","未知","#33A1C9");//现在湿度
        setMap(map,"date","未知","#33A1C9");//现在时间
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
