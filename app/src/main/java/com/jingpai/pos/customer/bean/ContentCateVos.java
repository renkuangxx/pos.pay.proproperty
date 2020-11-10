package com.jingpai.pos.customer.bean;

import java.util.Date;
import java.util.List;

public class ContentCateVos {
    private Integer id;

    private String name;

    private String cateImages;

    private String cateTitle;

    private Integer sort;

    private Boolean status;

    private Date createTime;

    ContentLikeCollectPageInfo contentLikeCollectPageInfo;

    private List<ContentCateVos> contentCateVos;

    public String getCateTitle() {
        return cateTitle;
    }

    public void setCateTitle(String cateTitle) {
        this.cateTitle = cateTitle;
    }

    public String getCateImages() {
        return cateImages;
    }

    public void setCateImages(String cateImages) {
        this.cateImages = cateImages;
    }

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ContentLikeCollectPageInfo getContentLikeCollectPageInfo() {
        return contentLikeCollectPageInfo;
    }

    public void setContentLikeCollectPageInfo(ContentLikeCollectPageInfo contentLikeCollectPageInfo) {
        this.contentLikeCollectPageInfo = contentLikeCollectPageInfo;
    }

    public List<ContentCateVos> getContentCateVos() {
        return contentCateVos;
    }

    public void setContentCateVos(List<ContentCateVos> contentCateVos) {
        this.contentCateVos = contentCateVos;
    }
}
