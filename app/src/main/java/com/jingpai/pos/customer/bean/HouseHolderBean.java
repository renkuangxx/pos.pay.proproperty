package com.jingpai.pos.customer.bean;

import java.io.Serializable;

/**
 * @author 86173
 */
public class HouseHolderBean implements Serializable {

    /**
     * applyReason :
     * applyTime :
     * auditId :
     * auditResult :
     * certificateType :
     * idCard :
     * identity :
     * layout :
     * memberName :
     * memberPhone :
     * name :
     * remark :
     */


    private String auditId;
    private String roomNo;
    private String houseId;
    private String memberName;
    private String memberPhone;
    private String certificateType;
    private String idCard;
    private String identity;
    private String name;
    private String layout;
    private String applyTime;
    private String applyReason;
    private int auditResult;
    private String auditResultName;
    private boolean hasAuth;
    private boolean isApplyUser;
    private String managerAuditResult;
    private String managerName;
    private String managerAuditTime;
    private String ownerAuditResult;
    private String ownerAuditTime;
    private String rejectReason;


    private String changeType;
    private String idCardNegative;  //反面
    private String idCardPositive;  //正面
    private String leaseContract;
    private String leaseEndTime;
    private String leaseStartTime;
    private String gender;
    private String memberType;
    private String managerRejectReason;


    public String getManagerRejectReason() {
        return managerRejectReason;
    }

    public void setManagerRejectReason(String managerRejectReason) {
        this.managerRejectReason = managerRejectReason;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getIdCardNegative() {
        return idCardNegative;
    }

    public void setIdCardNegative(String idCardNegative) {
        this.idCardNegative = idCardNegative;
    }

    public String getIdCardPositive() {
        return idCardPositive;
    }

    public void setIdCardPositive(String idCardPositive) {
        this.idCardPositive = idCardPositive;
    }

    public String getLeaseContract() {
        return leaseContract;
    }

    public void setLeaseContract(String leaseContract) {
        this.leaseContract = leaseContract;
    }

    public String getLeaseEndTime() {
        return leaseEndTime;
    }

    public void setLeaseEndTime(String leaseEndTime) {
        this.leaseEndTime = leaseEndTime;
    }

    public String getLeaseStartTime() {
        return leaseStartTime;
    }

    public void setLeaseStartTime(String leaseStartTime) {
        this.leaseStartTime = leaseStartTime;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public int getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(int auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditResultName() {
        return auditResultName;
    }

    public void setAuditResultName(String auditResultName) {
        this.auditResultName = auditResultName;
    }

    public boolean isHasAuth() {
        return hasAuth;
    }

    public void setHasAuth(boolean hasAuth) {
        this.hasAuth = hasAuth;
    }

    public boolean isApplyUser() {
        return isApplyUser;
    }

    public void setApplyUser(boolean applyUser) {
        isApplyUser = applyUser;
    }

    public String getManagerAuditResult() {
        return managerAuditResult;
    }

    public void setManagerAuditResult(String managerAuditResult) {
        this.managerAuditResult = managerAuditResult;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerAuditTime() {
        return managerAuditTime;
    }

    public void setManagerAuditTime(String managerAuditTime) {
        this.managerAuditTime = managerAuditTime;
    }

    public String getOwnerAuditResult() {
        return ownerAuditResult;
    }

    public void setOwnerAuditResult(String ownerAuditResult) {
        this.ownerAuditResult = ownerAuditResult;
    }

    public String getOwnerAuditTime() {
        return ownerAuditTime;
    }

    public void setOwnerAuditTime(String ownerAuditTime) {
        this.ownerAuditTime = ownerAuditTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
