package com.jingpai.pos.customer.bean;

import java.util.List;

public class ContentCateListBean {



    /**
     * contentCateVos : [null]
     * contentLikeCollectPageInfo : {"endRow":0,"hasNextPage":true,"hasPreviousPage":true,"isFirstPage":true,"isLastPage":true,"list":[{"areaCode":"string","avatar":"string","body":"string","budget":"string","building":"string","collectCount":0,"contentCateId":0,"cover":"string","createTime":"2020-06-28T06:01:24.259Z","houseLayout":"string","houseNumber":"string","id":0,"industryId":0,"initClct":0,"initRead":0,"initThumb":0,"issueTime":"2020-06-28T06:01:24.259Z","like":true,"likeCount":0,"link":"string","merchantId":"string","merchantName":"string","nickName":"string","operUser":0,"operUserName":"string","pattern":"string","quartersCode":"string","remark":"string","renovationCondition":"string","sort":0,"squareCost":0,"state":0,"status":true,"style":"string","summary":"string","title":"string","towards":"string","updateTime":"2020-06-28T06:01:24.259Z"}],"navigateFirstPage":0,"navigateLastPage":0,"navigatePages":0,"navigatepageNums":[0],"nextPage":0,"pageNum":0,"pageSize":0,"pages":0,"prePage":0,"size":0,"startRow":0,"total":0}
     * createTime : 2020-06-28T06:01:24.259Z
     * id : 0
     * name : string
     * sort : 0
     * status : true
     */

    private ContentLikeCollectPageInfo contentLikeCollectPageInfo;
    private String createTime;
    private int id;
    private String name;
    private int sort;
    private boolean status;
    private List<ContentCateVos> contentCateVos;

    public List<ContentCateVos> getContentCateVos() {
        return contentCateVos;
    }

    public void setContentCateVos(List<ContentCateVos> contentCateVos) {
        this.contentCateVos = contentCateVos;
    }

    public ContentLikeCollectPageInfo getContentLikeCollectPageInfo() {
        return contentLikeCollectPageInfo;
    }

    public void setContentLikeCollectPageInfo(ContentLikeCollectPageInfo contentLikeCollectPageInfo) {
        this.contentLikeCollectPageInfo = contentLikeCollectPageInfo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
