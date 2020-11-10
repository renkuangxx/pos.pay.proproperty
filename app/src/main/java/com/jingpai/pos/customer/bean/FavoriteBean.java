package com.jingpai.pos.customer.bean;

import java.io.Serializable;
import java.util.List;

public class FavoriteBean implements Serializable{

    private String backGroundUrl;
    private List<TabBean> homeContentCateList;//

    public String getBackGroundUrl() {
        return backGroundUrl;
    }

    public void setBackGroundUrl(String backGroundUrl) {
        this.backGroundUrl = backGroundUrl;
    }

    public List<TabBean> getHomeContentCateList() {
        return homeContentCateList;
    }

    public void setHomeContentCateList(List<TabBean> homeContentCateList) {
        this.homeContentCateList = homeContentCateList;
    }

    public static class TabBean implements Serializable{
        private String id;
        private String name;
        private int sort;
        private List<ProductBean> homeContentVos;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public List<ProductBean> getHomeContentVos() {
            return homeContentVos;
        }

        public void setHomeContentVos(List<ProductBean> homeContentVos) {
            this.homeContentVos = homeContentVos;
        }

        public static class ProductBean implements Serializable {
            private String cover;
            private String h5Url;//
            private String id;//
            private String link;//
            private String title;//

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getH5Url() {
                return h5Url;
            }

            public void setH5Url(String h5Url) {
                this.h5Url = h5Url;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

        }
    }


}


