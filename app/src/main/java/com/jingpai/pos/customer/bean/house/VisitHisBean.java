package com.jingpai.pos.customer.bean.house;

import java.io.Serializable;
import java.util.List;

public class VisitHisBean {

    //communityId
    //小区id
    //integer(int64)
    //
    //createTime
    //创建时间
    //string(date-time)
    //
    //delState
    //删除状态
    //boolean
    //
    //enterTime
    //进入时间
    //string
    //
    //house
    //房屋名
    //string
    //
    //houseId
    //房屋id
    //integer(int64)
    //
    //id
    //id
    //integer(int64)
    //
    //leaveTime
    //离开时间
    //string
    //
    //licensePlateNo
    //车牌号
    //string
    //
    //modifyTime
    //修改时间
    //string(date-time)
    //
    //parkingId
    //关联车位id
    //string
    //
    //password
    //门禁密码
    //string
    //
    //qrCode
    //二维码字符
    //string
    //
    //status
    //状态（0:未到访，1:已进场，2:已出场，3:车位待授权）
    //integer(int32)
    //
    //unit
    //单元号
    //string
    //
    //userId
    //用户id
    //string
    //
    //uuid
    //第三方id
    //string
    //
    //visitUseTime
    //拜访时长
    //integer(int32)
    //
    //visitorDate
    //访问时间
    //string
    //
    //visitorIdCard
    //身份证号码
    //string
    //
    //visitorName
    //姓名
    //string
    //
    //visitorPhone
    //手机号
    //string
    //
    //hasMore
    //是否有下一页
    //boolean
    //
    //pageNo
    //页数
    //integer(int32)
    //integer(int32)
    //pageSize
    //条数
    //integer(int32)
    //integer(int32)

    /**
     * after : {}
     * before :
     * data : [{"arrived":true,"build":"","communityId":0,"createTime":"","delState":true,"enterTime":"","house":"","houseId":0,"id":0,"leaveTime":"","licensePlateNo":"","modifyTime":"","parkingId":"","password":"","qrCode":"","status":0,"unit":"","userId":"","uuid":"","visitUseTime":0,"visitorDate":"","visitorIdCard":"","visitorName":"","visitorPhone":""}]
     * hasMore : true
     * pageNo : 0
     * pageSize : 0
     */

    private AfterBean after;
    private String before;
    private boolean hasMore;
    private int pageNo;
    private int pageSize;
    private List<DataBean> data;


    public AfterBean getAfter() {
        return after;
    }

    public void setAfter(AfterBean after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class AfterBean {
    }

    public static class DataBean implements Serializable {

        /**
         * arrived : true
         * build :
         * communityId : 0
         * createTime :
         * delState : true
         * enterTime :
         * house :
         * houseId : 0
         * id : 0
         * leaveTime :
         * licensePlateNo :
         * modifyTime :
         * parkingId :
         * password :
         * qrCode :
         * status : 0
         * unit :
         * userId :
         * uuid :
         * visitUseTime : 0
         * visitorDate :
         * visitorIdCard :
         * visitorName :
         * visitorPhone :
         */


        private boolean arrived;
        private String building="";
        private int communityId;
        private String createTime;
        private boolean delState;
        private String enterTime;
        private String house;
        private int houseId;
        private int id;
        private String leaveTime;
        private String licensePlateNo;
        private String modifyTime;
        private String parkingId;
        private String parkingApplyId;
        private String parkingPlace;
        private String parkingNo;
        private String password;
        private String qrCode;
        //状态（0:未到访，1:已进场，2:已出场，3:车位待授权, 4:已拒绝, 5:已取消, 6:车位授权申请）
        private int finalStatus;
        private String unit;
        private String roomNo;
        private String userId;
        private String uuid;
        private int visitUseTime;
        private String visitorDate;
        private String visitorIdCard;
        private String visitorName;
        private String visitorPhone;
        //申请人手机号（申请别人车位）
        private String applicantPhone;
        //访客登记标识
        private String visitorRegistrationId;
        //是否车位授权人
        private boolean authorizer;
        //访问类型（访客：VISITOR，访车：VISITCAR）
        private String visitType;

        public boolean isArrived() {
            return arrived;
        }

        public void setArrived(boolean arrived) {
            this.arrived = arrived;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean isDelState() {
            return delState;
        }

        public void setDelState(boolean delState) {
            this.delState = delState;
        }

        public String getEnterTime() {
            return enterTime;
        }

        public void setEnterTime(String enterTime) {
            this.enterTime = enterTime;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public int getHouseId() {
            return houseId;
        }

        public void setHouseId(int houseId) {
            this.houseId = houseId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLeaveTime() {
            return leaveTime;
        }

        public void setLeaveTime(String leaveTime) {
            this.leaveTime = leaveTime;
        }

        public String getLicensePlateNo() {
            return licensePlateNo;
        }

        public void setLicensePlateNo(String licensePlateNo) {
            this.licensePlateNo = licensePlateNo;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getParkingId() {
            return parkingId;
        }

        public void setParkingId(String parkingId) {
            this.parkingId = parkingId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public int getFinalStatus() {
            return finalStatus;
        }

        public void setFinalStatus(int finalStatus) {
            this.finalStatus = finalStatus;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getVisitUseTime() {
            return visitUseTime;
        }

        public void setVisitUseTime(int visitUseTime) {
            this.visitUseTime = visitUseTime;
        }

        public String getVisitorDate() {
            return visitorDate;
        }

        public void setVisitorDate(String visitorDate) {
            this.visitorDate = visitorDate;
        }

        public String getVisitorIdCard() {
            return visitorIdCard;
        }

        public void setVisitorIdCard(String visitorIdCard) {
            this.visitorIdCard = visitorIdCard;
        }

        public String getVisitorName() {
            return visitorName;
        }

        public void setVisitorName(String visitorName) {
            this.visitorName = visitorName;
        }

        public String getVisitorPhone() {
            return visitorPhone;
        }

        public void setVisitorPhone(String visitorPhone) {
            this.visitorPhone = visitorPhone;
        }

        public String getParkingPlace() {
            return parkingPlace;
        }

        public void setParkingPlace(String parkingPlace) {
            this.parkingPlace = parkingPlace;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public boolean isAuthorizer() {
            return authorizer;
        }

        public void setAuthorizer(boolean authorizer) {
            this.authorizer = authorizer;
        }

        public String getVisitorRegistrationId() {
            return visitorRegistrationId;
        }

        public void setVisitorRegistrationId(String visitorRegistrationId) {
            this.visitorRegistrationId = visitorRegistrationId;
        }

        public String getParkingApplyId() {
            return parkingApplyId;
        }

        public void setParkingApplyId(String parkingApplyId) {
            this.parkingApplyId = parkingApplyId;
        }

        public String getApplicantPhone() {
            return applicantPhone;
        }

        public void setApplicantPhone(String applicantPhone) {
            this.applicantPhone = applicantPhone;
        }

        public String getParkingNo() {
            return parkingNo;
        }

        public void setParkingNo(String parkingNo) {
            this.parkingNo = parkingNo;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getVisitType() {
            return visitType;
        }

        public void setVisitType(String visitType) {
            this.visitType = visitType;
        }
    }

    public static class VisitState {
        //状态（0:未到访，1:已进场，2:已出场，3:车位待授权, 4:已拒绝, 5:已取消, 6:车位授权申请）
        public static final int STATE_NO_COME = 0;
        public static final int STATE_HAS_IN = 1;
        public static final int STATE_HAS_OUT = 2;
        public static final int STATE_AWAIT_AUTH = 3;
        public static final int STATE_HAS_REFUSE = 4;
        public static final int STATE_HAS_CANCEL = 5;
        public static final int STATE_CAR_APPLY = 6;
    }
}
