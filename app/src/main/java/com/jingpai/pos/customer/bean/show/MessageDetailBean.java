package com.jingpai.pos.customer.bean.show;

public class MessageDetailBean extends AnnouncementBean.DataBeanX.DataBean {
    /**
     * id : 1
     * title : 测试1
     * content : 测试2
     * delState : false
     * createTime : 2020-02-24 19:52:20
     * modifyTime : 2020-02-24 19:52:20
     * type 推送类型（VOICE:语音消息，CONFIRM:需确认消息，WEBVIEW:打开H5页面，APP:打开原生页面，IMAGE:打开图片
     * url 跳转链接地址
     * image
     */
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
