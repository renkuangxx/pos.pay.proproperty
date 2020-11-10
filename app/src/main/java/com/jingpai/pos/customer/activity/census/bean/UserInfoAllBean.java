package com.jingpai.pos.customer.activity.census.bean;


import java.util.List;

/**
 * @author 86173
 */
public class UserInfoAllBean {

    /**
     * data : {"censusName":"","completeness":0,"createTime":"","gender":"","idPassportNo":"","identity":"","name":"","population":{"children":[{"birthDate":"","gender":""}],"communityId":0,"companyName":"","country":"","createTime":"","currentResidentialAddress":"","delState":true,"detailedAddress":"","educationLevel":"","employmentStatus":"","frontPhoto":"","gender":"","id":0,"idPassportNo":"","joinPartyDate":"","lastMenstrualDate":"","locationOrgRelationship":"","maritalDate":"","maritalStatus":"","modifyTime":"","name":"","national":"","oligogenicsStatus":"","operatorId":"","oversea":"","permanentResidenceAddress":"","phone":"","politicalLandscape":"","populationCensusId":"","pregnancyStatus":"","propertyArea":"","relationshipHouseOwner":"","remark":"","reversePhoto":"","singletonCardNo":"","singletonStatus":"","soldier":""}}
     * image :
     * text :
     */

    public List<DataBean> data;
    public String image;
    public String text;



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
