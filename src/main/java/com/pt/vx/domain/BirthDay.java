package com.pt.vx.domain;

public class BirthDay {

    private int year;
    private int month;
    private int day;

    private boolean chineseFlag;

    private boolean countFlag;

    public BirthDay(){

    }

    public BirthDay(int year, int month, int day, boolean chineseFlag, boolean countFlag) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.chineseFlag = chineseFlag;
        this.countFlag = countFlag;
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

    public boolean isChineseFlag() {
        return chineseFlag;
    }

    public void setChineseFlag(boolean chineseFlag) {
        this.chineseFlag = chineseFlag;
    }

    public boolean isCountFlag() {
        return countFlag;
    }

    public void setCountFlag(boolean countFlag) {
        this.countFlag = countFlag;
    }
}
