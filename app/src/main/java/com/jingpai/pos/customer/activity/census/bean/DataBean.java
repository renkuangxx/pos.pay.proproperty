package com.jingpai.pos.customer.activity.census.bean;

public class DataBean {
    /**
     * censusName :
     * completeness : 0
     * createTime :
     * gender :
     * idPassportNo :
     * identity :
     * name :
     */

    public String censusName;
    public int completeness;
    public String createTime;
    public String gender;
    public String idPassportNo;
    public String identity;
    public String name;


    public PopulationBean population;

    public PopulationBean getPopulation() {
        return population;
    }

    public void setPopulation(PopulationBean population) {
        this.population = population;
    }

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
