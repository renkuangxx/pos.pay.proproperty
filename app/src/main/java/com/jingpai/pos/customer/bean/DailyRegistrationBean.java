package com.jingpai.pos.customer.bean;

import java.util.List;

/**
 * 时间: 2020/3/9
 * 功能:
 */
public class DailyRegistrationBean {

    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : {"list":{"pageSize":20,"data":[{"id":3,"healthState":"健康","temperature":"","date":"2020-03-08"}],"hasMore":false,"before":"3"},"submit":false,"today":"2020-03-09"}
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
         * list : {"pageSize":20,"data":[{"id":3,"healthState":"健康","temperature":"","date":"2020-03-08"}],"hasMore":false,"before":"3"}
         * submit : false
         * today : 2020-03-09
         */

        private ListBean list;
        private boolean submit;
        private String today;

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public boolean isSubmit() {
            return submit;
        }

        public void setSubmit(boolean submit) {
            this.submit = submit;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public static class ListBean {
            /**
             * pageSize : 20
             * data : [{"id":3,"healthState":"健康","temperature":"","date":"2020-03-08"}]
             * hasMore : false
             * before : 3
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
                 * id : 3
                 * healthState : 健康
                 * temperature :
                 * date : 2020-03-08
                 */

                private int id;
                private String healthState;
                private String temperature;
                private String date;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getHealthState() {
                    return healthState;
                }

                public void setHealthState(String healthState) {
                    this.healthState = healthState;
                }

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }
        }
    }
}
