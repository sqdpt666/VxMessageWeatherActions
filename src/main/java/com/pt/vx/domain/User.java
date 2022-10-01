package com.pt.vx.domain;

import java.time.LocalDate;

public class User {

    private String Vx;
    private String templateId;
    private String userName;
    private BirthDay[] birthDays;
    private String address;
    private String city;

    public String getVx() {
        return Vx;
    }

    public void setVx(String vx) {
        Vx = vx;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BirthDay[] getBirthDays() {
        return birthDays;
    }

    public void setBirthDays(BirthDay[] birthDays) {
        this.birthDays = birthDays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
