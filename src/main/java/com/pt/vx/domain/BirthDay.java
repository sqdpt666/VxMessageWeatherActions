package com.pt.vx.domain;

public class BirthDay {

    private int year;
    private int month;
    private int day;
    private boolean isChinese;

    public BirthDay(){

    }

    public BirthDay(int year, int month, int day, boolean isChinese) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.isChinese = isChinese;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isChinese() {
        return isChinese;
    }

    public void setChinese(boolean chinese) {
        isChinese = chinese;
    }
}
