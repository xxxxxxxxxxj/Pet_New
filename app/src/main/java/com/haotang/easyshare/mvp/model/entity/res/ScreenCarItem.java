package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/18 21:55
 */
public class ScreenCarItem {

    /**
     * code : 0
     * data : {"batteryLifes":[{"name":"100公里以下","value":"0-100"},{"name":"100-200公里","value":"100-200"},{"name":"200-300公里","value":"200-300"},{"name":"300-400公里","value":"300-400"},{"name":"400公里以上","value":"400"}],"cars":[{"name":"微型车","value":"1"},{"name":"小型车","value":"2"},{"name":"紧凑型","value":"3"},{"name":"中型车","value":"4"},{"name":"中大型车","value":"5"},{"name":"大型车","value":"6"},{"name":"跑车","value":"7"},{"name":"MPV","value":"8"},{"name":"SUV","value":"9"},{"name":"微面","value":"10"},{"name":"微卡","value":"11"},{"name":"轻客","value":"12"},{"name":"皮卡","value":"13"}],"prices":[{"name":"0-5万","value":"0-5"},{"name":"5-8万","value":"5-8"},{"name":"8-15万","value":"8-15"},{"name":"15-20万","value":"15-20"},{"name":"20-30万","value":"20-30"},{"name":"30-50万","value":"30-50"},{"name":"50-70万","value":"50-70"},{"name":"70-100万","value":"70-100"},{"name":"100万以上","value":"100"}]}
     * msg : 操作成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<BatteryLifesBean> batteryLifes;
        private List<CarsBean> cars;
        private List<PricesBean> prices;

        public List<BatteryLifesBean> getBatteryLifes() {
            return batteryLifes;
        }

        public void setBatteryLifes(List<BatteryLifesBean> batteryLifes) {
            this.batteryLifes = batteryLifes;
        }

        public List<CarsBean> getCars() {
            return cars;
        }

        public void setCars(List<CarsBean> cars) {
            this.cars = cars;
        }

        public List<PricesBean> getPrices() {
            return prices;
        }

        public void setPrices(List<PricesBean> prices) {
            this.prices = prices;
        }

        public static class BatteryLifesBean {
            /**
             * name : 100公里以下
             * value : 0-100
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class CarsBean {
            /**
             * name : 微型车
             * value : 1
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class PricesBean {
            /**
             * name : 0-5万
             * value : 0-5
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
