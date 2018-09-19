package com.example.me_jie.snailexpress_staff.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * 地区选择
 */

public class AllRegionBean extends BaseBean{


    /**
     * area : [{"id":"25799e4a916b446e81fc95630d1249c9","isNewRecord":false,"remarks":"","createDate":"2017-04-14 10:12:36","updateDate":"2017-04-14 10:12:36","parentIds":"0,2d7682947153480fa1425d516334c1a0,","name":"北京","sort":30,"code":"7307fea3-23ef-486e-8db7-0e32aeeec464","type":"2","nodes":[{"id":"1c4f6592e7f443ca92f8b5140b606a39","isNewRecord":false,"remarks":"","createDate":"2017-04-18 20:02:25","updateDate":"2017-04-18 20:02:25","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,","name":"北京","sort":30,"code":"","type":"3","nodes":[{"id":"79e7aab3dc964909a720cb2e0c02f3fe","isNewRecord":false,"remarks":"","createDate":"2017-04-14 10:18:48","updateDate":"2017-04-18 20:04:09","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,","name":"丰台区","sort":30,"code":"6736fbe9-5893-40cd-afd5-99197b37b2a2","type":"4","parentId":"1c4f6592e7f443ca92f8b5140b606a39"}],"parentId":"25799e4a916b446e81fc95630d1249c9"}],"parentId":"2d7682947153480fa1425d516334c1a0"}]
     * status : 1
     */

    private List<AreaBean> area;


    public List<AreaBean> getArea() {
        return area;
    }

    public void setArea(List<AreaBean> area) {
        this.area = area;
    }


    public static class AreaBean implements IPickerViewData {
        /**
         * id : 25799e4a916b446e81fc95630d1249c9
         * isNewRecord : false
         * remarks :
         * createDate : 2017-04-14 10:12:36
         * updateDate : 2017-04-14 10:12:36
         * parentIds : 0,2d7682947153480fa1425d516334c1a0,
         * name : 北京
         * sort : 30
         * code : 7307fea3-23ef-486e-8db7-0e32aeeec464
         * type : 2
         * nodes : [{"id":"1c4f6592e7f443ca92f8b5140b606a39","isNewRecord":false,"remarks":"","createDate":"2017-04-18 20:02:25","updateDate":"2017-04-18 20:02:25","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,","name":"北京","sort":30,"code":"","type":"3","nodes":[{"id":"79e7aab3dc964909a720cb2e0c02f3fe","isNewRecord":false,"remarks":"","createDate":"2017-04-14 10:18:48","updateDate":"2017-04-18 20:04:09","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,","name":"丰台区","sort":30,"code":"6736fbe9-5893-40cd-afd5-99197b37b2a2","type":"4","parentId":"1c4f6592e7f443ca92f8b5140b606a39"}],"parentId":"25799e4a916b446e81fc95630d1249c9"}]
         * parentId : 2d7682947153480fa1425d516334c1a0
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

        @Override
        public String toString() {
            return "AreaBean{" +
                    "id='" + id + '\'' +
                    ", isNewRecord=" + isNewRecord +
                    ", remarks='" + remarks + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", updateDate='" + updateDate + '\'' +
                    ", parentIds='" + parentIds + '\'' +
                    ", name='" + name + '\'' +
                    ", sort=" + sort +
                    ", code='" + code + '\'' +
                    ", type='" + type + '\'' +
                    ", parentId='" + parentId + '\'' +
                    ", nodes=" + nodes +
                    '}';
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
             * id : 1c4f6592e7f443ca92f8b5140b606a39
             * isNewRecord : false
             * remarks :
             * createDate : 2017-04-18 20:02:25
             * updateDate : 2017-04-18 20:02:25
             * parentIds : 0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,
             * name : 北京
             * sort : 30
             * code :
             * type : 3
             * nodes : [{"id":"79e7aab3dc964909a720cb2e0c02f3fe","isNewRecord":false,"remarks":"","createDate":"2017-04-14 10:18:48","updateDate":"2017-04-18 20:04:09","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,","name":"丰台区","sort":30,"code":"6736fbe9-5893-40cd-afd5-99197b37b2a2","type":"4","parentId":"1c4f6592e7f443ca92f8b5140b606a39"}]
             * parentId : 25799e4a916b446e81fc95630d1249c9
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

            @Override
            public String toString() {
                return "NodesBeanX{" +
                        "id='" + id + '\'' +
                        ", isNewRecord=" + isNewRecord +
                        ", remarks='" + remarks + '\'' +
                        ", createDate='" + createDate + '\'' +
                        ", updateDate='" + updateDate + '\'' +
                        ", parentIds='" + parentIds + '\'' +
                        ", name='" + name + '\'' +
                        ", sort=" + sort +
                        ", code='" + code + '\'' +
                        ", type='" + type + '\'' +
                        ", parentId='" + parentId + '\'' +
                        ", nodes=" + nodes +
                        '}';
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
                 * id : 79e7aab3dc964909a720cb2e0c02f3fe
                 * isNewRecord : false
                 * remarks :
                 * createDate : 2017-04-14 10:18:48
                 * updateDate : 2017-04-18 20:04:09
                 * parentIds : 0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,
                 * name : 丰台区
                 * sort : 30
                 * code : 6736fbe9-5893-40cd-afd5-99197b37b2a2
                 * type : 4
                 * parentId : 1c4f6592e7f443ca92f8b5140b606a39
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

                @Override
                public String toString() {
                    return "NodesBean{" +
                            "id='" + id + '\'' +
                            ", isNewRecord=" + isNewRecord +
                            ", remarks='" + remarks + '\'' +
                            ", createDate='" + createDate + '\'' +
                            ", updateDate='" + updateDate + '\'' +
                            ", parentIds='" + parentIds + '\'' +
                            ", name='" + name + '\'' +
                            ", sort=" + sort +
                            ", code='" + code + '\'' +
                            ", type='" + type + '\'' +
                            ", parentId='" + parentId + '\'' +
                            '}';
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
