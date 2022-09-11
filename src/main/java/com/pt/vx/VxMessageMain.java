package com.pt.vx;


import com.pt.vx.service.MessageService;

public class VxMessageMain {
    public static void main(String[] args) {
        MessageService service=new MessageService();
        service.sendMessage();
    }
}
