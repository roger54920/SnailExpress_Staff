package com.example.me_jie.snailexpress_staff.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by me-jie on 2017/4/25.
 * 蜗牛站点
 */

public class SnailSiteBean extends BaseBean{

    private List<WorkStationListBean> workStationList;

    public List<WorkStationListBean> getWorkStationList() {
        return workStationList;
    }

    public void setWorkStationList(List<WorkStationListBean> workStationList) {
        this.workStationList = workStationList;
    }

    public static class WorkStationListBean implements IPickerViewData {
        /**
         * id : 60a038dd0665473eb9c14ca17339668f
         * isNewRecord : false
         * workstationCode : 0
         * stationName : ycy5
         */

        private String id;
        private boolean isNewRecord;
        private int workstationCode;
        private String stationName;

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

        public int getWorkstationCode() {
            return workstationCode;
        }

        public void setWorkstationCode(int workstationCode) {
            this.workstationCode = workstationCode;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        @Override
        public String getPickerViewText() {
            return this.stationName;
        }
    }
}
