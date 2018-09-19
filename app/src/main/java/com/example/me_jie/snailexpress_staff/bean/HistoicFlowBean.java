package com.example.me_jie.snailexpress_staff.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/4/8.
 * 时间轴
 */

public class HistoicFlowBean extends BaseBean{

    private List<ListTrajectoryBean> listTrajectory;

    public List<ListTrajectoryBean> getListTrajectory() {
        return listTrajectory;
    }

    public void setListTrajectory(List<ListTrajectoryBean> listTrajectory) {
        this.listTrajectory = listTrajectory;
    }

    public static class ListTrajectoryBean {
        /**
         * id : 1cb156e5f99341ef8dd4668b6c84d239
         * isNewRecord : false
         * pDate : 1495682646000
         * pContent : Bella 用户发起寄件申请 15811478673
         * sendoracceptId : ac8f6d58eae347c092db059b8f9b9698
         * type : 1
         */

        private String id;
        private boolean isNewRecord;
        private long pDate;
        private String pContent;
        private String sendoracceptId;
        private String type;

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

        public long getPDate() {
            return pDate;
        }

        public void setPDate(long pDate) {
            this.pDate = pDate;
        }

        public String getPContent() {
            return pContent;
        }

        public void setPContent(String pContent) {
            this.pContent = pContent;
        }

        public String getSendoracceptId() {
            return sendoracceptId;
        }

        public void setSendoracceptId(String sendoracceptId) {
            this.sendoracceptId = sendoracceptId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
