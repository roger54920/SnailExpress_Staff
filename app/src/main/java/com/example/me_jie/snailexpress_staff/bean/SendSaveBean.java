package com.example.me_jie.snailexpress_staff.bean;

/**
 * Created by me-jie on 2017/7/3.
 */

public class SendSaveBean {
    private String receiverprovincename;//收件地址省份
    private String receivercityname;//收件地址城市
    private String receiverexpareaname;//收件地址区

    public String getReceiverprovincename() {
        return receiverprovincename;
    }

    public void setReceiverprovincename(String receiverprovincename) {
        this.receiverprovincename = receiverprovincename;
    }

    public String getReceivercityname() {
        return receivercityname;
    }

    public void setReceivercityname(String receivercityname) {
        this.receivercityname = receivercityname;
    }

    public String getReceiverexpareaname() {
        return receiverexpareaname;
    }

    public void setReceiverexpareaname(String receiverexpareaname) {
        this.receiverexpareaname = receiverexpareaname;
    }
    /**
     * 保存的参数
     */
    public static class SendSaveParameter{
        private String worksId;// 站的ID
        private String  receivername ;//收件人名称
        private String sendername;//  寄件人名称
        private String  receivermoblie;// 收件手机号码
        private String sendermoblie;//  寄件人手机号码
        private String  receiverprovincename;// 收件地址省份
        private String receivercityname;//  收件地址城市
        private String receiverexpareaname;// 收件地址区
        private String receiveraddress;//  收件详细地址
        private String senderprovincename;// 寄件地址省份
        private String sendercityname;//  寄件地址城市
        private String senderexpareaname;// 寄件地址区
        private String senderaddress;//  寄件详细地址
        private String  goodsname;// 商品类型
        private String shippercode;// 承运公司
        private String source_sender;//返回地址 寄件
        private String source_accept;//收件

        public String getSource_sender() {
            return source_sender;
        }

        public void setSource_sender(String source_sender) {
            this.source_sender = source_sender;
        }

        public String getSource_accept() {
            return source_accept;
        }

        public void setSource_accept(String source_accept) {
            this.source_accept = source_accept;
        }

        public String getWorksId() {
            return worksId;
        }

        public void setWorksId(String worksId) {
            this.worksId = worksId;
        }

        public String getReceivername() {
            return receivername;
        }

        public void setReceivername(String receivername) {
            this.receivername = receivername;
        }

        public String getSendername() {
            return sendername;
        }

        public void setSendername(String sendername) {
            this.sendername = sendername;
        }

        public String getReceivermoblie() {
            return receivermoblie;
        }

        public void setReceivermoblie(String receivermoblie) {
            this.receivermoblie = receivermoblie;
        }

        public String getSendermoblie() {
            return sendermoblie;
        }

        public void setSendermoblie(String sendermoblie) {
            this.sendermoblie = sendermoblie;
        }

        public String getReceiverprovincename() {
            return receiverprovincename;
        }

        public void setReceiverprovincename(String receiverprovincename) {
            this.receiverprovincename = receiverprovincename;
        }

        public String getReceivercityname() {
            return receivercityname;
        }

        public void setReceivercityname(String receivercityname) {
            this.receivercityname = receivercityname;
        }

        public String getReceiverexpareaname() {
            return receiverexpareaname;
        }

        public void setReceiverexpareaname(String receiverexpareaname) {
            this.receiverexpareaname = receiverexpareaname;
        }

        public String getReceiveraddress() {
            return receiveraddress;
        }

        public void setReceiveraddress(String receiveraddress) {
            this.receiveraddress = receiveraddress;
        }

        public String getSenderprovincename() {
            return senderprovincename;
        }

        public void setSenderprovincename(String senderprovincename) {
            this.senderprovincename = senderprovincename;
        }

        public String getSendercityname() {
            return sendercityname;
        }

        public void setSendercityname(String sendercityname) {
            this.sendercityname = sendercityname;
        }

        public String getSenderexpareaname() {
            return senderexpareaname;
        }

        public void setSenderexpareaname(String senderexpareaname) {
            this.senderexpareaname = senderexpareaname;
        }

        public String getSenderaddress() {
            return senderaddress;
        }

        public void setSenderaddress(String senderaddress) {
            this.senderaddress = senderaddress;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getShippercode() {
            return shippercode;
        }

        public void setShippercode(String shippercode) {
            this.shippercode = shippercode;
        }
    }
}

