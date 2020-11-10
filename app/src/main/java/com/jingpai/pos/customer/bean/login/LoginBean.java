package com.jingpai.pos.customer.bean.login;

import java.util.List;

/*
 * function:
 */
public class LoginBean {

    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : {"id":"1203855047167836160","username":"广告","name":"张文彬","avatar":"http://valuation.oss-cn-shanghai.aliyuncs.com/property-file/20200219/0052baf880db4b2f8a58fd5d2e652090.jpg","phone":"18551695587","sex":2,"faceId":null,"delState":false,"createTime":"2020-01-07 16:36:27","modifyTime":"2020-02-19 19:01:18","communities":[{"id":154,"name":"锦绣山城"},{"id":44,"name":"手抓饼服务集团"}],"token":"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjAzODU1MDQ3MTY3ODM2MTYwIiwiaXNzIjoidXNlcklkIiwiaWF0IjoxNTgyNTMwMDc0LCJzdWIiOiJDT05TVU1FUiIsImV4cCI6MTU5MDMwNjA3NH0.x7HDEwCdKovM0XCN6lCtt751PAVHzOTPcjKIjzAppMo"}
     */

    private boolean success;
    private String returnCode;
    private String returnMsg;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1203855047167836160
         * username : 广告
         * name : 张文彬
         * avatar : http://valuation.oss-cn-shanghai.aliyuncs.com/property-file/20200219/0052baf880db4b2f8a58fd5d2e652090.jpg
         * phone : 18551695587
         * sex : 2
         * faceId : null
         * delState : false
         * createTime : 2020-01-07 16:36:27
         * modifyTime : 2020-02-19 19:01:18
         * communities : [{"id":154,"name":"锦绣山城"},{"id":44,"name":"手抓饼服务集团"}]
         * token : eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjAzODU1MDQ3MTY3ODM2MTYwIiwiaXNzIjoidXNlcklkIiwiaWF0IjoxNTgyNTMwMDc0LCJzdWIiOiJDT05TVU1FUiIsImV4cCI6MTU5MDMwNjA3NH0.x7HDEwCdKovM0XCN6lCtt751PAVHzOTPcjKIjzAppMo
         */

        private String id;
        private String username;
        private String name;
        private String avatar;
        private String phone;
        private int sex;
        private Object faceId;
        private boolean delState;
        private String createTime;
        private String modifyTime;
        private String token;
        private List<CommunitiesBean> communities;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Object getFaceId() {
            return faceId;
        }

        public void setFaceId(Object faceId) {
            this.faceId = faceId;
        }

        public boolean isDelState() {
            return delState;
        }

        public void setDelState(boolean delState) {
            this.delState = delState;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<CommunitiesBean> getCommunities() {
            return communities;
        }

        public void setCommunities(List<CommunitiesBean> communities) {
            this.communities = communities;
        }

        public static class CommunitiesBean {
            /**
             * id : 154
             * name : 锦绣山城
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
