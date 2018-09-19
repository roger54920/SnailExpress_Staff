package com.example.me_jie.snailexpress_staff.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by me-jie on 2017/4/23.
 * 收件
 */

public class RecipientsBean extends BaseBean {
    /**
     * Act : {"isNewRecord":true,"taskId":"3b6f04449bd049a6afab33ee55e6d6b6","taskName":"站长确认包裹入库","taskDefKey":"confirmInWorkstation","procInsId":"8b9a33e1f5b64d8a854cf8c56c4929b0","procDefId":"accept_process:32:c8c7c096241446b6aaed6160cc34c512","procDefKey":"accept_process","status":"todo","assignee":"41c9f18bc59140068c4297f24b30389d","vars":{"map":{"parameterMap":{"id":"3942c55f3af842f282fa27cfbaa0eb54","act.procInsId":"8b9a33e1f5b64d8a854cf8c56c4929b0","receiver":{"id":"afb819f533d843b0a483c59e2ff1daeb","phone":"18502340014"},"expressParcel.id":"1cb969c86b4d4b98b998a6b1f0347330","act.taskId":"43eecbff0145447fb0ff02822dca38ba"},"stationmasterId":"41c9f18bc59140068c4297f24b30389d","consumerId":"cd2d0097ccd24f24badba037b3989261","mobile":"18502340014"}},"todoTask":true,"finishTask":false,"durationTime":"","taskCreateDate":"2017-05-10 13:54:16"}
     * expressAccept : {"id":"3942c55f3af842f282fa27cfbaa0eb54","isNewRecord":false,"createDate":"2017-05-10 13:46:35","updateDate":"2017-05-10 13:54:17","expressParcel":{"id":"1cb969c86b4d4b98b998a6b1f0347330","isNewRecord":false,"createDate":"2017-05-10 13:46:35","updateDate":"2017-05-10 13:46:43","shipperCode":"ZTO","lgisticCode":"779057968433","photo":"1cb969c86b4d4b98b998a6b1f0347330.jpg"},"stationmaster":{"id":"41c9f18bc59140068c4297f24b30389d","isNewRecord":false,"loginFlag":"1","roleNames":""},"workstation":{"id":"a07a9224689f468495f0f56a5ebd280e","isNewRecord":false,"remarks":"河南-安阳-汤阴-瓦岗寨","createDate":"2017-04-28 14:19:00","updateDate":"2017-04-29 18:36:58","stationName":"瓦岗寨","provincialCode":{"id":"bee95f0b8487438bb4fcaf5a4ac3dcb3","isNewRecord":false,"name":"河南","sort":30,"ifChecked":"\u0000","parentId":"0"},"cityCode":{"id":"b59038bfd1804488b81e60158664ffa1","isNewRecord":false,"name":"安阳","sort":30,"ifChecked":"\u0000","parentId":"0"},"areaCode":{"id":"c8ac80c297ef4720840195736a334ae6","isNewRecord":false,"name":"汤阴","sort":30,"ifChecked":"\u0000","parentId":"0"},"cellCode":{"id":"8a245d30a8af47de8df8d7a4bac37357","isNewRecord":false,"name":"瓦岗寨","sort":30,"ifChecked":"\u0000","parentId":"0"},"workstationCode":"1493360340375"},"businessStatus":"快递派件中","receiver":{"id":"afb819f533d843b0a483c59e2ff1daeb","isNewRecord":false},"costBePaid":0,"procInsId":"8b9a33e1f5b64d8a854cf8c56c4929b0"}
     */

    private ActBean Act;
    /**
     * expressAccept : {"id":"2bdc36a7dc2d410cba0df9ac882b0f94","isNewRecord":false,"remarks":"送达时间  2017/05/22 13:20","createDate":"2017-05-22 13:14:53","updateDate":"2017-05-22 13:20:21","expressParcel":{"id":"982a7c1076a4412997b7a5e41032d53b","isNewRecord":false,"createDate":"2017-05-22 13:14:53","updateDate":"2017-05-22 13:20:31","commodity":{"id":"ffe94e9ba92045f0a5a68b351d9f5aa3","isNewRecord":false,"goodsPrice":0,"goodsquantity":0,"goodsVol":0},"shipperCode":"ZTO","lgisticCode":"779057968432","photo":"982a7c1076a4412997b7a5e41032d53b.jpg"},"consumer":{"id":"59be32ac1c1e4aacaffd837b637256fa","isNewRecord":false,"createDate":"2017-05-22 10:26:32","updateDate":"2017-05-22 10:26:32","loginName":"15811478673","name":"Bella","mobile":"15811478673","loginIp":"124.202.223.218","loginDate":"2017-05-22 12:34:28","loginFlag":"1","roleName":"consumer","oldLoginIp":"124.202.223.218","oldLoginDate":"2017-05-22 12:34:28","roleNames":"客户"},"stationmaster":{"id":"ab26f19a9a0a4872a49939614b4888c6","isNewRecord":false,"loginFlag":"1","roleNames":""},"courier":{"id":"629f6f520a7e42d3853e18b9e1f16684","isNewRecord":false,"loginFlag":"1","roleNames":""},"workstation":{"id":"3d64fc368bb945049d842f9a76e0a6e3","isNewRecord":false,"remarks":"北京-朝阳区-人民社区","createDate":"2017-05-19 11:13:18","updateDate":"2017-05-19 11:13:18","stationName":"人民社区","provincialCode":{"id":"25799e4a916b446e81fc95630d1249c9","isNewRecord":false,"name":"北京","sort":30,"ifChecked":"\u0000","parentId":"0"},"cityCode":{"id":"1c4f6592e7f443ca92f8b5140b606a39","isNewRecord":false,"name":"北京","sort":30,"ifChecked":"\u0000","parentId":"0"},"areaCode":{"id":"e40fc70fc4654e73b33cc65957a6fe2d","isNewRecord":false,"name":"朝阳区","sort":30,"ifChecked":"\u0000","parentId":"0"},"cellCode":{"id":"3b78ee4bc86b48a6997a98196c0828a3","isNewRecord":false,"name":"人民社区","sort":30,"ifChecked":"\u0000","parentId":"0"},"houseNumber":{"id":"ac52c588c9924f7280841811abf478b1","isNewRecord":false,"sort":30,"ifChecked":"\u0000","parentId":"0"},"unit":{"id":"8337c3f9835745138c60fced4907b39f","isNewRecord":false,"sort":30,"ifChecked":"\u0000","parentId":"0"},"room":{"id":"3b78ee4bc86b48a6997a98196c0828a3","isNewRecord":false,"sort":30,"ifChecked":"\u0000","parentId":"0"},"workstationCode":"1495163598034"},"businessStatus":"已送达","receiver":{"id":"2ecc816a660845fd8eed4758f39ce517","isNewRecord":false},"ifInWorkstation":"0","containerNO":"123456","costBePaid":0,"procInsId":"b2d566df291049329d3bb91dccc8222a"}
     */

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
         * taskId : 3b6f04449bd049a6afab33ee55e6d6b6
         * taskName : 站长确认包裹入库
         * taskDefKey : confirmInWorkstation
         * procInsId : 8b9a33e1f5b64d8a854cf8c56c4929b0
         * procDefId : accept_process:32:c8c7c096241446b6aaed6160cc34c512
         * procDefKey : accept_process
         * status : todo
         * assignee : 41c9f18bc59140068c4297f24b30389d
         * vars : {"map":{"parameterMap":{"id":"3942c55f3af842f282fa27cfbaa0eb54","act.procInsId":"8b9a33e1f5b64d8a854cf8c56c4929b0","receiver":{"id":"afb819f533d843b0a483c59e2ff1daeb","phone":"18502340014"},"expressParcel.id":"1cb969c86b4d4b98b998a6b1f0347330","act.taskId":"43eecbff0145447fb0ff02822dca38ba"},"stationmasterId":"41c9f18bc59140068c4297f24b30389d","consumerId":"cd2d0097ccd24f24badba037b3989261","mobile":"18502340014"}}
         * todoTask : true
         * finishTask : false
         * durationTime :
         * taskCreateDate : 2017-05-10 13:54:16
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

        public static class VarsBean {
            /**
             * map : {"parameterMap":{"id":"3942c55f3af842f282fa27cfbaa0eb54","act.procInsId":"8b9a33e1f5b64d8a854cf8c56c4929b0","receiver":{"id":"afb819f533d843b0a483c59e2ff1daeb","phone":"18502340014"},"expressParcel.id":"1cb969c86b4d4b98b998a6b1f0347330","act.taskId":"43eecbff0145447fb0ff02822dca38ba"},"stationmasterId":"41c9f18bc59140068c4297f24b30389d","consumerId":"cd2d0097ccd24f24badba037b3989261","mobile":"18502340014"}
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
                 * parameterMap : {"id":"3942c55f3af842f282fa27cfbaa0eb54","act.procInsId":"8b9a33e1f5b64d8a854cf8c56c4929b0","receiver":{"id":"afb819f533d843b0a483c59e2ff1daeb","phone":"18502340014"},"expressParcel.id":"1cb969c86b4d4b98b998a6b1f0347330","act.taskId":"43eecbff0145447fb0ff02822dca38ba"}
                 * stationmasterId : 41c9f18bc59140068c4297f24b30389d
                 * consumerId : cd2d0097ccd24f24badba037b3989261
                 * mobile : 18502340014
                 */

                private ParameterMapBean parameterMap;
                private String stationmasterId;
                private String consumerId;
                private String mobile;

                public ParameterMapBean getParameterMap() {
                    return parameterMap;
                }

                public void setParameterMap(ParameterMapBean parameterMap) {
                    this.parameterMap = parameterMap;
                }

                public String getStationmasterId() {
                    return stationmasterId;
                }

                public void setStationmasterId(String stationmasterId) {
                    this.stationmasterId = stationmasterId;
                }

                public String getConsumerId() {
                    return consumerId;
                }

                public void setConsumerId(String consumerId) {
                    this.consumerId = consumerId;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public static class ParameterMapBean {
                    /**
                     * id : 3942c55f3af842f282fa27cfbaa0eb54
                     * act.procInsId : 8b9a33e1f5b64d8a854cf8c56c4929b0
                     * receiver : {"id":"afb819f533d843b0a483c59e2ff1daeb","phone":"18502340014"}
                     * expressParcel.id : 1cb969c86b4d4b98b998a6b1f0347330
                     * act.taskId : 43eecbff0145447fb0ff02822dca38ba
                     */

                    private String id;
                    @SerializedName("act.procInsId")
                    private String _$ActProcInsId54; // FIXME check this code
                    private ReceiverBean receiver;
                    @SerializedName("expressParcel.id")
                    private String _$ExpressParcelId268; // FIXME check this code
                    @SerializedName("act.taskId")
                    private String _$ActTaskId65; // FIXME check this code

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String get_$ActProcInsId54() {
                        return _$ActProcInsId54;
                    }

                    public void set_$ActProcInsId54(String _$ActProcInsId54) {
                        this._$ActProcInsId54 = _$ActProcInsId54;
                    }

                    public ReceiverBean getReceiver() {
                        return receiver;
                    }

                    public void setReceiver(ReceiverBean receiver) {
                        this.receiver = receiver;
                    }

                    public String get_$ExpressParcelId268() {
                        return _$ExpressParcelId268;
                    }

                    public void set_$ExpressParcelId268(String _$ExpressParcelId268) {
                        this._$ExpressParcelId268 = _$ExpressParcelId268;
                    }

                    public String get_$ActTaskId65() {
                        return _$ActTaskId65;
                    }

                    public void set_$ActTaskId65(String _$ActTaskId65) {
                        this._$ActTaskId65 = _$ActTaskId65;
                    }

                    public static class ReceiverBean {
                        /**
                         * id : afb819f533d843b0a483c59e2ff1daeb
                         * phone : 18502340014
                         */

                        private String id;
                        private String phone;

                        public String getId() {
                            return id;
                        }

                        public void setId(String id) {
                            this.id = id;
                        }

                        public String getPhone() {
                            return phone;
                        }

                        public void setPhone(String phone) {
                            this.phone = phone;
                        }
                    }
                }
            }
        }
    }


    public static class RecipientsSaveBean {
        private String houseNumber;
        private String unit;
        private String room;
        private String id;
        private String phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
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
    }

    public static class ExpressAcceptBean {
        /**
         * id : 2bdc36a7dc2d410cba0df9ac882b0f94
         * isNewRecord : false
         * remarks : 送达时间  2017/05/22 13:20
         * createDate : 2017-05-22 13:14:53
         * updateDate : 2017-05-22 13:20:21
         * expressParcel : {"id":"982a7c1076a4412997b7a5e41032d53b","isNewRecord":false,"createDate":"2017-05-22 13:14:53","updateDate":"2017-05-22 13:20:31","commodity":{"id":"ffe94e9ba92045f0a5a68b351d9f5aa3","isNewRecord":false,"goodsPrice":0,"goodsquantity":0,"goodsVol":0},"shipperCode":"ZTO","lgisticCode":"779057968432","photo":"982a7c1076a4412997b7a5e41032d53b.jpg"}
         * consumer : {"id":"59be32ac1c1e4aacaffd837b637256fa","isNewRecord":false,"createDate":"2017-05-22 10:26:32","updateDate":"2017-05-22 10:26:32","loginName":"15811478673","name":"Bella","mobile":"15811478673","loginIp":"124.202.223.218","loginDate":"2017-05-22 12:34:28","loginFlag":"1","roleName":"consumer","oldLoginIp":"124.202.223.218","oldLoginDate":"2017-05-22 12:34:28","roleNames":"客户"}
         * stationmaster : {"id":"ab26f19a9a0a4872a49939614b4888c6","isNewRecord":false,"loginFlag":"1","roleNames":""}
         * courier : {"id":"629f6f520a7e42d3853e18b9e1f16684","isNewRecord":false,"loginFlag":"1","roleNames":""}
         * workstation : {"id":"3d64fc368bb945049d842f9a76e0a6e3","isNewRecord":false,"remarks":"北京-朝阳区-人民社区","createDate":"2017-05-19 11:13:18","updateDate":"2017-05-19 11:13:18","stationName":"人民社区","provincialCode":{"id":"25799e4a916b446e81fc95630d1249c9","isNewRecord":false,"name":"北京","sort":30,"ifChecked":"\u0000","parentId":"0"},"cityCode":{"id":"1c4f6592e7f443ca92f8b5140b606a39","isNewRecord":false,"name":"北京","sort":30,"ifChecked":"\u0000","parentId":"0"},"areaCode":{"id":"e40fc70fc4654e73b33cc65957a6fe2d","isNewRecord":false,"name":"朝阳区","sort":30,"ifChecked":"\u0000","parentId":"0"},"cellCode":{"id":"3b78ee4bc86b48a6997a98196c0828a3","isNewRecord":false,"name":"人民社区","sort":30,"ifChecked":"\u0000","parentId":"0"},"houseNumber":{"id":"ac52c588c9924f7280841811abf478b1","isNewRecord":false,"sort":30,"ifChecked":"\u0000","parentId":"0"},"unit":{"id":"8337c3f9835745138c60fced4907b39f","isNewRecord":false,"sort":30,"ifChecked":"\u0000","parentId":"0"},"room":{"id":"3b78ee4bc86b48a6997a98196c0828a3","isNewRecord":false,"sort":30,"ifChecked":"\u0000","parentId":"0"},"workstationCode":"1495163598034"}
         * businessStatus : 已送达
         * receiver : {"id":"2ecc816a660845fd8eed4758f39ce517","isNewRecord":false}
         * ifInWorkstation : 0
         * containerNO : 123456
         * costBePaid : 0
         * procInsId : b2d566df291049329d3bb91dccc8222a
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private ExpressParcelBean expressParcel;
        private ConsumerBean consumer;
        private StationmasterBean stationmaster;
        private CourierBean courier;
        private WorkstationBean workstation;
        private String businessStatus;
        private ReceiverBean receiver;
        private String ifInWorkstation;
        private String containerNO;
        private int costBePaid;
        private String procInsId;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public ConsumerBean getConsumer() {
            return consumer;
        }

        public void setConsumer(ConsumerBean consumer) {
            this.consumer = consumer;
        }

        public StationmasterBean getStationmaster() {
            return stationmaster;
        }

        public void setStationmaster(StationmasterBean stationmaster) {
            this.stationmaster = stationmaster;
        }

        public CourierBean getCourier() {
            return courier;
        }

        public void setCourier(CourierBean courier) {
            this.courier = courier;
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

        public String getProcInsId() {
            return procInsId;
        }

        public void setProcInsId(String procInsId) {
            this.procInsId = procInsId;
        }

        public static class ExpressParcelBean {
            /**
             * id : 982a7c1076a4412997b7a5e41032d53b
             * isNewRecord : false
             * createDate : 2017-05-22 13:14:53
             * updateDate : 2017-05-22 13:20:31
             * commodity : {"id":"ffe94e9ba92045f0a5a68b351d9f5aa3","isNewRecord":false,"goodsPrice":0,"goodsquantity":0,"goodsVol":0}
             * shipperCode : ZTO
             * lgisticCode : 779057968432
             * photo : 982a7c1076a4412997b7a5e41032d53b.jpg
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private String updateDate;
            private CommodityBean commodity;
            private String shipperCode;
            private String lgisticCode;
            private String photo;
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

            public CommodityBean getCommodity() {
                return commodity;
            }

            public void setCommodity(CommodityBean commodity) {
                this.commodity = commodity;
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

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
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

            public static class CommodityBean {
                /**
                 * id : ffe94e9ba92045f0a5a68b351d9f5aa3
                 * isNewRecord : false
                 * goodsPrice : 0
                 * goodsquantity : 0
                 * goodsVol : 0
                 */

                private String id;
                private boolean isNewRecord;
                private int goodsPrice;
                private int goodsquantity;
                private int goodsVol;

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
        }

        public static class ConsumerBean {
            /**
             * id : 59be32ac1c1e4aacaffd837b637256fa
             * isNewRecord : false
             * createDate : 2017-05-22 10:26:32
             * updateDate : 2017-05-22 10:26:32
             * loginName : 15811478673
             * name : Bella
             * mobile : 15811478673
             * loginIp : 124.202.223.218
             * loginDate : 2017-05-22 12:34:28
             * loginFlag : 1
             * roleName : consumer
             * oldLoginIp : 124.202.223.218
             * oldLoginDate : 2017-05-22 12:34:28
             * roleNames : 客户
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private String updateDate;
            private String loginName;
            private String name;
            private String mobile;
            private String loginIp;
            private String loginDate;
            private String loginFlag;
            private String roleName;
            private String oldLoginIp;
            private String oldLoginDate;
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

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
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

            public String getLoginIp() {
                return loginIp;
            }

            public void setLoginIp(String loginIp) {
                this.loginIp = loginIp;
            }

            public String getLoginDate() {
                return loginDate;
            }

            public void setLoginDate(String loginDate) {
                this.loginDate = loginDate;
            }

            public String getLoginFlag() {
                return loginFlag;
            }

            public void setLoginFlag(String loginFlag) {
                this.loginFlag = loginFlag;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public String getOldLoginIp() {
                return oldLoginIp;
            }

            public void setOldLoginIp(String oldLoginIp) {
                this.oldLoginIp = oldLoginIp;
            }

            public String getOldLoginDate() {
                return oldLoginDate;
            }

            public void setOldLoginDate(String oldLoginDate) {
                this.oldLoginDate = oldLoginDate;
            }

            public String getRoleNames() {
                return roleNames;
            }

            public void setRoleNames(String roleNames) {
                this.roleNames = roleNames;
            }
        }

        public static class StationmasterBean {
            /**
             * id : ab26f19a9a0a4872a49939614b4888c6
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

        public static class WorkstationBean {
            /**
             * "id": "f58d7bdc52164fc8a4092b0cb0bf60e9",
             * "isNewRecord": false,
             * "remarks": "北京市,市辖区,海淀区,人民社区,1号楼,1单元,101室",
             * "createDate": "2017-06-05 13:57:17",
             * "updateDate": "2017-06-05 13:57:17",
             * "stationName": "测试蜗牛站",
             * "workstationCode": "1496642237405",
             * "provincialCode": "110000",
             * "cityCode": "110100",
             * "areaCode": "110108",
             * "cellCode": "9704ad37861a4d9f944fc18c8970b560",
             * "houseNumber": "f984fba3c54646719fa19b93d57c22dc",
             * "unit": "ffe12e3e951d469da384de5ee58a4f92",
             * "room": "b27d4e44512d4bfc900c616ada979870"
             */
            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String createDate;
            private String updateDate;
            private String stationName;
            private String workstationCode;
            private String provincialCode;
            private String cityCode;
            private String areaCode;
            private String cellCode;
            private String houseNumber;
            private String unit;
            private String room;

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

            public String getStationName() {
                return stationName;
            }

            public void setStationName(String stationName) {
                this.stationName = stationName;
            }

            public String getWorkstationCode() {
                return workstationCode;
            }

            public void setWorkstationCode(String workstationCode) {
                this.workstationCode = workstationCode;
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
        }

        public static class ReceiverBean {
            /**
             * id : 2ecc816a660845fd8eed4758f39ce517
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
