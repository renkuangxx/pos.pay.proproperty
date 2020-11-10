package com.jingpai.pos.customer.activity.web;

import com.jingpai.pos.customer.activity.census.activity.FirstCensusActivity;
import com.jingpai.pos.activity.login.PersonalCenterActivity;

public enum AppPage {

    PERSONAL_CENTER("personalCenter", PersonalCenterActivity.class),

    POPULATION_CENSUS("populationCensus", FirstCensusActivity.class);

    private String name;

    private Class activity;

    private AppPage(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public Class getActivity() {
        return activity;
    }

    public static AppPage instanceOf(String name) {

        for (AppPage value : AppPage.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }

        return null;
    }

    public static void openPage(String name) {

    }
}
