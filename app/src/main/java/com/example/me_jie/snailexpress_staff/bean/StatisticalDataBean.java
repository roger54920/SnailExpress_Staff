package com.example.me_jie.snailexpress_staff.bean;

/**
 * Created by me-jie on 2017/5/17.
 * 首页数据统计
 */

public class StatisticalDataBean extends BaseBean{


    /**
     * returnCount : 0
     * sendCount : 0
     * acceptCount : 0
     */

    private String returnCount;
    private int sendCount;
    private int acceptCount;
    private ExpressRobSingleBean.PageBean.ListBean order;//抢单
    private SendDisplayBean.PageBean.ListBean expressSend;//寄件
    private RecipientsDisplayBean.PageBean.ListBean expressAccept;//收件

    public String getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(String returnCount) {
        this.returnCount = returnCount;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public int getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(int acceptCount) {
        this.acceptCount = acceptCount;
    }

    public ExpressRobSingleBean.PageBean.ListBean getOrder() {
        return order;
    }

    public void setOrder(ExpressRobSingleBean.PageBean.ListBean order) {
        this.order = order;
    }

    public SendDisplayBean.PageBean.ListBean getExpressSend() {
        return expressSend;
    }

    public void setExpressSend(SendDisplayBean.PageBean.ListBean expressSend) {
        this.expressSend = expressSend;
    }

    public RecipientsDisplayBean.PageBean.ListBean getExpressAccept() {
        return expressAccept;
    }

    public void setExpressAccept(RecipientsDisplayBean.PageBean.ListBean expressAccept) {
        this.expressAccept = expressAccept;
    }
}
