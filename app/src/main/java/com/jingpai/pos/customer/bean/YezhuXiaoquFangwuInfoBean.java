package com.jingpai.pos.customer.bean;

import java.util.List;

public class YezhuXiaoquFangwuInfoBean {
    public List<XiaoquFangwuInfoBean> getXiaoquFangwu() {
        return xiaoquFangwu;
    }

    public void setXiaoquFangwu(List<XiaoquFangwuInfoBean> xiaoquFangwu) {
        this.xiaoquFangwu = xiaoquFangwu;
    }

    List<XiaoquFangwuInfoBean> xiaoquFangwu;    //获取小区fangwu列表
}
