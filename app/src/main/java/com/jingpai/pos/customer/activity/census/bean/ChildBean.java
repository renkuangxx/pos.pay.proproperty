package com.jingpai.pos.customer.activity.census.bean;

public class ChildBean {
    String birthDate;
    String gender;

    public ChildBean(String birthDate, String gender) {
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
