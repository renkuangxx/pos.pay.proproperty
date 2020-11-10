package com.jingpai.pos.customer.bean;

import java.io.Serializable;

public class CommunityFunnyBean implements Serializable {
    private String icon;
    private int localIcon;
    private String title;
    private String type;
    private String url;
    private boolean usable;
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLocalIcon() {
        return localIcon;
    }

    public void setLocalIcon(int localIcon) {
        this.localIcon = localIcon;
    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }
}
