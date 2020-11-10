package com.jingpai.pos.customer.bean.show;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 时间: 2020/2/22
 * 功能:
 */
public class MatterDetailBean implements Serializable {


    /**
     * assignTime : 2020-02-25T07:54:24.493Z
     * createTime : 2020-02-25T07:54:24.493Z
     * description : string
     * files : ["string"]
     * handlers : [{"createTime":"2020-02-25T07:54:24.493Z","delState":true,"id":"string","image":["string"],"modifyTime":"2020-02-25T07:54:24.493Z","position":"string","reportId":"string","result":"string","state":0,"userId":"string","username":"string","voice":"string"}]
     * id : string
     * managerId : string
     * managerName : string
     * managerPhone : string
     * state : 0
     * type : string
     * user : string
     */

    private String assignTime;//分配时间
    private String comment;//评价
    private String community;//小区
    private String createTime;//创建时间
    private String description;//问题描述
    private List<String> files;//问题图片
    private List<HandlersBean> handlers;//处理人
    private String id;
    private String managerId;//客服管家
    private String managerName;//客服管家名字
    private String managerPhone;//客服管家手机
    private String modifyTime;//编辑时间
    private int star;//星级
    private int state;//报事状态,可用值:CANCEL,TO_HANDLE,ASSIGNED,HANDLING,TO_PASS,TO_EVALUATE,FINISHED
    private String type;//问题类型
    private String user;//报事人
    private int urge;//催办次数


    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<HandlersBean> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<HandlersBean> handlers) {
        this.handlers = handlers;
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

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getUrge() {
        return urge;
    }

    public void setUrge(int urge) {
        this.urge = urge;
    }

    public static class     HandlersBean {
        /**
         * createTime : 2020-02-25T07:54:24.493Z
         * delState : true
         * id : string
         * image : ["string"]
         * modifyTime : 2020-02-25T07:54:24.493Z
         * position : string
         * reportId : string
         * result : string
         * state : 0
         * userId : string
         * username : string
         * voice : string
         */

        private String communityId;//小区id
        private String createTime;
        private boolean delState;//删除状态
        private String id;
        private String modifyTime;
        private ArrayList<String> image;
        private String position;//职位
        private String reportId;//报事id
        private String result;
        private int state;
        private String userId;
        private String handleTime;
        private String username;
        private String userphone;
        private String voice;


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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
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

        public ArrayList<String> getImage() {
            return image;
        }

        public void setImage(ArrayList<String> image) {
            this.image = image;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getUserphone() {
            return userphone;
        }

        public void setUserphone(String userphone) {
            this.userphone = userphone;
        }

        public String getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(String handleTime) {
            this.handleTime = handleTime;
        }
    }
}
