package com.pt.vx.domain.Api;

import java.util.List;

/**
 * @Author: PengTao
 * @Date: 2022/9/22 10:42
 **/
public class WorldRead60s {

    private String name;
    private List<String> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
