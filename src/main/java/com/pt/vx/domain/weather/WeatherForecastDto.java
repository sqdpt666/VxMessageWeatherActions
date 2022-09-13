package com.pt.vx.domain.weather;

import java.util.List;


public class WeatherForecastDto {

    //城市名称
    private String city;
    //城市编码
    private String adcode;
    //省份名称
    private String province;
    //预报发布时间
    private String reporttime;
    //预报数据list结构，元素cast,按顺序为当天、第二天、第三天的预报数据
    private List<Cast> casts;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }
}
