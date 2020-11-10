package com.jingpai.pos.customer.bean;

import java.io.Serializable;

public class LivingBean implements Serializable{
//    @ApiModelProperty("内容编号")
    private String id;

//    @ApiModelProperty("内容标题")
    private String title;

//    @ApiModelProperty("内容封面")
    private String cover;

//    @ApiModelProperty("链接")
    private String link;

//    @ApiModelProperty("H5内容访问地址")
    private String H5Url;

//    @ApiModelProperty("直播开始时间")
    private String liveStartTime;

//    @ApiModelProperty("直播结束时间")
    private String liveEndTime;

//    @ApiModelProperty("排序")
    private Integer sortNum;
//    @ApiModelProperty("视频状态（1未开始，2进行中，3结束）")
    private int liveStatus;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getH5Url() {
        return H5Url;
    }

    public void setH5Url(String h5Url) {
        H5Url = h5Url;
    }

    public String getLiveStartTime() {
        return liveStartTime;
    }

    public void setLiveStartTime(String liveStartTime) {
        this.liveStartTime = liveStartTime;
    }

    public String getLiveEndTime() {
        return liveEndTime;
    }

    public void setLiveEndTime(String liveEndTime) {
        this.liveEndTime = liveEndTime;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }
}


