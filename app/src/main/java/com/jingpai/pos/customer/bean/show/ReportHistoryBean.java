package com.jingpai.pos.customer.bean.show;

import java.util.List;

/*
 * function:
 */
public class ReportHistoryBean {


    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : {"pageSize":2,"data":[{"id":"1229616736110309378","type":"乱停","description":"饭后继续就更好","managerId":"1207944514048622592","state":-1,"createTime":"2020-02-18 12:00:50"},{"id":"1229616196743786497","type":"卫生","description":"我们都在这里了。我在这里等你回来我给你打电话了，你是谁呢。我们都在一起了。我在这里等你回来我给你打电话给我打电话吧。我","managerId":"1207944514048622592","state":0,"createTime":"2020-02-18 11:58:41"}],"hasMore":true,"before":"1581998321000"}
     */

    private boolean success;
    private String returnCode;
    private String returnMsg;
    private DataBeanX data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * pageSize : 2
         * data : [{"id":"1229616736110309378","type":"乱停","description":"饭后继续就更好","managerId":"1207944514048622592","state":-1,"createTime":"2020-02-18 12:00:50"},{"id":"1229616196743786497","type":"卫生","description":"我们都在这里了。我在这里等你回来我给你打电话了，你是谁呢。我们都在一起了。我在这里等你回来我给你打电话给我打电话吧。我","managerId":"1207944514048622592","state":0,"createTime":"2020-02-18 11:58:41"}]
         * hasMore : true
         * before : 1581998321000
         */

        private int pageSize;
        private boolean hasMore;
        private String before;
        private List<DataBean> data;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1229616736110309378
             * type : 乱停
             * description : 饭后继续就更好
             * managerId : 1207944514048622592
             * state : -1
             * createTime : 2020-02-18 12:00:50
             */

            private String id;
            private String type;
            private String description;
            private String managerId;
            private int state;
            private String createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getManagerId() {
                return managerId;
            }

            public void setManagerId(String managerId) {
                this.managerId = managerId;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
