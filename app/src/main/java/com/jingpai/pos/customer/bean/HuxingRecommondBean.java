package com.jingpai.pos.customer.bean;

import java.util.List;

public class HuxingRecommondBean {
        /**
         * cateImages : string
         * cateTitle : string
         * contentLikeCollectPageInfo : {"endRow":0,"hasNextPage":true,"hasPreviousPage":true,"isFirstPage":true,"isLastPage":true,"list":[{"areaCode":"string","avatar":"string","body":"string","budget":"string","building":"string","collectCount":0,"contentCateId":0,"cover":"string","createTime":"2020-06-30T02:16:52.233Z","houseLayout":"string","houseNumber":"string","id":0,"industryId":0,"initClct":0,"initRead":0,"initThumb":0,"issueTime":"2020-06-30T02:16:52.233Z","like":true,"likeCount":0,"link":"string","merchantId":"string","merchantName":"string","nickName":"string","operUser":0,"operUserName":"string","pattern":"string","quartersCode":"string","remark":"string","renovationCondition":"string","sort":0,"squareCost":0,"state":0,"status":true,"style":"string","summary":"string","title":"string","towards":"string","updateTime":"2020-06-30T02:16:52.233Z","userMobile":"string"}],"navigateFirstPage":0,"navigateLastPage":0,"navigatePages":0,"navigatepageNums":[0],"nextPage":0,"pageNum":0,"pageSize":0,"pages":0,"prePage":0,"size":0,"startRow":0,"total":0}
         */

        private String cateImages;
        private String cateTitle;
        private ContentLikeCollectPageInfoBean contentLikeCollectPageInfo;

        public String getCateImages() {
            return cateImages;
        }

        public void setCateImages(String cateImages) {
            this.cateImages = cateImages;
        }

        public String getCateTitle() {
            return cateTitle;
        }

        public void setCateTitle(String cateTitle) {
            this.cateTitle = cateTitle;
        }

        public ContentLikeCollectPageInfoBean getContentLikeCollectPageInfo() {
            return contentLikeCollectPageInfo;
        }

        public void setContentLikeCollectPageInfo(ContentLikeCollectPageInfoBean contentLikeCollectPageInfo) {
            this.contentLikeCollectPageInfo = contentLikeCollectPageInfo;
        }

        public static class ContentLikeCollectPageInfoBean {
            /**
             * endRow : 0
             * hasNextPage : true
             * hasPreviousPage : true
             * isFirstPage : true
             * isLastPage : true
             * list : [{"areaCode":"string","avatar":"string","body":"string","budget":"string","building":"string","collectCount":0,"contentCateId":0,"cover":"string","createTime":"2020-06-30T02:16:52.233Z","houseLayout":"string","houseNumber":"string","id":0,"industryId":0,"initClct":0,"initRead":0,"initThumb":0,"issueTime":"2020-06-30T02:16:52.233Z","like":true,"likeCount":0,"link":"string","merchantId":"string","merchantName":"string","nickName":"string","operUser":0,"operUserName":"string","pattern":"string","quartersCode":"string","remark":"string","renovationCondition":"string","sort":0,"squareCost":0,"state":0,"status":true,"style":"string","summary":"string","title":"string","towards":"string","updateTime":"2020-06-30T02:16:52.233Z","userMobile":"string"}]
             * navigateFirstPage : 0
             * navigateLastPage : 0
             * navigatePages : 0
             * navigatepageNums : [0]
             * nextPage : 0
             * pageNum : 0
             * pageSize : 0
             * pages : 0
             * prePage : 0
             * size : 0
             * startRow : 0
             * total : 0
             */

            private int endRow;
            private boolean hasNextPage;
            private boolean hasPreviousPage;
            private boolean isFirstPage;
            private boolean isLastPage;
            private int navigateFirstPage;
            private int navigateLastPage;
            private int navigatePages;
            private int nextPage;
            private int pageNum;
            private int pageSize;
            private int pages;
            private int prePage;
            private int size;
            private int startRow;
            private int total;
            private List<HuxingListBean> list;
            private List<Integer> navigatepageNums;

            public int getEndRow() {
                return endRow;
            }

            public void setEndRow(int endRow) {
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

            public boolean isIsFirstPage() {
                return isFirstPage;
            }

            public void setIsFirstPage(boolean isFirstPage) {
                this.isFirstPage = isFirstPage;
            }

            public boolean isIsLastPage() {
                return isLastPage;
            }

            public void setIsLastPage(boolean isLastPage) {
                this.isLastPage = isLastPage;
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

            public int getStartRow() {
                return startRow;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<Integer> getNavigatepageNums() {
                return navigatepageNums;
            }

            public void setNavigatepageNums(List<Integer> navigatepageNums) {
                this.navigatepageNums = navigatepageNums;
            }

            public List<HuxingListBean> getList() {
                return list;
            }

            public void setList(List<HuxingListBean> list) {
                this.list = list;
            }
        }
}
