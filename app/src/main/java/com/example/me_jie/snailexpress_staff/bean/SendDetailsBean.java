package com.example.me_jie.snailexpress_staff.bean;

/**
 * Created by me-jie on 2017/5/14.
 * 寄件列表详情
 */

public class SendDetailsBean extends BaseBean{

    /**
     * mapsender : {"taskId":"ac19233e5eb44d4bb53fcc596bfa1e10","sendercity":"安阳","sendername":"同学咯咯哈","receivername":"同学咯咯哈","sendermobile":"17656568656","senderpro":"河南","rececity":"安阳","senderId":"1b2417c005404c908995f8e639188a28","taskKey":"outWorkstation","recearea":"汤阴","cost":"20.0000","receaddress":"汤阴","goodsname":"食品","recepro":"河南","containerNO":"","senderarea":"汤阴","ifInWorkstation":"1","receivermobile":"17656568656","procInsId":"1ac0011bbeb04a15aeefde19e8448e0f","shipperCode":"顺丰快递","createDate":"2017-05-16 16:53:24","goodsWeight":"125.0000","senderaddress":"汤阴"}
     */

    private MapsenderBean mapsender;

    public MapsenderBean getMapsender() {
        return mapsender;
    }

    public void setMapsender(MapsenderBean mapsender) {
        this.mapsender = mapsender;
    }

    public static class MapsenderBean {
        /**
         * taskId : ac19233e5eb44d4bb53fcc596bfa1e10
         * sendercity : 安阳
         * sendername : 同学咯咯哈
         * receivername : 同学咯咯哈
         * sendermobile : 17656568656
         * senderpro : 河南
         * rececity : 安阳
         * senderId : 1b2417c005404c908995f8e639188a28
         * taskKey : outWorkstation
         * recearea : 汤阴
         * cost : 20.0000
         * receaddress : 汤阴
         * goodsname : 食品
         * recepro : 河南
         * containerNO :
         * senderarea : 汤阴
         * ifInWorkstation : 1
         * receivermobile : 17656568656
         * procInsId : 1ac0011bbeb04a15aeefde19e8448e0f
         * shipperCode : 顺丰快递
         * createDate : 2017-05-16 16:53:24
         * goodsWeight : 125.0000
         * senderaddress : 汤阴
         */

        private String taskId;
        private String sendercity;
        private String sendername;
        private String receivername;
        private String sendermobile;
        private String senderpro;
        private String rececity;
        private String senderId;
        private String taskKey;
        private String recearea;
        private String cost;
        private String receaddress;
        private String goodsname;
        private String recepro;
        private String containerNO;
        private String senderarea;
        private String ifInWorkstation;
        private String receivermobile;
        private String procInsId;
        private String shipperCode;
        private String createDate;
        private String goodsWeight;
        private String senderaddress;
        private String twodcode;
        private String lgisticCode;
        private String workstationId;

        public String getWorkstationId() {
            return workstationId;
        }

        public void setWorkstationId(String workstationId) {
            this.workstationId = workstationId;
        }

        public String getLgisticCode() {
            return lgisticCode;
        }

        public void setLgisticCode(String lgisticCode) {
            this.lgisticCode = lgisticCode;
        }

        public String getTwodcode() {
            return twodcode;
        }

        public void setTwodcode(String twodcode) {
            this.twodcode = twodcode;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getSendercity() {
            return sendercity;
        }

        public void setSendercity(String sendercity) {
            this.sendercity = sendercity;
        }

        public String getSendername() {
            return sendername;
        }

        public void setSendername(String sendername) {
            this.sendername = sendername;
        }

        public String getReceivername() {
            return receivername;
        }

        public void setReceivername(String receivername) {
            this.receivername = receivername;
        }

        public String getSendermobile() {
            return sendermobile;
        }

        public void setSendermobile(String sendermobile) {
            this.sendermobile = sendermobile;
        }

        public String getSenderpro() {
            return senderpro;
        }

        public void setSenderpro(String senderpro) {
            this.senderpro = senderpro;
        }

        public String getRececity() {
            return rececity;
        }

        public void setRececity(String rececity) {
            this.rececity = rececity;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getTaskKey() {
            return taskKey;
        }

        public void setTaskKey(String taskKey) {
            this.taskKey = taskKey;
        }

        public String getRecearea() {
            return recearea;
        }

        public void setRecearea(String recearea) {
            this.recearea = recearea;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getReceaddress() {
            return receaddress;
        }

        public void setReceaddress(String receaddress) {
            this.receaddress = receaddress;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getRecepro() {
            return recepro;
        }

        public void setRecepro(String recepro) {
            this.recepro = recepro;
        }

        public String getContainerNO() {
            return containerNO;
        }

        public void setContainerNO(String containerNO) {
            this.containerNO = containerNO;
        }

        public String getSenderarea() {
            return senderarea;
        }

        public void setSenderarea(String senderarea) {
            this.senderarea = senderarea;
        }

        public String getIfInWorkstation() {
            return ifInWorkstation;
        }

        public void setIfInWorkstation(String ifInWorkstation) {
            this.ifInWorkstation = ifInWorkstation;
        }

        public String getReceivermobile() {
            return receivermobile;
        }

        public void setReceivermobile(String receivermobile) {
            this.receivermobile = receivermobile;
        }

        public String getProcInsId() {
            return procInsId;
        }

        public void setProcInsId(String procInsId) {
            this.procInsId = procInsId;
        }

        public String getShipperCode() {
            return shipperCode;
        }

        public void setShipperCode(String shipperCode) {
            this.shipperCode = shipperCode;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getGoodsWeight() {
            return goodsWeight;
        }

        public void setGoodsWeight(String goodsWeight) {
            this.goodsWeight = goodsWeight;
        }

        public String getSenderaddress() {
            return senderaddress;
        }

        public void setSenderaddress(String senderaddress) {
            this.senderaddress = senderaddress;
        }
    }
}
