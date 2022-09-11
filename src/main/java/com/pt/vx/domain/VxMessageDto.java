package com.pt.vx.domain;

import java.util.HashMap;

public class VxMessageDto {

    private String touser;

    private String template_id;

    private String topcolor = "#FF0000";


    private HashMap<String,DataInfo> data;


    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public HashMap<String, DataInfo> getData() {
        return data;
    }

    public void setData(HashMap<String, DataInfo> data) {
        this.data = data;
    }
}
