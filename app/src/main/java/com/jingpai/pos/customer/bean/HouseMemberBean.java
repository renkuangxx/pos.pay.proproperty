package com.jingpai.pos.customer.bean;

/**
 * 时间: 2020/3/1
 * 功能:
 */
public class HouseMemberBean {


    /**
     * avatar : string
     * createTime : string
     * faceId : string
     * houseId : string
     * idCard : string
     * memberRelationshipId : string
     * name : string
     * owner : true
     * phone : string
     * type : string
     * typeName : string
     * userId : string
     */

    private String avatar;
    private String createTime;
    private String faceId;
    private String houseId;
    private String idCard;
    private String memberRelationshipId;
    private String name;
    private boolean owner;
    private String phone;
    private String type;
    private String typeName;
    private String userId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMemberRelationshipId() {
        return memberRelationshipId;
    }

    public void setMemberRelationshipId(String memberRelationshipId) {
        this.memberRelationshipId = memberRelationshipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
