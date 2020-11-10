package com.jingpai.pos.customer.activity.census.bean;

import java.util.List;

public class PopulationBean {
    /**
     * children : [{"birthDate":"","gender":""}]
     * communityId : 0
     * companyName :
     * country :
     * createTime :
     * currentResidentialAddress :
     * delState : true
     * detailedAddress :
     * educationLevel :
     * employmentStatus :
     * frontPhoto :
     * gender :
     * id : 0
     * idPassportNo :
     * joinPartyDate :
     * lastMenstrualDate :
     * locationOrgRelationship :
     * maritalDate :
     * maritalStatus :
     * modifyTime :
     * name :
     * national :
     * oligogenicsStatus :
     * operatorId :
     * oversea :
     * permanentResidenceAddress :
     * phone :
     * politicalLandscape :
     * populationCensusId :
     * pregnancyStatus :
     * propertyArea :
     * relationshipHouseOwner :
     * remark :
     * reversePhoto :
     * singletonCardNo :
     * singletonStatus :
     * soldier :
     */

    public int communityId;
    public String companyName;
    public String country;
    public String createTime;
    public String currentResidentialAddress;
    public boolean delState;
    public String detailedAddress;
    public String educationLevel;
    public String employmentStatus;
    public String frontPhoto;
    public String gender;
    public int id;
    public String idPassportNo;
    public String joinPartyDate;
    public String lastMenstrualDate;
    public String locationOrgRelationship;
    public String maritalDate;
    public String maritalStatus;
    public String modifyTime;
    public String name;
    public String national;
    public String oligogenicsStatus;
    public String operatorId;
    public String oversea;
    public String permanentResidenceAddress;
    public String phone;
    public String politicalLandscape;
    public String populationCensusId;
    public String pregnancyStatus;
    public String propertyArea;
    public String relationshipHouseOwner;
    public String remark;
    public String reversePhoto;
    public String singletonCardNo;
    public String singletonStatus;
    public String soldier;
    public String isFertility;
    public String isOligogenics;
    public String position;//0为业主

    public String getIsFertility() {
        return isFertility;
    }

    public void setIsFertility(String isFertility) {
        this.isFertility = isFertility;
    }

    public String getIsOligogenics() {
        return isOligogenics;
    }

    public void setIsOligogenics(String isOligogenics) {
        this.isOligogenics = isOligogenics;
    }

    public List<ChildrenBean> children;

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCurrentResidentialAddress() {
        return currentResidentialAddress;
    }

    public void setCurrentResidentialAddress(String currentResidentialAddress) {
        this.currentResidentialAddress = currentResidentialAddress;
    }

    public boolean isDelState() {
        return delState;
    }

    public void setDelState(boolean delState) {
        this.delState = delState;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdPassportNo() {
        return idPassportNo;
    }

    public void setIdPassportNo(String idPassportNo) {
        this.idPassportNo = idPassportNo;
    }

    public String getJoinPartyDate() {
        return joinPartyDate;
    }

    public void setJoinPartyDate(String joinPartyDate) {
        this.joinPartyDate = joinPartyDate;
    }

    public String getLastMenstrualDate() {
        return lastMenstrualDate;
    }

    public void setLastMenstrualDate(String lastMenstrualDate) {
        this.lastMenstrualDate = lastMenstrualDate;
    }

    public String getLocationOrgRelationship() {
        return locationOrgRelationship;
    }

    public void setLocationOrgRelationship(String locationOrgRelationship) {
        this.locationOrgRelationship = locationOrgRelationship;
    }

    public String getMaritalDate() {
        return maritalDate;
    }

    public void setMaritalDate(String maritalDate) {
        this.maritalDate = maritalDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getOligogenicsStatus() {
        return oligogenicsStatus;
    }

    public void setOligogenicsStatus(String oligogenicsStatus) {
        this.oligogenicsStatus = oligogenicsStatus;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOversea() {
        return oversea;
    }

    public void setOversea(String oversea) {
        this.oversea = oversea;
    }

    public String getPermanentResidenceAddress() {
        return permanentResidenceAddress;
    }

    public void setPermanentResidenceAddress(String permanentResidenceAddress) {
        this.permanentResidenceAddress = permanentResidenceAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPoliticalLandscape() {
        return politicalLandscape;
    }

    public void setPoliticalLandscape(String politicalLandscape) {
        this.politicalLandscape = politicalLandscape;
    }

    public String getPopulationCensusId() {
        return populationCensusId;
    }

    public void setPopulationCensusId(String populationCensusId) {
        this.populationCensusId = populationCensusId;
    }

    public String getPregnancyStatus() {
        return pregnancyStatus;
    }

    public void setPregnancyStatus(String pregnancyStatus) {
        this.pregnancyStatus = pregnancyStatus;
    }

    public String getPropertyArea() {
        return propertyArea;
    }

    public void setPropertyArea(String propertyArea) {
        this.propertyArea = propertyArea;
    }

    public String getRelationshipHouseOwner() {
        return relationshipHouseOwner;
    }

    public void setRelationshipHouseOwner(String relationshipHouseOwner) {
        this.relationshipHouseOwner = relationshipHouseOwner;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReversePhoto() {
        return reversePhoto;
    }

    public void setReversePhoto(String reversePhoto) {
        this.reversePhoto = reversePhoto;
    }

    public String getSingletonCardNo() {
        return singletonCardNo;
    }

    public void setSingletonCardNo(String singletonCardNo) {
        this.singletonCardNo = singletonCardNo;
    }

    public String getSingletonStatus() {
        return singletonStatus;
    }

    public void setSingletonStatus(String singletonStatus) {
        this.singletonStatus = singletonStatus;
    }

    public String getSoldier() {
        return soldier;
    }

    public void setSoldier(String soldier) {
        this.soldier = soldier;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
