package com.example.me_jie.snailexpress_staff.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * 门牌号
 */

public class RecipientsTabletBean extends BaseBean {


    /**
     * status : 1
     * house : [{"id":"9bb134e5c640499fae323056a0b7e186","isNewRecord":false,"remarks":"","createDate":"2017-04-19 11:53:54","updateDate":"2017-04-19 11:53:54","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,ac8a0bd56495486484a9e45210976788,","name":"1楼","sort":30,"code":"1","type":"6","nodes":[{"id":"55bd815d11a34ac39838c09cb767b2c4","isNewRecord":false,"remarks":"","createDate":"2017-04-19 18:12:14","updateDate":"2017-04-19 18:12:14","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,ac8a0bd56495486484a9e45210976788,9bb134e5c640499fae323056a0b7e186,","name":"1单元","sort":30,"code":"1","type":"7","nodes":[{"id":"be39e0b992274447a8459100141d0eac","isNewRecord":false,"remarks":"","createDate":"2017-04-19 18:12:52","updateDate":"2017-04-19 18:16:24","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,ac8a0bd56495486484a9e45210976788,9bb134e5c640499fae323056a0b7e186,55bd815d11a34ac39838c09cb767b2c4,","name":"101","sort":30,"code":"101","type":"8","parentId":"55bd815d11a34ac39838c09cb767b2c4"}],"parentId":"9bb134e5c640499fae323056a0b7e186"}],"parentId":"ac8a0bd56495486484a9e45210976788"}]
     */

    private List<HouseBean> house;


    public List<HouseBean> getHouse() {
        return house;
    }

    public void setHouse(List<HouseBean> house) {
        this.house = house;
    }

    public static class HouseBean implements IPickerViewData {
        /**
         * id : 9bb134e5c640499fae323056a0b7e186
         * isNewRecord : false
         * remarks :
         * createDate : 2017-04-19 11:53:54
         * updateDate : 2017-04-19 11:53:54
         * parentIds : 0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,ac8a0bd56495486484a9e45210976788,
         * name : 1楼
         * sort : 30
         * code : 1
         * type : 6
         * nodes : [{"id":"55bd815d11a34ac39838c09cb767b2c4","isNewRecord":false,"remarks":"","createDate":"2017-04-19 18:12:14","updateDate":"2017-04-19 18:12:14","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,ac8a0bd56495486484a9e45210976788,9bb134e5c640499fae323056a0b7e186,","name":"1单元","sort":30,"code":"1","type":"7","nodes":[{"id":"be39e0b992274447a8459100141d0eac","isNewRecord":false,"remarks":"","createDate":"2017-04-19 18:12:52","updateDate":"2017-04-19 18:16:24","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,ac8a0bd56495486484a9e45210976788,9bb134e5c640499fae323056a0b7e186,55bd815d11a34ac39838c09cb767b2c4,","name":"101","sort":30,"code":"101","type":"8","parentId":"55bd815d11a34ac39838c09cb767b2c4"}],"parentId":"9bb134e5c640499fae323056a0b7e186"}]
         * parentId : ac8a0bd56495486484a9e45210976788
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String parentIds;
        private String name;
        private int sort;
        private String code;
        private String type;
        private String parentId;
        private List<NodesBeanX> nodes;

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

        public String getParentIds() {
            return parentIds;
        }

        public void setParentIds(String parentIds) {
            this.parentIds = parentIds;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public List<NodesBeanX> getNodes() {
            return nodes;
        }

        public void setNodes(List<NodesBeanX> nodes) {
            this.nodes = nodes;
        }

        @Override
        public String getPickerViewText() {
            return this.name;
        }

        public static class NodesBeanX {
            /**
             * id : 55bd815d11a34ac39838c09cb767b2c4
             * isNewRecord : false
             * remarks :
             * createDate : 2017-04-19 18:12:14
             * updateDate : 2017-04-19 18:12:14
             * parentIds : 0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,ac8a0bd56495486484a9e45210976788,9bb134e5c640499fae323056a0b7e186,
             * name : 1单元
             * sort : 30
             * code : 1
             * type : 7
             * nodes : [{"id":"be39e0b992274447a8459100141d0eac","isNewRecord":false,"remarks":"","createDate":"2017-04-19 18:12:52","updateDate":"2017-04-19 18:16:24","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,ac8a0bd56495486484a9e45210976788,9bb134e5c640499fae323056a0b7e186,55bd815d11a34ac39838c09cb767b2c4,","name":"101","sort":30,"code":"101","type":"8","parentId":"55bd815d11a34ac39838c09cb767b2c4"}]
             * parentId : 9bb134e5c640499fae323056a0b7e186
             */

            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String createDate;
            private String updateDate;
            private String parentIds;
            private String name;
            private int sort;
            private String code;
            private String type;
            private String parentId;
            private List<NodesBean> nodes;

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

            public String getParentIds() {
                return parentIds;
            }

            public void setParentIds(String parentIds) {
                this.parentIds = parentIds;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public List<NodesBean> getNodes() {
                return nodes;
            }

            public void setNodes(List<NodesBean> nodes) {
                this.nodes = nodes;
            }

            public static class NodesBean {
                /**
                 * id : be39e0b992274447a8459100141d0eac
                 * isNewRecord : false
                 * remarks :
                 * createDate : 2017-04-19 18:12:52
                 * updateDate : 2017-04-19 18:16:24
                 * parentIds : 0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,ac8a0bd56495486484a9e45210976788,9bb134e5c640499fae323056a0b7e186,55bd815d11a34ac39838c09cb767b2c4,
                 * name : 101
                 * sort : 30
                 * code : 101
                 * type : 8
                 * parentId : 55bd815d11a34ac39838c09cb767b2c4
                 */

                private String id;
                private boolean isNewRecord;
                private String remarks;
                private String createDate;
                private String updateDate;
                private String parentIds;
                private String name;
                private int sort;
                private String code;
                private String type;
                private String parentId;

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

                public String getParentIds() {
                    return parentIds;
                }

                public void setParentIds(String parentIds) {
                    this.parentIds = parentIds;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getParentId() {
                    return parentId;
                }

                public void setParentId(String parentId) {
                    this.parentId = parentId;
                }
            }
        }
    }
}
