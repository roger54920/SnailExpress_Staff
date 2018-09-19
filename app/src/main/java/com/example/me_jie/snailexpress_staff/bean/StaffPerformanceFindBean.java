package com.example.me_jie.snailexpress_staff.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/6/1.
 * 员工业绩查询
 */

public class StaffPerformanceFindBean extends BaseBean{

    /**
     * page : {"pageNo":1,"pageSize":10,"count":1,"list":[{"id":"1","isNewRecord":false,"createDate":"2017-06-01 10:30:19","courier":{"id":"629f6f520a7e42d3853e18b9e1f16684","isNewRecord":false,"loginFlag":"1","roleNames":""},"consumerName":"小哈","consumerAddress":"不知道","businessId":"1234","businessType":"2","serviceCharge":0.6,"deliverySpeed":0,"serviceAttitude":0,"serviceReward":0},{"id":"88128421cfd54b02b07dff4c4cba0f6e","isNewRecord":false,"createDate":"2017-05-31 20:14:04","courier":{"id":"629f6f520a7e42d3853e18b9e1f16684","isNewRecord":false,"loginFlag":"1","roleNames":""},"consumerName":"Bella","consumerAddress":"北京北京丰台区城南益城园1楼1单元302","businessId":"f00655bc188b47f6a3fd21bafc78c9ab","businessType":"1","serviceCharge":0.8,"deliverySpeed":4,"serviceAttitude":1,"serviceReward":0.5}],"firstResult":0,"maxResults":10}
     * performanceIncome : 1.9
     */

    private PageBean page;
    private double performanceIncome;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public double getPerformanceIncome() {
        return performanceIncome;
    }

    public void setPerformanceIncome(double performanceIncome) {
        this.performanceIncome = performanceIncome;
    }

    public static class PageBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * count : 1
         * list : [{"id":"1","isNewRecord":false,"createDate":"2017-06-01 10:30:19","courier":{"id":"629f6f520a7e42d3853e18b9e1f16684","isNewRecord":false,"loginFlag":"1","roleNames":""},"consumerName":"小哈","consumerAddress":"不知道","businessId":"1234","businessType":"2","serviceCharge":0.6,"deliverySpeed":0,"serviceAttitude":0,"serviceReward":0},{"id":"88128421cfd54b02b07dff4c4cba0f6e","isNewRecord":false,"createDate":"2017-05-31 20:14:04","courier":{"id":"629f6f520a7e42d3853e18b9e1f16684","isNewRecord":false,"loginFlag":"1","roleNames":""},"consumerName":"Bella","consumerAddress":"北京北京丰台区城南益城园1楼1单元302","businessId":"f00655bc188b47f6a3fd21bafc78c9ab","businessType":"1","serviceCharge":0.8,"deliverySpeed":4,"serviceAttitude":1,"serviceReward":0.5}]
         * firstResult : 0
         * maxResults : 10
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private int firstResult;
        private int maxResults;
        private List<ListBean> list;

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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getFirstResult() {
            return firstResult;
        }

        public void setFirstResult(int firstResult) {
            this.firstResult = firstResult;
        }

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * isNewRecord : false
             * createDate : 2017-06-01 10:30:19
             * courier : {"id":"629f6f520a7e42d3853e18b9e1f16684","isNewRecord":false,"loginFlag":"1","roleNames":""}
             * consumerName : 小哈
             * consumerAddress : 不知道
             * businessId : 1234
             * businessType : 2
             * serviceCharge : 0.6
             * deliverySpeed : 0
             * serviceAttitude : 0
             * serviceReward : 0
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private CourierBean courier;
            private String consumerName;
            private String consumerAddress;
            private String businessId;
            private String businessType;
            private double serviceCharge;
            private int deliverySpeed;
            private int serviceAttitude;
            private double serviceReward;
            private double score;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public CourierBean getCourier() {
                return courier;
            }

            public void setCourier(CourierBean courier) {
                this.courier = courier;
            }

            public String getConsumerName() {
                return consumerName;
            }

            public void setConsumerName(String consumerName) {
                this.consumerName = consumerName;
            }

            public String getConsumerAddress() {
                return consumerAddress;
            }

            public void setConsumerAddress(String consumerAddress) {
                this.consumerAddress = consumerAddress;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public String getBusinessType() {
                return businessType;
            }

            public void setBusinessType(String businessType) {
                this.businessType = businessType;
            }

            public double getServiceCharge() {
                return serviceCharge;
            }

            public void setServiceCharge(double serviceCharge) {
                this.serviceCharge = serviceCharge;
            }

            public int getDeliverySpeed() {
                return deliverySpeed;
            }

            public void setDeliverySpeed(int deliverySpeed) {
                this.deliverySpeed = deliverySpeed;
            }

            public int getServiceAttitude() {
                return serviceAttitude;
            }

            public void setServiceAttitude(int serviceAttitude) {
                this.serviceAttitude = serviceAttitude;
            }

            public double getServiceReward() {
                return serviceReward;
            }

            public void setServiceReward(double serviceReward) {
                this.serviceReward = serviceReward;
            }

            public static class CourierBean {
                /**
                 * id : 629f6f520a7e42d3853e18b9e1f16684
                 * isNewRecord : false
                 * loginFlag : 1
                 * roleNames :
                 */

                private String id;
                private boolean isNewRecord;
                private String loginFlag;
                private String roleNames;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public boolean isIsNewRecord() {
                    return isNewRecord;
                }

                public void setIsNewRecord(boolean isNewRecord) {
                    this.isNewRecord = isNewRecord;
                }

                public String getLoginFlag() {
                    return loginFlag;
                }

                public void setLoginFlag(String loginFlag) {
                    this.loginFlag = loginFlag;
                }

                public String getRoleNames() {
                    return roleNames;
                }

                public void setRoleNames(String roleNames) {
                    this.roleNames = roleNames;
                }
            }
        }
    }
}
