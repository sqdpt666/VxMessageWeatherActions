package com.pt.vx.domain.weather;

import java.util.List;


public class CodeResponseDto {

    private Integer status;
    private Integer count;
    private String info;
    private List<Code> geocodes;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Code> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(List<Code> geocodes) {
        this.geocodes = geocodes;
    }
}
