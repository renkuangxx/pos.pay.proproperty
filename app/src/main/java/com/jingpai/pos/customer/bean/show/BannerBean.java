package com.jingpai.pos.customer.bean.show;

import com.stx.xhb.androidx.entity.SimpleBannerInfo;

public class BannerBean extends SimpleBannerInfo {

    private Integer id;

    private String name;

    private Integer type;

    private String imageUrl;

    private String link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    @Override
    public Object getXBannerUrl() {
        return imageUrl;
    }
}
