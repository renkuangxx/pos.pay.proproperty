package com.jingpai.pos.customer.bean.show;

import java.io.Serializable;

/**
 * 时间: 2020/2/19
 * 功能:
 */
public class FileBean implements Serializable {

    /**
     * filePath : string
     * fileType : string
     * id : string
     */

    private String filePath;
    private String fileType;
    private String id;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
