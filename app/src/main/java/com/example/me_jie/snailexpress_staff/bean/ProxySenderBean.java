package com.example.me_jie.snailexpress_staff.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by me-jie on 2017/7/4.
 * 寄件相关信息
 */

public class ProxySenderBean extends BaseBean {
    private List<CacompanyBean> cacompany;
    private List<WeightpriceBean> weightprice;
    private List<WorksStrBean> worksStr;
    private List<GoodsTypeListBean> goodsTypeList;

    public List<CacompanyBean> getCacompany() {
        return cacompany;
    }

    public void setCacompany(List<CacompanyBean> cacompany) {
        this.cacompany = cacompany;
    }

    public List<WeightpriceBean> getWeightprice() {
        return weightprice;
    }

    public void setWeightprice(List<WeightpriceBean> weightprice) {
        this.weightprice = weightprice;
    }

    public List<WorksStrBean> getWorksStr() {
        return worksStr;
    }

    public void setWorksStr(List<WorksStrBean> worksStr) {
        this.worksStr = worksStr;
    }

    public List<GoodsTypeListBean> getGoodsTypeList() {
        return goodsTypeList;
    }

    public void setGoodsTypeList(List<GoodsTypeListBean> goodsTypeList) {
        this.goodsTypeList = goodsTypeList;
    }

    public static class CacompanyBean implements IPickerViewData{
        /**
         * id : 889e773a0b47414797d744fe8f632026
         * remarks :
         * createDate : 2017-04-30 09:07:54
         * updateDate : 2017-05-16 18:25:56
         * value : emsfedex
         * label : 中通快递
         * type : sender_carrier_company
         * description : 承运公司
         * sort : 10
         * parentId : 0
         */

        private String id;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String value;
        private String label;
        private String type;
        private String description;
        private int sort;
        private String parentId;

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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String getPickerViewText() {
            return this.getLabel();
        }
    }

    public static class WeightpriceBean {
        /**
         * id : 3d5ef6e1ffce49e3b5e944d60500b935
         * remarks :
         * createDate : 2017-04-27 16:57:36
         * updateDate : 2017-04-27 16:59:42
         * value : one
         * label : 0.7
         * type : sender_weight_price
         * description : 称重首次价格
         * sort : 10
         * parentId : 0
         */

        private String id;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String value;
        private String label;
        private String type;
        private String description;
        private int sort;
        private String parentId;

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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }

    public static class WorksStrBean {
        /**
         * id : d30f0cff52a543ce91c296afeb8befe7
         * createDate : 2017-06-27 12:10:37
         * updateDate : 2017-06-27 12:13:20
         * stationName : 站点111
         * workstationCode : 1498536636827
         * provincialCode : 110000
         * cityCode : 110100
         * areaCode : 110108
         * cellCode : 0e3c1dabe5d548489977005af69c4992
         * houseNumber : 96a1892a2490408bb365ae6918f7cc34
         * unit : 8bdd428194fb4e478ac4e6b2d9f565e4
         * room : 099c4d770bc64bf4aa973d2c6a9bdee9
         */

        private String id;
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

    public static class GoodsTypeListBean implements IPickerViewData{
        /**
         * id : 3fad0e7ba974452faef48452df49f046
         * remarks :
         * createDate : 2017-04-27 15:27:52
         * updateDate : 2017-05-16 20:15:34
         * value : commodity
         * label : 日用品
         * type : sender_goods_type
         * description : 物品类型
         * sort : 10
         * parentId : 0
         */

        private String id;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String value;
        private String label;
        private String type;
        private String description;
        private int sort;
        private String parentId;

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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String getPickerViewText() {
            return this.getLabel();
        }
    }
}
