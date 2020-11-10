package com.jingpai.pos.customer.custom;

public class KeyValueBean {
    private String key;
    private String value;
    private int drawableLeftId=-1;

    public KeyValueBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValueBean(String key, String value, int drawableLeftId) {
        this.key = key;
        this.value = value;
        this.drawableLeftId = drawableLeftId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDrawableLeftId() {
        return drawableLeftId;
    }

    public void setDrawableLeftId(int drawableLeftId) {
        this.drawableLeftId = drawableLeftId;
    }
}
