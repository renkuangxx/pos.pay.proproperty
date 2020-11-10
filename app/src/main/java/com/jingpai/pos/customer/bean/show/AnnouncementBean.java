package com.jingpai.pos.customer.bean.show;

import java.util.List;

/**
 * 时间: 2020/2/24
 * 功能:
 */
public class AnnouncementBean {

    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : {"pageSize":5,"data":[{"id":1,"title":"测试1","content":"测试2","delState":false,"createTime":"2020-02-24 19:52:20","modifyTime":"2020-02-24 19:52:20"},{"id":2,"title":"测试3","content":"测试4","delState":false,"createTime":"2020-02-24 19:52:33","modifyTime":"2020-02-24 19:52:33"},{"id":3,"title":"测试5","content":"测试6","delState":false,"createTime":"2020-02-24 19:52:39","modifyTime":"2020-02-24 19:52:39"}],"hasMore":false,"before":"3"}
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
         * pageSize : 5
         * data : [{"id":1,"title":"测试1","content":"测试2","delState":false,"createTime":"2020-02-24 19:52:20","modifyTime":"2020-02-24 19:52:20"},{"id":2,"title":"测试3","content":"测试4","delState":false,"createTime":"2020-02-24 19:52:33","modifyTime":"2020-02-24 19:52:33"},{"id":3,"title":"测试5","content":"测试6","delState":false,"createTime":"2020-02-24 19:52:39","modifyTime":"2020-02-24 19:52:39"}]
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
            @Override
            public String toString() {
                return "DataBean{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", content='" + content + '\'' +
                        ", delState=" + delState +
                        ", createTime='" + createTime + '\'' +
                        ", modifyTime='" + modifyTime + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }

            /**
             * id : 1
             * title : 测试1
             * content : 测试2
             * delState : false
             * createTime : 2020-02-24 19:52:20
             * modifyTime : 2020-02-24 19:52:20
             * type 推送类型（VOICE:语音消息，CONFIRM:需确认消息，WEBVIEW:打开H5页面，APP:打开原生页面，IMAGE:打开图片
             * url 跳转链接地址
             */


            private String id;
            private String title;
            private String content;
            private boolean delState;
            private String createTime;
            private String modifyTime;
            private String type;
            private String url;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public boolean isDelState() {
                return delState;
            }

            public void setDelState(boolean delState) {
                this.delState = delState;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(String modifyTime) {
                this.modifyTime = modifyTime;
            }
        }
    }
}
