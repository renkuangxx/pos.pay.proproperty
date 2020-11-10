package com.jingpai.pos.customer.bean.show;

import java.util.List;

public class PromotionBean {

    private String enrollEndTime;

    private String enrollStartTime;

    private String promotionStartTime;

    private String promotionEndTime;

    private String promotionStatusName;

    private String promotionId;

    private String labels;

    private int promotionStatus;
    private int promotionType;

    private List<String> labelList;

    private String promotionName;

    private String price;

    private String promotionPic;
    private String promotionCommuPic;

    private int enrollCount;

    //新增图片URL字段
    private String promotionEndIconUrl;

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public String getPromotionEndIconUrl() {
        return promotionEndIconUrl;
    }

    public void setPromotionEndIconUrl(String promotionEndIconUrl) {
        this.promotionEndIconUrl = promotionEndIconUrl;
    }

    public String getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(String enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public String getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(String enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public String getPromotionStartTime() {
        return promotionStartTime;
    }

    public void setPromotionStartTime(String promotionStartTime) {
        this.promotionStartTime = promotionStartTime;
    }

    public String getPromotionEndTime() {
        return promotionEndTime;
    }

    public void setPromotionEndTime(String promotionEndTime) {
        this.promotionEndTime = promotionEndTime;
    }

    public String getPromotionStatusName() {
        return promotionStatusName;
    }

    public void setPromotionStatusName(String promotionStatusName) {
        this.promotionStatusName = promotionStatusName;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public int getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(int promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public String getPromotionName() {
        return promotionName==null?"":promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPromotionPic() {
        return promotionPic;
    }

    public void setPromotionPic(String promotionPic) {
        this.promotionPic = promotionPic;
    }

    public int getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(int enrollCount) {
        this.enrollCount = enrollCount;
    }

    public String getPromotionCommuPic() {
        return promotionCommuPic;
    }

    public void setPromotionCommuPic(String promotionCommuPic) {
        this.promotionCommuPic = promotionCommuPic;
    }
}
