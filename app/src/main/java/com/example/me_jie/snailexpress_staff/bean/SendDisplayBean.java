package com.example.me_jie.snailexpress_staff.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/4/27.
 * 站长寄件列表
 */

public class SendDisplayBean extends BaseBean{


    /**
     * page : {"pageNo":1,"pageSize":10,"count":1,"list":[{"id":"cf8b3fd48c0e4a189e1e8c6cf452faec","isNewRecord":false,"createDate":"2017-05-17 18:49:16","expressParcel":{"isNewRecord":true,"commodity":{"isNewRecord":true,"goodsName":"数码产品","goodsPrice":0,"goodsquantity":0,"goodsVol":0},"receiver":{"isNewRecord":true,"name":"李四","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"益城园1楼1单元101"},"sender":{"isNewRecord":true,"name":"李四","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"益城园1楼1单元101"},"shipperCode":"顺丰快递","lgisticCode":"0"}}],"firstResult":0,"maxResults":10}
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
         * count : 1
         * list : [{"id":"cf8b3fd48c0e4a189e1e8c6cf452faec","isNewRecord":false,"createDate":"2017-05-17 18:49:16","expressParcel":{"isNewRecord":true,"commodity":{"isNewRecord":true,"goodsName":"数码产品","goodsPrice":0,"goodsquantity":0,"goodsVol":0},"receiver":{"isNewRecord":true,"name":"李四","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"益城园1楼1单元101"},"sender":{"isNewRecord":true,"name":"李四","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"益城园1楼1单元101"},"shipperCode":"顺丰快递","lgisticCode":"0"}}]
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
             * id : cf8b3fd48c0e4a189e1e8c6cf452faec
             * isNewRecord : false
             * createDate : 2017-05-17 18:49:16
             * expressParcel : {"isNewRecord":true,"commodity":{"isNewRecord":true,"goodsName":"数码产品","goodsPrice":0,"goodsquantity":0,"goodsVol":0},"receiver":{"isNewRecord":true,"name":"李四","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"益城园1楼1单元101"},"sender":{"isNewRecord":true,"name":"李四","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"益城园1楼1单元101"},"shipperCode":"顺丰快递","lgisticCode":"0"}
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private ExpressParcelBean expressParcel;
            private String listHisCirInfor;
            private String remarks;
            private String businessStatus;

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getBusinessStatus() {
                return businessStatus;
            }

            public void setBusinessStatus(String businessStatus) {
                this.businessStatus = businessStatus;
            }

            public String getListHisCirInfor() {
                return listHisCirInfor;
            }

            public void setListHisCirInfor(String listHisCirInfor) {
                this.listHisCirInfor = listHisCirInfor;
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

            public ExpressParcelBean getExpressParcel() {
                return expressParcel;
            }

            public void setExpressParcel(ExpressParcelBean expressParcel) {
                this.expressParcel = expressParcel;
            }

            public static class ExpressParcelBean {
                /**
                 * isNewRecord : true
                 * commodity : {"isNewRecord":true,"goodsName":"数码产品","goodsPrice":0,"goodsquantity":0,"goodsVol":0}
                 * receiver : {"isNewRecord":true,"name":"李四","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"益城园1楼1单元101"}
                 * sender : {"isNewRecord":true,"name":"李四","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"益城园1楼1单元101"}
                 * shipperCode : 顺丰快递
                 * lgisticCode : 0
                 */

                private boolean isNewRecord;
                private CommodityBean commodity;
                private ReceiverBean receiver;
                private SenderBean sender;
                private String shipperCode;
                private String lgisticCode;

                public String getCost() {
                    return cost;
                }

                public void setCost(String cost) {
                    this.cost = cost;
                }

                private String cost;

                public boolean isIsNewRecord() {
                    return isNewRecord;
                }

                public void setIsNewRecord(boolean isNewRecord) {
                    this.isNewRecord = isNewRecord;
                }

                public CommodityBean getCommodity() {
                    return commodity;
                }

                public void setCommodity(CommodityBean commodity) {
                    this.commodity = commodity;
                }

                public ReceiverBean getReceiver() {
                    return receiver;
                }

                public void setReceiver(ReceiverBean receiver) {
                    this.receiver = receiver;
                }

                public SenderBean getSender() {
                    return sender;
                }

                public void setSender(SenderBean sender) {
                    this.sender = sender;
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

                public static class CommodityBean {
                    /**
                     * isNewRecord : true
                     * goodsName : 数码产品
                     * goodsPrice : 0
                     * goodsquantity : 0
                     * goodsVol : 0
                     */

                    private boolean isNewRecord;
                    private String goodsName;
                    private int goodsPrice;
                    private int goodsquantity;
                    private int goodsVol;

                    public String getGoodsWeight() {
                        return goodsWeight;
                    }

                    public void setGoodsWeight(String goodsWeight) {
                        this.goodsWeight = goodsWeight;
                    }

                    private String goodsWeight;

                    public boolean isIsNewRecord() {
                        return isNewRecord;
                    }

                    public void setIsNewRecord(boolean isNewRecord) {
                        this.isNewRecord = isNewRecord;
                    }

                    public String getGoodsName() {
                        return goodsName;
                    }

                    public void setGoodsName(String goodsName) {
                        this.goodsName = goodsName;
                    }

                    public int getGoodsPrice() {
                        return goodsPrice;
                    }

                    public void setGoodsPrice(int goodsPrice) {
                        this.goodsPrice = goodsPrice;
                    }

                    public int getGoodsquantity() {
                        return goodsquantity;
                    }

                    public void setGoodsquantity(int goodsquantity) {
                        this.goodsquantity = goodsquantity;
                    }

                    public int getGoodsVol() {
                        return goodsVol;
                    }

                    public void setGoodsVol(int goodsVol) {
                        this.goodsVol = goodsVol;
                    }
                }

                public static class ReceiverBean {
                    /**
                     * isNewRecord : true
                     * name : 李四
                     * provinceName : 北京
                     * cityName : 北京
                     * expAreaName : 丰台区
                     * address : 益城园1楼1单元101
                     */

                    private boolean isNewRecord;
                    private String name;
                    private String provinceName;
                    private String cityName;
                    private String expAreaName;
                    private String address;
                    private String mobile;

                    public String getMobile() {
                        return mobile;
                    }

                    public void setMobile(String mobile) {
                        this.mobile = mobile;
                    }


                    public boolean isIsNewRecord() {
                        return isNewRecord;
                    }

                    public void setIsNewRecord(boolean isNewRecord) {
                        this.isNewRecord = isNewRecord;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
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

                public static class SenderBean {
                    /**
                     * isNewRecord : true
                     * name : 李四
                     * provinceName : 北京
                     * cityName : 北京
                     * expAreaName : 丰台区
                     * address : 益城园1楼1单元101
                     */

                    private boolean isNewRecord;
                    private String name;
                    private String provinceName;
                    private String cityName;
                    private String expAreaName;
                    private String address;
                    public boolean isIsNewRecord() {
                        return isNewRecord;
                    }

                    public void setIsNewRecord(boolean isNewRecord) {
                        this.isNewRecord = isNewRecord;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
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
        }
    }
}
