package com.pt.vx.service;


import cn.hutool.json.JSONUtil;
import com.pt.vx.domain.BirthDay;
import com.pt.vx.domain.DataInfo;
import com.pt.vx.domain.User;
import com.pt.vx.domain.VxMessageDto;
import com.pt.vx.domain.weather.Cast;
import com.pt.vx.domain.weather.WeatherForecastDto;
import com.pt.vx.domain.weather.WeatherResponseDto;
import com.pt.vx.utils.DateUtil;
import com.pt.vx.utils.VxUtil;
import com.pt.vx.utils.WeatherUtil;

import java.time.LocalDate;
import java.util.Date;
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
    public void sendMessage(User user){
        VxMessageDto dto = new VxMessageDto();
        dto.setTemplate_id(user.getTemplateId());
        dto.setTouser(user.getVx());
        HashMap<String, DataInfo> map = new HashMap<>();
        setMap(map,"userName",user.getUserName(),"#FFCCCC"); 
        setWeather(map,user.getAddress(), user.getCity(), WeatherUtil.TYPE_ALL); 
        setMap(map,"holdDay", DateUtil.passDayOfNow(user.getLoveDay()),"#FFCCCC"); 
        setMap(map,"yourBirthDay",getBirthDay(user),"#FFCCCC"); 
        setMap(map,"myBirthDay", getBirthDay(user),"#FFCCCC"); 
        int monthValue = user.getLoveDay().getMonthValue();
        int dayOfMonth = user.getLoveDay().getDayOfMonth();
        setMap(map,"loveDay",DateUtil.getNextBirthDay(monthValue,dayOfMonth),"#FFCCCC"); 
        dto.setData(map);
        String message = JSONUtil.toJsonStr(dto);
        VxUtil.sendMessage(message);
    }

    private String getBirthDay(User user){
        BirthDay birthDay = user.getBirthDay();
        LocalDate of = LocalDate.of(birthDay.getYear(), birthDay.getMonth(), birthDay.getDay());
        return birthDay.isChinese()?
                DateUtil.getNextChineseBirthDay(of) :
                DateUtil.getNextBirthDay(of);
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
