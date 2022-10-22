package com.pt.vx.domain.hefeng;

import java.util.List;

public class LocationResult {

    String code;
    List<CityLocation> location;

    public List<CityLocation> getLocation() {
        return location;
    }

    public void setLocation(List<CityLocation> location) {
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
