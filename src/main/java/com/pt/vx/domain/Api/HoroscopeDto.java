package com.pt.vx.domain.Api;

/**
 * @Author: PengTao
 * @Date: 2022/9/22 11:13
 **/
public class HoroscopeDto {

    private String title;
    private String time;
    private String luckycolor;
    private String luckynumber;
    private String shortcomment;
    private String luckyconstellation;
    private Fortunetext fortunetext;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLuckycolor() {
        return luckycolor;
    }

    public void setLuckycolor(String luckycolor) {
        this.luckycolor = luckycolor;
    }

    public String getLuckynumber() {
        return luckynumber;
    }

    public void setLuckynumber(String luckynumber) {
        this.luckynumber = luckynumber;
    }

    public String getShortcomment() {
        return shortcomment;
    }

    public void setShortcomment(String shortcomment) {
        this.shortcomment = shortcomment;
    }

    public String getLuckyconstellation() {
        return luckyconstellation;
    }

    public void setLuckyconstellation(String luckyconstellation) {
        this.luckyconstellation = luckyconstellation;
    }

    public Fortunetext getFortunetext() {
        return fortunetext;
    }

    public void setFortunetext(Fortunetext fortunetext) {
        this.fortunetext = fortunetext;
    }
}
