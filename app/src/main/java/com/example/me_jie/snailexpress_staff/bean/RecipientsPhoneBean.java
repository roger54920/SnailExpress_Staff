package com.example.me_jie.snailexpress_staff.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/5/9.
 * 查询  手机号获取信息
 */

public class RecipientsPhoneBean extends BaseBean{

    /**
     * ifPhone : true
     * ifUserExit : true
     * address : [{"id":"9d3d4e3bbafb47f19cf2f6a1ec46d596","remarks":"北京市,市辖区,海淀区,人民社区,10号楼,5单元,301室","createDate":"2017-06-20 15:52:46","updateDate":"2017-06-20 15:52:46","owner":{"id":"7b0f42c4a8c3485ea0fc94e4cb6a3154","loginFlag":"1"},"provincialCode":"110000","cityCode":"110100","areaCode":"110108","cellCode":"a8026e580278421ab1c3c508d10bc883","houseNumber":"f7319a5092a44394bc3b135fb9956688","unit":"e00609b1c9404dac84caec1770a0cba5","room":"617f21b29fbb4763aa66a3bb63c95986","ifDefault":"1","type":"1","ifUserParcelNo":"1","parcelNo":"12568","phone":"13520151945","name":"后天"}]
     */

    private boolean ifPhone;
    private boolean ifUserExit;
    private List<AddressBean> address;

    public boolean isIfPhone() {
        return ifPhone;
    }

    public void setIfPhone(boolean ifPhone) {
        this.ifPhone = ifPhone;
    }

    public boolean isIfUserExit() {
        return ifUserExit;
    }

    public void setIfUserExit(boolean ifUserExit) {
        this.ifUserExit = ifUserExit;
    }

    public List<AddressBean> getAddress() {
        return address;
    }

    public void setAddress(List<AddressBean> address) {
        this.address = address;
    }

    public static class AddressBean {
        /**
         * id : 9d3d4e3bbafb47f19cf2f6a1ec46d596
         * remarks : 北京市,市辖区,海淀区,人民社区,10号楼,5单元,301室
         * createDate : 2017-06-20 15:52:46
         * updateDate : 2017-06-20 15:52:46
         * owner : {"id":"7b0f42c4a8c3485ea0fc94e4cb6a3154","loginFlag":"1"}
         * provincialCode : 110000
         * cityCode : 110100
         * areaCode : 110108
         * cellCode : a8026e580278421ab1c3c508d10bc883
         * houseNumber : f7319a5092a44394bc3b135fb9956688
         * unit : e00609b1c9404dac84caec1770a0cba5
         * room : 617f21b29fbb4763aa66a3bb63c95986
         * ifDefault : 1
         * type : 1
         * ifUserParcelNo : 1
         * parcelNo : 12568
         * phone : 13520151945
         * name : 后天
         */

        private String id;
        private String remarks;
        private String createDate;
        private String updateDate;
        private OwnerBean owner;
        private String provincialCode;
        private String cityCode;
        private String areaCode;
        private String cellCode;
        private String houseNumber;
        private String unit;
        private String room;
        private String ifDefault;
        private String type;
        private String ifUserParcelNo;
        private String parcelNo;
        private String phone;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public OwnerBean getOwner() {
            return owner;
        }

        public void setOwner(OwnerBean owner) {
            this.owner = owner;
        }

        public String getProvincialCode() {
            return provincialCode;
        }

        public void setProvincialCode(String provincialCode) {
            this.provincialCode = provincialCode;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getCellCode() {
            return cellCode;
        }

        public void setCellCode(String cellCode) {
            this.cellCode = cellCode;
        }

        public String getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getIfDefault() {
            return ifDefault;
        }

        public void setIfDefault(String ifDefault) {
            this.ifDefault = ifDefault;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIfUserParcelNo() {
            return ifUserParcelNo;
        }

        public void setIfUserParcelNo(String ifUserParcelNo) {
            this.ifUserParcelNo = ifUserParcelNo;
        }

        public String getParcelNo() {
            return parcelNo;
        }

        public void setParcelNo(String parcelNo) {
            this.parcelNo = parcelNo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class OwnerBean {
            /**
             * id : 7b0f42c4a8c3485ea0fc94e4cb6a3154
             * loginFlag : 1
             */

            private String id;
            private String loginFlag;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLoginFlag() {
                return loginFlag;
            }

            public void setLoginFlag(String loginFlag) {
                this.loginFlag = loginFlag;
            }
        }
    }
}
