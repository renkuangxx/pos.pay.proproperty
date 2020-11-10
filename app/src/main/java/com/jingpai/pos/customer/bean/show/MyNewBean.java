package com.jingpai.pos.customer.bean.show;

/**
 * 时间: 2020/2/6
 * 功能:
 */
public class MyNewBean {

    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : {"id":"1225353078136016898","fileType":"IMAGE","filePath":"http://valuation.oss-cn-shanghai.aliyuncs.com/property-file/20200206/3545a91f53904acf8f05786977752ed0.jpg","uploader":"666215782081888256","createTime":"2020-02-06 17:38:34","modifyTime":"2020-02-06 17:38:34"}
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
         * id : 1225353078136016898
         * fileType : IMAGE
         * filePath : http://valuation.oss-cn-shanghai.aliyuncs.com/property-file/20200206/3545a91f53904acf8f05786977752ed0.jpg
         * uploader : 666215782081888256
         * createTime : 2020-02-06 17:38:34
         * modifyTime : 2020-02-06 17:38:34
         */

        private String id;
        private String fileType;
        private String filePath;
        private String uploader;
        private String createTime;
        private String modifyTime;

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

        public String getUploader() {
            return uploader;
        }

        public void setUploader(String uploader) {
            this.uploader = uploader;
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
    }
}
