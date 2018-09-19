package com.example.me_jie.snailexpress_staff.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/5/13.
 * 抢单
 */

public class ExpressRobSingleBean extends BaseBean {

    /**
     * ifCourierContinueAbleGetQRCode : true
     */

    private boolean ifCourierContinueAbleGetQRCode;
    /**
     * page : {"pageNo":1,"pageSize":10,"count":1,"list":[{"username":"侯想","address":"北京海淀区倒座庙小区西区1楼1单元101","businessType":"送件","orderId":"23d73ba285014467b15e5879e3ef3b95","mobile":"18502340014"}],"maxResults":10,"firstResult":0}
     */

    private PageBean page;
    public boolean isIfCourierContinueAbleGetQRCode() {
        return ifCourierContinueAbleGetQRCode;
    }

    public void setIfCourierContinueAbleGetQRCode(boolean ifCourierContinueAbleGetQRCode) {
        this.ifCourierContinueAbleGetQRCode = ifCourierContinueAbleGetQRCode;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * count : 1
         * list : [{"username":"侯想","address":"北京海淀区倒座庙小区西区1楼1单元101","businessType":"送件","orderId":"23d73ba285014467b15e5879e3ef3b95","mobile":"18502340014"}]
         * maxResults : 10
         * firstResult : 0
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private int maxResults;
        private int firstResult;
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

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }

        public int getFirstResult() {
            return firstResult;
        }

        public void setFirstResult(int firstResult) {
            this.firstResult = firstResult;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * username : 侯想
             * address : 北京海淀区倒座庙小区西区1楼1单元101
             * businessType : 送件
             * orderId : 23d73ba285014467b15e5879e3ef3b95
             * mobile : 18502340014
             */

            private String username;
            private String address;
            private String businessType;
            private String orderId;
            private String mobile;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBusinessType() {
                return businessType;
            }

            public void setBusinessType(String businessType) {
                this.businessType = businessType;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }
    }
}
