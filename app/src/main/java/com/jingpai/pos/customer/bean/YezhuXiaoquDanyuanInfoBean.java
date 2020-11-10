package com.jingpai.pos.customer.bean;

import java.util.List;

public class YezhuXiaoquDanyuanInfoBean {
    public List<XiaoquDanyuanInfoBean> getXiaoquDanyuan() {
        return xiaoquDanyuan;
    }

    public void setXiaoquDanyuan(List<XiaoquDanyuanInfoBean> xiaoquDanyuan) {
        this.xiaoquDanyuan = xiaoquDanyuan;
    }

    List<XiaoquDanyuanInfoBean> xiaoquDanyuan;  //获取小区单元列表
}
