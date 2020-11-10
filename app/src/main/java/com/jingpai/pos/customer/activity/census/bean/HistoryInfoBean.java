package com.jingpai.pos.customer.activity.census.bean;

/**
 * @author 86173
 */
public class HistoryInfoBean {

    /**
     *
     * censusName	人口普查名称	string
     * completeness	完成度（百分比）	integer(int32)	integer(int32)
     * createTime	创建时间	string(date-time)	string(date-time)
     * gender	性别（女，男）	string
     * idPassportNo	证件号	string
     * identity	身份	string
     * name	姓名
     */

    private String censusName;
    private int completeness;
    private String createTime;
    private String gender;
    private String idPassportNo;
    private String identity;
    private String name;

    public String getCensusName() {
        return censusName;
    }

    public void setCensusName(String censusName) {
        this.censusName = censusName;
    }

    public int getCompleteness() {
        return completeness;
    }

    public void setCompleteness(int completeness) {
        this.completeness = completeness;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdPassportNo() {
        return idPassportNo;
    }

    public void setIdPassportNo(String idPassportNo) {
        this.idPassportNo = idPassportNo;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
