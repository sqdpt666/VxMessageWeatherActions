package com.pt.vx;


import cn.hutool.core.collection.CollectionUtil;
import com.pt.vx.config.AllConfig;
import com.pt.vx.domain.User;
import com.pt.vx.service.MessageService;

import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;


public class  VxMessageMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        MessageService service=new MessageService();
        List<User> userList =   AllConfig.userList;
        if(CollectionUtil.isNotEmpty(userList)){
            for(User user : userList){
                service.sendMessage(user);
                Thread.sleep(2333);
            }
        }

    }
}
