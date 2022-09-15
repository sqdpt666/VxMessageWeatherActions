package com.pt.vx.domain;

import java.time.LocalDate;

public class User {

    private String Vx;
    private String userName;

    private BirthDay birthDay;

    private LocalDate loveDay;

    private BirthDay careDay;

    private String address;

    private String city;

   private String templateId;




    public String getVx() {
        return Vx;
    }

    public void setVx(String vx) {
        Vx = vx;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BirthDay getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(BirthDay birthDay) {
        this.birthDay = birthDay;
    }

    public BirthDay getCareDay() {
        return careDay;
    }

    public void setCareDay(BirthDay careDay) {
        this.careDay = careDay;
    }

    public LocalDate getLoveDay() {
        return loveDay;
    }

    public void setLoveDay(LocalDate loveDay) {
        this.loveDay = loveDay;
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

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

}
