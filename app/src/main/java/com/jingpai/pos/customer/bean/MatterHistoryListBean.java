package com.jingpai.pos.customer.bean;

import java.util.List;

public class MatterHistoryListBean {

    /**
     * after : {}
     * before :
     * data : [{"applyTime":"","auditId":"","auditState":0,"auditStateName":"","layout":"","name":"","phone":""}]
     * hasMore : true
     * pageNo : 0
     * pageSize : 0
     */

    private String before;
    private boolean hasMore;
    private int pageNo;
    private int pageSize;
    private List<MatterHistoryBean> data;

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

    public List<MatterHistoryBean> getData() {
        return data;
    }

    public void setData(List<MatterHistoryBean> data) {
        this.data = data;
    }

    public static class MatterHistoryBean {
        private String id;
        private String assignTime;//分配时间
        private String clientType;//发起客户端
        private String comment;//评价
        private String community;//小区
        private String createTime;//创建时间
        private String description;//问题描述
        private String files;//问题图片
        private HandlerBean handlers;//处理人
        private String managerId;//客服管家
        private String managerName;//客服管家名称
        private String managerPhone;//客服管家手机
        private String modifyTime;//编辑时间
        private String star;//星级
        private int state;//报事状态,可用值:CANCEL,TO_HANDLE,ASSIGNED,HANDLING,TO_PASS,TO_EVALUATE,FINISHED
        private String type;//问题类型
        private String urge;//催办次数
        private String user;//报事人

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAssignTime() {
            return assignTime;
        }

        public void setAssignTime(String assignTime) {
            this.assignTime = assignTime;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFiles() {
            return files;
        }

        public void setFiles(String files) {
            this.files = files;
        }

        public HandlerBean getHandlers() {
            return handlers;
        }

        public void setHandlers(HandlerBean handlers) {
            this.handlers = handlers;
        }

        public String getManagerId() {
            return managerId;
        }

        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getManagerPhone() {
            return managerPhone;
        }

        public void setManagerPhone(String managerPhone) {
            this.managerPhone = managerPhone;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrge() {
            return urge;
        }

        public void setUrge(String urge) {
            this.urge = urge;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

    public static class HandlerBean {
        private String id;
        private String communityId;//小区id
        private String createTime;//创建时间
        private int delState;//删除状态
        private String image;//处理结果图片
        private String modifyTime;//编辑时间
        private String position;//职位
        private String reportId;//报事id
        private String result;//结果
        private String state;//状态,可用值:CANCEL,TO_HANDLE,ASSIGNED,HANDLING,TO_PASS,TO_EVALUATE,FINISHED
        private String userId;//用户id
        private String username;//用户名
        private String voice;//	处理结果语音

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDelState() {
            return delState;
        }

        public void setDelState(int delState) {
            this.delState = delState;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getReportId() {
            return reportId;
        }

        public void setReportId(String reportId) {
            this.reportId = reportId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getVoice() {
            return voice;
        }

        public void setVoice(String voice) {
            this.voice = voice;
        }
    }
}
