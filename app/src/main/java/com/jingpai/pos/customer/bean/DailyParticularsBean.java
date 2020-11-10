package com.jingpai.pos.customer.bean;

/*
 * function:
 */
public class DailyParticularsBean {

    /**
     * beenToEpidemicAreas : true
     * company :
     * createTime :
     * date :
     * healthCode :
     * healthState :
     * id : 0
     * idNumber :
     * idType :
     * name :
     * organization :
     * phone :
     * politic :
     * temperature :
     * touchPatient : true
     * userId :
     */

    private boolean beenToEpidemicAreas;
    private String company;
    private String createTime;
    private String date;
    private String healthCode;
    private String healthState;
    private int id;
    private String idNumber;
    private String idType;
    private String name;
    private String organization;
    private String phone;
    private String politic;
    private String temperature;
    private boolean touchPatient;
    private String userId;

    public boolean isBeenToEpidemicAreas() {
        return beenToEpidemicAreas;
    }

    public void setBeenToEpidemicAreas(boolean beenToEpidemicAreas) {
        this.beenToEpidemicAreas = beenToEpidemicAreas;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHealthCode() {
        return healthCode;
    }

    public void setHealthCode(String healthCode) {
        this.healthCode = healthCode;
    }

    public String getHealthState() {
        return healthState;
    }

    public void setHealthState(String healthState) {
        this.healthState = healthState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPolitic() {
        return politic;
    }

    public void setPolitic(String politic) {
        this.politic = politic;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public boolean isTouchPatient() {
        return touchPatient;
    }

    public void setTouchPatient(boolean touchPatient) {
        this.touchPatient = touchPatient;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
