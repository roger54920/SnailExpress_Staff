package com.example.me_jie.snailexpress_staff.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/4/27.
 * 站长收件列表
 */

public class RecipientsDisplayBean extends BaseBean{

    /**
     * page : {"pageNo":1,"pageSize":10,"count":3,"list":[{"id":"578e7fce50cf4f448d148f374aa514fe","isNewRecord":false,"processInstanceStatus":"11111","remarks":"2222","createDate":"2017-05-09 22:33:33","updateDate":"2017-05-09 22:40:52","expressParcel":{"id":"4e77405bc7194cca9df63e734310a95f","isNewRecord":false,"shipperCode":"YTO","lgisticCode":"12345678123"},"stationmaster":{"id":"41c9f18bc59140068c4297f24b30389d","isNewRecord":false,"loginFlag":"1","roleNames":""},"workstation":{"id":"a07a9224689f468495f0f56a5ebd280e","isNewRecord":false},"businessStatus":"快递派件中","receiver":{"id":"afb819f533d843b0a483c59e2ff1daeb","isNewRecord":false},"ifInWorkstation":"1","containerNO":"12121212","costBePaid":0}],"firstResult":0,"maxResults":10}
     */

    private PageBean page;

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
         * count : 3
         * list : [{"id":"578e7fce50cf4f448d148f374aa514fe","isNewRecord":false,"processInstanceStatus":"11111","remarks":"2222","createDate":"2017-05-09 22:33:33","updateDate":"2017-05-09 22:40:52","expressParcel":{"id":"4e77405bc7194cca9df63e734310a95f","isNewRecord":false,"shipperCode":"YTO","lgisticCode":"12345678123"},"stationmaster":{"id":"41c9f18bc59140068c4297f24b30389d","isNewRecord":false,"loginFlag":"1","roleNames":""},"workstation":{"id":"a07a9224689f468495f0f56a5ebd280e","isNewRecord":false},"businessStatus":"快递派件中","receiver":{"id":"afb819f533d843b0a483c59e2ff1daeb","isNewRecord":false},"ifInWorkstation":"1","containerNO":"12121212","costBePaid":0}]
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
             * id : 578e7fce50cf4f448d148f374aa514fe
             * isNewRecord : false
             * processInstanceStatus : 11111
             * remarks : 2222
             * createDate : 2017-05-09 22:33:33
             * updateDate : 2017-05-09 22:40:52
             * expressParcel : {"id":"4e77405bc7194cca9df63e734310a95f","isNewRecord":false,"shipperCode":"YTO","lgisticCode":"12345678123"}
             * stationmaster : {"id":"41c9f18bc59140068c4297f24b30389d","isNewRecord":false,"loginFlag":"1","roleNames":""}
             * workstation : {"id":"a07a9224689f468495f0f56a5ebd280e","isNewRecord":false}
             * businessStatus : 快递派件中
             * receiver : {"id":"afb819f533d843b0a483c59e2ff1daeb","isNewRecord":false}
             * ifInWorkstation : 1
             * containerNO : 12121212
             * costBePaid : 0
             */

            private String id;
            private boolean isNewRecord;
            private String processInstanceStatus;
            private String remarks;
            private String createDate;
            private String updateDate;
            private ExpressParcelBean expressParcel;
            private StationmasterBean stationmaster;
            private WorkstationBean workstation;
            private String businessStatus;
            private ReceiverBean receiver;
            private String ifInWorkstation;
            private String containerNO;
            private int costBePaid;
            private String procInsId;
            private String ifConsumerPickUp;
            private String ifCancel;
            private String businessStatusCode;

            public String getIfCancel() {
                return ifCancel;
            }

            public void setIfCancel(String ifCancel) {
                this.ifCancel = ifCancel;
            }

            public String getIfConsumerPickUp() {
                return ifConsumerPickUp;
            }

            public void setIfConsumerPickUp(String ifConsumerPickUp) {
                this.ifConsumerPickUp = ifConsumerPickUp;
            }

            public String getBusinessStatusCode() {
                return businessStatusCode;
            }

            public void setBusinessStatusCode(String businessStatusCode) {
                this.businessStatusCode = businessStatusCode;
            }

            public String getProcInsId() {
                return procInsId;
            }

            public void setProcInsId(String procInsId) {
                this.procInsId = procInsId;
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

            public String getProcessInstanceStatus() {
                return processInstanceStatus;
            }

            public void setProcessInstanceStatus(String processInstanceStatus) {
                this.processInstanceStatus = processInstanceStatus;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public ExpressParcelBean getExpressParcel() {
                return expressParcel;
            }

            public void setExpressParcel(ExpressParcelBean expressParcel) {
                this.expressParcel = expressParcel;
            }

            public StationmasterBean getStationmaster() {
                return stationmaster;
            }

            public void setStationmaster(StationmasterBean stationmaster) {
                this.stationmaster = stationmaster;
            }

            public WorkstationBean getWorkstation() {
                return workstation;
            }

            public void setWorkstation(WorkstationBean workstation) {
                this.workstation = workstation;
            }

            public String getBusinessStatus() {
                return businessStatus;
            }

            public void setBusinessStatus(String businessStatus) {
                this.businessStatus = businessStatus;
            }

            public ReceiverBean getReceiver() {
                return receiver;
            }

            public void setReceiver(ReceiverBean receiver) {
                this.receiver = receiver;
            }

            public String getIfInWorkstation() {
                return ifInWorkstation;
            }

            public void setIfInWorkstation(String ifInWorkstation) {
                this.ifInWorkstation = ifInWorkstation;
            }

            public String getContainerNO() {
                return containerNO;
            }

            public void setContainerNO(String containerNO) {
                this.containerNO = containerNO;
            }

            public int getCostBePaid() {
                return costBePaid;
            }

            public void setCostBePaid(int costBePaid) {
                this.costBePaid = costBePaid;
            }

            public static class ExpressParcelBean {
                /**
                 * id : 4e77405bc7194cca9df63e734310a95f
                 * isNewRecord : false
                 * shipperCode : YTO
                 * lgisticCode : 12345678123
                 */

                private String id;
                private boolean isNewRecord;
                private String shipperCode;
                private String lgisticCode;
                private String cost;

                public String getCost() {
                    return cost;
                }

                public void setCost(String cost) {
                    this.cost = cost;
                }


                private ReceiverBean receiver;

                public ReceiverBean getReceiver() {
                    return receiver;
                }

                public void setReceiver(ReceiverBean receiver) {
                    this.receiver = receiver;
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

                public String getShipperCode() {
                    return shipperCode;
                }

                public void setShipperCode(String shipperCode) {
                    this.shipperCode = shipperCode;
                }

                public String getLgisticCode() {
                    return lgisticCode;
                }

                public void setLgisticCode(String lgisticCode) {
                    this.lgisticCode = lgisticCode;
                }

                public static class ReceiverBean{
                    private String id;
                    private boolean isNewRecord;
                    private String createDate;
                    private String updateDate;
                    private String name;
                    private String mobile;
                    private String provinceName;
                    private String cityName;
                    private String expAreaName;
                    private String address;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public boolean isNewRecord() {
                        return isNewRecord;
                    }

                    public void setNewRecord(boolean newRecord) {
                        isNewRecord = newRecord;
                    }

                    public String getCreateDate() {
                        return createDate;
                    }

                    public void setCreateDate(String createDate) {
                        this.createDate = createDate;
                    }

                    public String getUpdateDate() {
                        return updateDate;
                    }

                    public void setUpdateDate(String updateDate) {
                        this.updateDate = updateDate;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getMobile() {
                        return mobile;
                    }

                    public void setMobile(String mobile) {
                        this.mobile = mobile;
                    }

                    public String getProvinceName() {
                        return provinceName;
                    }

                    public void setProvinceName(String provinceName) {
                        this.provinceName = provinceName;
                    }

                    public String getCityName() {
                        return cityName;
                    }

                    public void setCityName(String cityName) {
                        this.cityName = cityName;
                    }

                    public String getExpAreaName() {
                        return expAreaName;
                    }

                    public void setExpAreaName(String expAreaName) {
                        this.expAreaName = expAreaName;
                    }

                    public String getAddress() {
                        return address;
                    }

                    public void setAddress(String address) {
                        this.address = address;
                    }
                }

            }

            public static class StationmasterBean {
                /**
                 * id : 41c9f18bc59140068c4297f24b30389d
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

            public static class WorkstationBean {
                /**
                 * id : a07a9224689f468495f0f56a5ebd280e
                 * isNewRecord : false
                 */

                private String id;
                private boolean isNewRecord;

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
            }

            public static class ReceiverBean {
                /**
                 * id : afb819f533d843b0a483c59e2ff1daeb
                 * isNewRecord : false
                 */

                private String id;
                private boolean isNewRecord;

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
            }
        }
    }
}
