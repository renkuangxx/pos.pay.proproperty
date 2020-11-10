package com.jingpai.pos.bean;

public class VersionBean {

    private String downloadUrl;

    private boolean forcedUpdate;

    private Integer versionCode;
    private String versionName;

    private String versionDesc;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

//    public boolean getForcedUpdate() {
//        return forcedUpdate;
//    }
//
//    public void setForcedUpdate(boolean forcedUpdate) {
//        this.forcedUpdate = forcedUpdate;
//    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isForcedUpdate() {
        return forcedUpdate;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }
}
