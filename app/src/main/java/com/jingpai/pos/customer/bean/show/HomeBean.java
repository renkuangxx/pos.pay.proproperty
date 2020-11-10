package com.jingpai.pos.customer.bean.show;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class HomeBean {

    public static final String TYPE_MESSAGE = "MESSAGE";

    public static final String TYPE_NOTICE = "NOTICE";//公告

    public static final String TYPE_MANAGER = "MANAGER";

    public static final String TYPE_ADVERT = "ADVERT";//banner
    public static final String TYPE_COMMUNITY_ACTIVITY = "COMMUNITY-ACTIVITY";//
    public static final String TYPE_CITY_ACTIVITY = "CITY-ACTIVITY";//
    public static final String TYPE_MENU = "MENU";
    public static final String TYPE_MENU_ADD = "MENUVADD";

    private LogoBean logo;
    private boolean authed;
    private boolean judgeProprietor;
    private List<HomeItem> sections;

    public List<HomeItem> getSections() {
        return sections;
    }

    public void setSections(List<HomeItem> sections) {
        this.sections = sections;
    }

    public boolean isJudgeProprietor() {
        return judgeProprietor;
    }

    public void setJudgeProprietor(boolean judgeProprietor) {
        this.judgeProprietor = judgeProprietor;
    }

    public boolean isAuthed() {
        return authed;
    }

    public void setAuthed(boolean authed) {
        this.authed = authed;
    }

    public LogoBean getLogo() {
        return logo;
    }

    public void setLogo(LogoBean logo) {
        this.logo = logo;
    }

    public HomeItem getHomeItem(String type) {
        HomeItem homeItem = null;

        if (sections != null) {
            for (HomeItem section : sections) {
                if (type.equals(section.getType())) {
                    homeItem = section;
                    break;
                }
            }
        }

        return homeItem;
    }

    public <T> List<T> getBeanList(String type, Class<T> cls) {
        HomeItem homeItem = getHomeItem(type);

        if (homeItem != null && homeItem.getList() != null) {
            return JSONArray.parseArray(homeItem.getList(), cls);
        }

        return new ArrayList<T>(0);
    }

    public <T> T getBean(String type, Class<T> cls) {
        HomeItem homeItem = getHomeItem(type);

        T data = null;
        if (homeItem != null) {
            data = JSONArray.parseObject(homeItem.getData(), cls);
        }

        return data;
    }
}
