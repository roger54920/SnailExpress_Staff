package com.example.me_jie.snailexpress_staff.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by me-jie on 2017/4/27.
 * 快递扫码信息
 */

public class CourierMessageBean extends BaseBean{
    /**
     * Act : {"isNewRecord":true,"taskId":"7270ff729e874feda38b93a69bf38961","taskName":"站长上传快递照片","taskDefKey":"sid-4EBA519B-C7C3-43FB-9685-7DB0CEFC4A52","procInsId":"08528e2a9b8341cb851911957c1f14d4","procDefId":"accept_process:27:a6b166a42e594bdd82181f403a6b2151","procDefKey":"accept_process","status":"todo","assignee":"b00a3a5e3811409eb392cf8498fc70b5","vars":{"map":{"stationmasterId":"b00a3a5e3811409eb392cf8498fc70b5"}},"todoTask":true,"finishTask":false,"durationTime":"","taskCreateDate":"2017-05-05 18:02:56","procDefName":"收件业务"}
     * expressAccept : {"id":"cc301b1ac9da4ff8afb12ecd7349003c","isNewRecord":false,"createDate":"2017-05-05 18:02:54","updateDate":"2017-05-05 18:02:54","expressParcel":{"id":"d72b40e08b454ca080f4730f29f98285","isNewRecord":false,"createDate":"2017-05-05 18:02:53","updateDate":"2017-05-05 18:02:53","shipperCode":"ZTO","lgisticCode":"779057968432"},"stationmaster":{"id":"b00a3a5e3811409eb392cf8498fc70b5","isNewRecord":false,"loginFlag":"1","roleNames":""},"workstation":{"id":"a07a9224689f468495f0f56a5ebd280e","isNewRecord":false},"businessStatus":"30","costBePaid":0,"procInsId":"08528e2a9b8341cb851911957c1f14d4"}
     */

    private ActBean Act;
    private ExpressAcceptBean expressAccept;

    public ActBean getAct() {
        return Act;
    }

    public void setAct(ActBean Act) {
        this.Act = Act;
    }

    public ExpressAcceptBean getExpressAccept() {
        return expressAccept;
    }

    public void setExpressAccept(ExpressAcceptBean expressAccept) {
        this.expressAccept = expressAccept;
    }

    public static class ActBean {
        /**
         * isNewRecord : true
         * taskId : 7270ff729e874feda38b93a69bf38961
         * taskName : 站长上传快递照片
         * taskDefKey : sid-4EBA519B-C7C3-43FB-9685-7DB0CEFC4A52
         * procInsId : 08528e2a9b8341cb851911957c1f14d4
         * procDefId : accept_process:27:a6b166a42e594bdd82181f403a6b2151
         * procDefKey : accept_process
         * status : todo
         * assignee : b00a3a5e3811409eb392cf8498fc70b5
         * vars : {"map":{"stationmasterId":"b00a3a5e3811409eb392cf8498fc70b5"}}
         * todoTask : true
         * finishTask : false
         * durationTime :
         * taskCreateDate : 2017-05-05 18:02:56
         * procDefName : 收件业务
         */

        private boolean isNewRecord;
        private String taskId;
        private String taskName;
        private String taskDefKey;
        private String procInsId;
        private String procDefId;
        private String procDefKey;
        @SerializedName("status")
        private String statusX;
        private String assignee;
        private VarsBean vars;
        private boolean todoTask;
        private boolean finishTask;
        private String durationTime;
        private String taskCreateDate;
        private String procDefName;

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskDefKey() {
            return taskDefKey;
        }

        public void setTaskDefKey(String taskDefKey) {
            this.taskDefKey = taskDefKey;
        }

        public String getProcInsId() {
            return procInsId;
        }

        public void setProcInsId(String procInsId) {
            this.procInsId = procInsId;
        }

        public String getProcDefId() {
            return procDefId;
        }

        public void setProcDefId(String procDefId) {
            this.procDefId = procDefId;
        }

        public String getProcDefKey() {
            return procDefKey;
        }

        public void setProcDefKey(String procDefKey) {
            this.procDefKey = procDefKey;
        }

        public String getStatusX() {
            return statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }

        public VarsBean getVars() {
            return vars;
        }

        public void setVars(VarsBean vars) {
            this.vars = vars;
        }

        public boolean isTodoTask() {
            return todoTask;
        }

        public void setTodoTask(boolean todoTask) {
            this.todoTask = todoTask;
        }

        public boolean isFinishTask() {
            return finishTask;
        }

        public void setFinishTask(boolean finishTask) {
            this.finishTask = finishTask;
        }

        public String getDurationTime() {
            return durationTime;
        }

        public void setDurationTime(String durationTime) {
            this.durationTime = durationTime;
        }

        public String getTaskCreateDate() {
            return taskCreateDate;
        }

        public void setTaskCreateDate(String taskCreateDate) {
            this.taskCreateDate = taskCreateDate;
        }

        public String getProcDefName() {
            return procDefName;
        }

        public void setProcDefName(String procDefName) {
            this.procDefName = procDefName;
        }

        public static class VarsBean {
            /**
             * map : {"stationmasterId":"b00a3a5e3811409eb392cf8498fc70b5"}
             */

            private MapBean map;

            public MapBean getMap() {
                return map;
            }

            public void setMap(MapBean map) {
                this.map = map;
            }

            public static class MapBean {
                /**
                 * stationmasterId : b00a3a5e3811409eb392cf8498fc70b5
                 */

                private String stationmasterId;

                public String getStationmasterId() {
                    return stationmasterId;
                }

                public void setStationmasterId(String stationmasterId) {
                    this.stationmasterId = stationmasterId;
                }
            }
        }
    }

    public static class ExpressAcceptBean {
        /**
         * id : cc301b1ac9da4ff8afb12ecd7349003c
         * isNewRecord : false
         * createDate : 2017-05-05 18:02:54
         * updateDate : 2017-05-05 18:02:54
         * expressParcel : {"id":"d72b40e08b454ca080f4730f29f98285","isNewRecord":false,"createDate":"2017-05-05 18:02:53","updateDate":"2017-05-05 18:02:53","shipperCode":"ZTO","lgisticCode":"779057968432"}
         * stationmaster : {"id":"b00a3a5e3811409eb392cf8498fc70b5","isNewRecord":false,"loginFlag":"1","roleNames":""}
         * workstation : {"id":"a07a9224689f468495f0f56a5ebd280e","isNewRecord":false}
         * businessStatus : 30
         * costBePaid : 0
         * procInsId : 08528e2a9b8341cb851911957c1f14d4
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private ExpressParcelBean expressParcel;
        private StationmasterBean stationmaster;
        private WorkstationBean workstation;
        private String businessStatus;
        private int costBePaid;
        private String procInsId;

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

        public int getCostBePaid() {
            return costBePaid;
        }

        public void setCostBePaid(int costBePaid) {
            this.costBePaid = costBePaid;
        }

        public String getProcInsId() {
            return procInsId;
        }

        public void setProcInsId(String procInsId) {
            this.procInsId = procInsId;
        }

        public static class ExpressParcelBean {
            /**
             * id : d72b40e08b454ca080f4730f29f98285
             * isNewRecord : false
             * createDate : 2017-05-05 18:02:53
             * updateDate : 2017-05-05 18:02:53
             * shipperCode : ZTO
             * lgisticCode : 779057968432
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private String updateDate;
            private String shipperCode;
            private String lgisticCode;

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

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
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
        }

        public static class StationmasterBean {
            /**
             * id : b00a3a5e3811409eb392cf8498fc70b5
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
    }
}
