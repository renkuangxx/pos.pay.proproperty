package com.jingpai.pos.customer.bean;

/**
 * 时间: 2020/3/8
 * 功能:
 */
public class ReturnBean {

    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : {"id":"1236605652583464961","fileType":"IMAGE","filePath":"http://doorway.oss-cn-hangzhou.aliyuncs.com/property-file/20200308/181959f3ca964c1d9a6fcf8f36ffa635.png"}
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
         * id : 1236605652583464961
         * fileType : IMAGE
         * filePath : http://doorway.oss-cn-hangzhou.aliyuncs.com/property-file/20200308/181959f3ca964c1d9a6fcf8f36ffa635.png
         */

        private String id;
        private String fileType;
        private String filePath;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }
}
