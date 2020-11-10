package com.jingpai.pos.customer.bean;

import java.util.List;

public class CommunityByCityNameBean {

    /**
     * city :
     * code :
     * page : {"current":0,"hitCount":true,"optimizeCountSql":true,"orders":[{"asc":true,"column":""}],"pages":0,"records":[{"accessType":"","address":"","areaCode":"","areaName":"","buildCount":0,"cascade":[],"city":"","corpId":"","costName":"","costType":"","county":"","createTime":"","delState":true,"deviceRoomCount":0,"houseCount":0,"id":0,"modifyTime":"","name":"","orgId":"","parkingCode":"","parkingCount":0,"province":"","sellerCode":"","sellerStatus":"","shopCount":0,"toolId":""}],"searchCount":true,"size":0,"total":0}
     */

    private String city;
    private String code;
    private PageBean page;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * current : 0
         * hitCount : true
         * optimizeCountSql : true
         * orders : [{"asc":true,"column":""}]
         * pages : 0
         * records : [{"accessType":"","address":"","areaCode":"","areaName":"","buildCount":0,"cascade":[],"city":"","corpId":"","costName":"","costType":"","county":"","createTime":"","delState":true,"deviceRoomCount":0,"houseCount":0,"id":0,"modifyTime":"","name":"","orgId":"","parkingCode":"","parkingCount":0,"province":"","sellerCode":"","sellerStatus":"","shopCount":0,"toolId":""}]
         * searchCount : true
         * size : 0
         * total : 0
         */

        private int current;
        private boolean hitCount;
        private boolean optimizeCountSql;
        private int pages;
        private boolean searchCount;
        private int size;
        private int total;
        private List<OrdersBean> orders;
        private List<RecordsBean> records;

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public boolean isHitCount() {
            return hitCount;
        }

        public void setHitCount(boolean hitCount) {
            this.hitCount = hitCount;
        }

        public boolean isOptimizeCountSql() {
            return optimizeCountSql;
        }

        public void setOptimizeCountSql(boolean optimizeCountSql) {
            this.optimizeCountSql = optimizeCountSql;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class OrdersBean {
            /**
             * asc : true
             * column :
             */

            private boolean asc;
            private String column;

            public boolean isAsc() {
                return asc;
            }

            public void setAsc(boolean asc) {
                this.asc = asc;
            }

            public String getColumn() {
                return column;
            }

            public void setColumn(String column) {
                this.column = column;
            }
        }

        public static class RecordsBean {
            /**
             * accessType :
             * address :
             * areaCode :
             * areaName :
             * buildCount : 0
             * cascade : []
             * city :
             * corpId :
             * costName :
             * costType :
             * county :
             * createTime :
             * delState : true
             * deviceRoomCount : 0
             * houseCount : 0
             * id : 0
             * modifyTime :
             * name :
             * orgId :
             * parkingCode :
             * parkingCount : 0
             * province :
             * sellerCode :
             * sellerStatus :
             * shopCount : 0
             * toolId :
             */

            private String accessType;
            private String address;
            private String areaCode;
            private String areaName;
            private int buildCount;
            private String city;
            private String corpId;
            private String costName;
            private String costType;
            private String county;
            private String createTime;
            private boolean delState;
            private int deviceRoomCount;
            private int houseCount;
            private int id;
            private String modifyTime;
            private String name;
            private String orgId;
            private String parkingCode;
            private int parkingCount;
            private String province;
            private String sellerCode;
            private String sellerStatus;
            private int shopCount;
            private String toolId;
            private List<?> cascade;

            public String getAccessType() {
                return accessType;
            }

            public void setAccessType(String accessType) {
                this.accessType = accessType;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public int getBuildCount() {
                return buildCount;
            }

            public void setBuildCount(int buildCount) {
                this.buildCount = buildCount;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCorpId() {
                return corpId;
            }

            public void setCorpId(String corpId) {
                this.corpId = corpId;
            }

            public String getCostName() {
                return costName;
            }

            public void setCostName(String costName) {
                this.costName = costName;
            }

            public String getCostType() {
                return costType;
            }

            public void setCostType(String costType) {
                this.costType = costType;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public boolean isDelState() {
                return delState;
            }

            public void setDelState(boolean delState) {
                this.delState = delState;
            }

            public int getDeviceRoomCount() {
                return deviceRoomCount;
            }

            public void setDeviceRoomCount(int deviceRoomCount) {
                this.deviceRoomCount = deviceRoomCount;
            }

            public int getHouseCount() {
                return houseCount;
            }

            public void setHouseCount(int houseCount) {
                this.houseCount = houseCount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(String modifyTime) {
                this.modifyTime = modifyTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public String getParkingCode() {
                return parkingCode;
            }

            public void setParkingCode(String parkingCode) {
                this.parkingCode = parkingCode;
            }

            public int getParkingCount() {
                return parkingCount;
            }

            public void setParkingCount(int parkingCount) {
                this.parkingCount = parkingCount;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getSellerCode() {
                return sellerCode;
            }

            public void setSellerCode(String sellerCode) {
                this.sellerCode = sellerCode;
            }

            public String getSellerStatus() {
                return sellerStatus;
            }

            public void setSellerStatus(String sellerStatus) {
                this.sellerStatus = sellerStatus;
            }

            public int getShopCount() {
                return shopCount;
            }

            public void setShopCount(int shopCount) {
                this.shopCount = shopCount;
            }

            public String getToolId() {
                return toolId;
            }

            public void setToolId(String toolId) {
                this.toolId = toolId;
            }

            public List<?> getCascade() {
                return cascade;
            }

            public void setCascade(List<?> cascade) {
                this.cascade = cascade;
            }
        }
    }
}
