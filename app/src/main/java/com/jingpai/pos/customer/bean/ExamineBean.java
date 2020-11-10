package com.jingpai.pos.customer.bean;

import java.util.List;

/**
 * @author 86173
 *
 */
public class ExamineBean {
    /**
     * auditId :
     * auditResult :
     * backUrl :
     * contractFiles : [{"fileUrl":""}]
     * expireTime :
     * frontUrl :
     * remark :
     */
    private String auditId;
    private String auditResult;
    private String backUrl;
    private String expireTime;
    private String frontUrl;
    private String remark;
    private List<ContractFilesBean> contractFiles;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ContractFilesBean> getContractFiles() {
        return contractFiles;
    }

    public void setContractFiles(List<ContractFilesBean> contractFiles) {
        this.contractFiles = contractFiles;
    }

}
