package com.jingpai.pos.customer.bean;

import java.util.List;

public class ContentLikeCollectPageInfo {
    /**
     * endRow : 0
     * hasNextPage : true
     * hasPreviousPage : true
     * isFirstPage : true
     * isLastPage : true
     * list : [{"areaCode":"string","avatar":"string","body":"string","budget":"string","building":"string","collectCount":0,"contentCateId":0,"cover":"string","createTime":"2020-06-28T06:01:24.259Z","houseLayout":"string","houseNumber":"string","id":0,"industryId":0,"initClct":0,"initRead":0,"initThumb":0,"issueTime":"2020-06-28T06:01:24.259Z","like":true,"likeCount":0,"link":"string","merchantId":"string","merchantName":"string","nickName":"string","operUser":0,"operUserName":"string","pattern":"string","quartersCode":"string","remark":"string","renovationCondition":"string","sort":0,"squareCost":0,"state":0,"status":true,"style":"string","summary":"string","title":"string","towards":"string","updateTime":"2020-06-28T06:01:24.259Z"}]
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
    private List<ContentLikeCollectPageInfo.ListBean> list;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
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

    public static class ListBean {
        /**
         * areaCode : string
         * avatar : string
         * body : string
         * budget : string
         * building : string
         * collectCount : 0
         * contentCateId : 0
         * cover : string
         * createTime : 2020-06-28T06:01:24.259Z
         * houseLayout : string
         * houseNumber : string
         * id : 0
         * industryId : 0
         * initClct : 0
         * initRead : 0
         * initThumb : 0
         * issueTime : 2020-06-28T06:01:24.259Z
         * like : true
         * likeCount : 0
         * link : string
         * merchantId : string
         * merchantName : string
         * nickName : string
         * operUser : 0
         * operUserName : string
         * pattern : string
         * quartersCode : string
         * remark : string
         * renovationCondition : string
         * sort : 0
         * squareCost : 0
         * state : 0
         * status : true
         * style : string
         * summary : string
         * title : string
         * towards : string
         * updateTime : 2020-06-28T06:01:24.259Z
         */

        private String areaCode;
        private String avatar;
        private String body;
        private String budget;
        private String building;
        private int collectCount;
        private int contentCateId;
        private String cover;
        private String createTime;
        private String houseLayout;
        private String houseNumber;
        private int id;
        private int industryId;
        private int initClct;
        private int initRead;
        private int initThumb;
        private String issueTime;
        private boolean like;
        private int likeCount;
        private String link;
        private String merchantId;
        private String merchantName;
        private String nickName;
        private int operUser;
        private String operUserName;
        private String pattern;
        private String quartersCode;
        private String remark;
        private String renovationCondition;
        private int sort;
        private int squareCost;
        private int state;
        private boolean status;
        private String style;
        private String summary;
        private String title;
        private String towards;
        private String updateTime;

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }

        public int getContentCateId() {
            return contentCateId;
        }

        public void setContentCateId(int contentCateId) {
            this.contentCateId = contentCateId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHouseLayout() {
            return houseLayout;
        }

        public void setHouseLayout(String houseLayout) {
            this.houseLayout = houseLayout;
        }

        public String getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIndustryId() {
            return industryId;
        }

        public void setIndustryId(int industryId) {
            this.industryId = industryId;
        }

        public int getInitClct() {
            return initClct;
        }

        public void setInitClct(int initClct) {
            this.initClct = initClct;
        }

        public int getInitRead() {
            return initRead;
        }

        public void setInitRead(int initRead) {
            this.initRead = initRead;
        }

        public int getInitThumb() {
            return initThumb;
        }

        public void setInitThumb(int initThumb) {
            this.initThumb = initThumb;
        }

        public String getIssueTime() {
            return issueTime;
        }

        public void setIssueTime(String issueTime) {
            this.issueTime = issueTime;
        }

        public boolean isLike() {
            return like;
        }

        public void setLike(boolean like) {
            this.like = like;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getOperUser() {
            return operUser;
        }

        public void setOperUser(int operUser) {
            this.operUser = operUser;
        }

        public String getOperUserName() {
            return operUserName;
        }

        public void setOperUserName(String operUserName) {
            this.operUserName = operUserName;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public String getQuartersCode() {
            return quartersCode;
        }

        public void setQuartersCode(String quartersCode) {
            this.quartersCode = quartersCode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRenovationCondition() {
            return renovationCondition;
        }

        public void setRenovationCondition(String renovationCondition) {
            this.renovationCondition = renovationCondition;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getSquareCost() {
            return squareCost;
        }

        public void setSquareCost(int squareCost) {
            this.squareCost = squareCost;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTowards() {
            return towards;
        }

        public void setTowards(String towards) {
            this.towards = towards;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
