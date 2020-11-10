package com.jingpai.pos.customer.bean.show;

import java.util.List;

public class CommunityServeBean {

    private List<MenuBean> menuList;

    private HomePageBean homePage;

    public List<MenuBean> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuBean> menuList) {
        this.menuList = menuList;
    }

    public HomePageBean getHomePage() {
        return homePage;
    }

    public void setHomePage(HomePageBean homePage) {
        this.homePage = homePage;
    }

    public CommunityServeBean() {
    }

    public static class MenuBean{
        private int tourisType;
        private String redirectUrl;
        private String name;
        private String icon;
        private int status;

        public int getTourisType() {
            return tourisType;
        }

        public void setTourisType(int tourisType) {
            this.tourisType = tourisType;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
    public static class HomePageBean {
        private String redirectUrl;
        private String icon;
        private int status;

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
