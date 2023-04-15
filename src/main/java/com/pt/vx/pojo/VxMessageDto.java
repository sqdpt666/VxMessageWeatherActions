package com.pt.vx.pojo;

import com.pt.vx.pojo.DataInfo;

import java.util.Map;

public class VxMessageDto {

    private String touser;

    private String template_id;

    private String topcolor = "#FF0000";


    private Map<String, DataInfo> data;


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

    public Map<String, DataInfo> getData() {
        return data;
    }

    public void setData(Map<String, DataInfo> data) {
        this.data = data;
    }
}
