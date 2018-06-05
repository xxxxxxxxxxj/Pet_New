package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/15 09:51
 */
public class HotCarBean {

    /**
     * code : 0
     * data : [{"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15247102182087551003.jpg","id":3,"brand":"奥迪"},{"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15247102193930703996.jpg","id":8,"brand":"宝马"},{"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15247102195669853288.jpg","id":9,"brand":"保时捷"},{"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15247102309296309912.jpg","id":30,"brand":"长城"}]
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
         * icon : http://img.sayiyinxiang.com/api/brand/imgs/15247102182087551003.jpg
         * id : 3
         * brand : 奥迪
         */

        private String icon;
        private int id;
        private String brand;
        private String sortLetters;  //显示数据拼音的首字母
        private String pyhead;//字母缩写
        private List<HotSpecialCarBean.DataBean> carList;

        public List<HotSpecialCarBean.DataBean> getCarList() {
            return carList;
        }

        public void setCarList(List<HotSpecialCarBean.DataBean> carList) {
            this.carList = carList;
        }

        public String getSortLetters() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }

        public String getPyhead() {
            return pyhead;
        }

        public void setPyhead(String pyhead) {
            this.pyhead = pyhead;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }
}
