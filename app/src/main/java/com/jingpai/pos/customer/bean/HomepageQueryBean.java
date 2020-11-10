package com.jingpai.pos.customer.bean;


import java.util.List;

//首页接口
public class HomepageQueryBean {

    public List<String> carousels;
    public List<ContentCateListBean> contentCateList;

    public List<String> getCarousels() {
        return carousels;
    }

    public void setCarousels(List<String> carousels) {
        this.carousels = carousels;
    }

    public List<ContentCateListBean> getContentCateList() {
        return contentCateList;
    }

    public void setContentCateList(List<ContentCateListBean> contentCateList) {
        this.contentCateList = contentCateList;
    }

}
