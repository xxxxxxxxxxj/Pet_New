package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 10:01
 */
public class RechargeTemp {

    /**
     * code : 0
     * data : [{"bonus":30,"createTime":"2018-07-31 14:47:20","headImg":"","id":1,"price":0.03,"sort":1,"state":1,"title":"充30"},{"bonus":50,"createTime":"2018-07-31 14:47:36","headImg":"","id":2,"price":0.05,"sort":1,"state":1,"title":"充50"},{"bonus":100,"createTime":"2018-07-31 14:47:48","headImg":"http://timg.sayiyinxiang.com/api/recharge/imgs/2018061911502134567.png","id":3,"price":0.1,"sort":1,"state":1,"title":"充100"},{"bonus":200,"createTime":"2018-07-31 14:47:54","headImg":"","id":4,"price":0.2,"sort":1,"state":1,"title":"充200"},{"bonus":500,"createTime":"2018-07-31 14:48:10","headImg":"","id":5,"price":0.5,"sort":1,"state":1,"title":"充500"},{"bonus":1000,"createTime":"2018-07-31 14:48:37","headImg":"","id":6,"price":1,"sort":1,"state":1,"title":"充1000"}]
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
         * bonus : 30
         * createTime : 2018-07-31 14:47:20
         * headImg :
         * id : 1
         * price : 0.03
         * sort : 1
         * state : 1
         * title : 充30
         */

        private int bonus;
        private String createTime;
        private String headImg;
        private int id;
        private double price;
        private int sort;
        private int state;
        private String title;
        private boolean select;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public int getBonus() {
            return bonus;
        }

        public void setBonus(int bonus) {
            this.bonus = bonus;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
