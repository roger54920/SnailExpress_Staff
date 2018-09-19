package com.example.me_jie.snailexpress_staff.bean;

/**
 * Created by me-jie on 2017/5/27.
 * 抢单列表item
 */

public class OrderBean {
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
