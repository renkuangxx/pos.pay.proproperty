package com.jingpai.pos.customer.bean;

import java.io.Serializable;
import java.util.List;

public class FavoriteTabBean implements Serializable{

//    "endRow": 0,
//            "hasNextPage": true,
//            "hasPreviousPage": true,
//            "isFirstPage": true,
//            "isLastPage": true,
//            "list": [
//    {
//        "cover": "",
//            "h5Url": "",
//            "id": 0,
//            "link": "",
//            "title": ""
//    }
//		],
//                "navigateFirstPage": 0,
//                "navigateLastPage": 0,
//                "navigatePages": 0,
//                "navigatepageNums": [],
//                "nextPage": 0,
//                "pageNum": 0,
//                "pageSize": 0,
//                "pages": 0,
//                "prePage": 0,
//                "size": 0,
//                "startRow": 0,
//                "total": 0

    private String endRow;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private List<ProductBean> list;//
    private int navigateFirstPage;//
    private int navigateLastPage;//
    private int navigatePages;//
    private int nextPage;//
    private int pageNum;//
    private int pageSize;//
    private int pages;//
    private int prePage;//
    private int size;//

    public String getEndRow() {
        return endRow;
    }

    public void setEndRow(String endRow) {
        this.endRow = endRow;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<ProductBean> getList() {
        return list;
    }

    public void setList(List<ProductBean> list) {
        this.list = list;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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


