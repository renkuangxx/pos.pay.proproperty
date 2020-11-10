package com.jingpai.pos.customer.bean.login;

/*
 * function:
 */
public class PersonInformationBean {

    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : {"username":"张总1","avatar":"http://valuation.oss-cn-shanghai.aliyuncs.com/property-file/20200219/0052baf880db4b2f8a58fd5d2e652090.jpg","phone":"18551695587","sex":2}
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
         * username : 张总1
         * avatar : http://valuation.oss-cn-shanghai.aliyuncs.com/property-file/20200219/0052baf880db4b2f8a58fd5d2e652090.jpg
         * phone : 18551695587
         * sex : 2
         */

        private String username;
        private String avatar;
        private String phone;
        private int sex;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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
    }
}
