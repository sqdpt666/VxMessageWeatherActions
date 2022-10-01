package com.pt.vx.domain;

public class FunctionConfig {

    private boolean open = false;

    private String color = "#FFFCCC";

    public FunctionConfig() {
    }

    public FunctionConfig(boolean open, String color) {
        this.open = open;
        this.color = color;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
