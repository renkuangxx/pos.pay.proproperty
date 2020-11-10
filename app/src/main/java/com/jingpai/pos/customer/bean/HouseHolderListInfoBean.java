package com.jingpai.pos.customer.bean;

import java.util.List;

public class HouseHolderListInfoBean {

    /**
     * after : {}
     * before :
     * data : [{"applyTime":"","auditId":"","auditState":0,"auditStateName":"","layout":"","name":"","phone":""}]
     * hasMore : true
     * pageNo : 0
     * pageSize : 0
     */

    private AfterBean after;
    private String before;
    private boolean hasMore;
    private int pageNo;
    private int pageSize;
    private List<HouseHolderDataBean> data;

    public AfterBean getAfter() {
        return after;
    }

    public void setAfter(AfterBean after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<HouseHolderDataBean> getData() {
        return data;
    }

    public void setData(List<HouseHolderDataBean> data) {
        this.data = data;
    }

    public static class AfterBean {
    }
}
