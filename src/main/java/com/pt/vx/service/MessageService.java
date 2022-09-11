package com.pt.vx.service;


import cn.hutool.json.JSONUtil;
import com.pt.vx.domain.DataInfo;
import com.pt.vx.domain.VxMessageDto;
import com.pt.vx.utils.DateUtil;
import com.pt.vx.utils.VxUtil;

import java.util.HashMap;

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
     *
     * 	最后，开心每一天！
     */
    public void sendMessage(){
        VxMessageDto dto = new VxMessageDto();
        dto.setTemplate_id("修改成你的模板ID");  //修改成你的模板ID
        dto.setTouser("修改成你的用户ID"); //修改成你的用户ID
        HashMap<String, DataInfo> map = new HashMap<>();
        setMap(map,"userName","改成她的名字","#FFCCCC"); //改成她的名字
        setAndSend(dto,map);
    }

    public void sendMessage(String id,String uid,String name){
        VxMessageDto dto = new VxMessageDto();
        dto.setTemplate_id(id);
        dto.setTouser(uid);
        HashMap<String, DataInfo> map = new HashMap<>();
        setMap(map,"userName",name,"#FFCCCC");
        setAndSend(dto,map);
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
