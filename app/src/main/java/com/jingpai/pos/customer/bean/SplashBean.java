package com.jingpai.pos.customer.bean;

import java.io.Serializable;

/**
 * Created By：jinheng.liu
 * on 2020/9/11
 */
public class SplashBean implements Serializable {
    private int flashScreenTime;
    private String imageUrl;
    private String link;
    private String name;

    public int getFlashScreenTime() {
        return flashScreenTime;
    }

    public void setFlashScreenTime(int flashScreenTime) {
        this.flashScreenTime = flashScreenTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
