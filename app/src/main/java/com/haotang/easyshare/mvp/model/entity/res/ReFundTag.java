package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/19 11:58
 */
public class ReFundTag {

    /**
     * code : 0
     * data : [{"content":"扫码困难","createTime":"2018-08-12 12:58:32","id":2,"parentId":1,"sort":1,"state":1},{"content":"多次启动失效","createTime":"2018-08-12 12:58:32","id":3,"parentId":1,"sort":2,"state":1},{"content":"支持的桩较少","createTime":"2018-08-12 12:58:32","id":4,"parentId":1,"sort":3,"state":1},{"content":"体验不好","createTime":"2018-08-12 12:58:32","id":5,"parentId":1,"sort":4,"state":1}]
     * msg : 操作成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : 扫码困难
         * createTime : 2018-08-12 12:58:32
         * id : 2
         * parentId : 1
         * sort : 1
         * state : 1
         */

        private String content;
        private String createTime;
        private int id;
        private int parentId;
        private int sort;
        private int state;
        private boolean select;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
