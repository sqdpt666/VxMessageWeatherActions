package com.pt.vx;


import com.pt.vx.config.AllConfig;
import com.pt.vx.domain.User;
import com.pt.vx.service.MessageService;

import java.util.List;

public class VxMessageMain {
    public static void main(String[] args) {
        MessageService service=new MessageService();
        List<User> userList =   AllConfig.userList;
        userList.forEach(service::sendMessage);
    }
}
