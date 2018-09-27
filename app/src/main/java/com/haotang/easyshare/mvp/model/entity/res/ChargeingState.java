package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/8/8 15:13
 */
public class ChargeingState {

    /**
     * code : 0
     * data : {"current":"33","totalServiceFee":"33","totalPowerPrice":"33","totalPrice":"33","totalPower":"33","totalTime":"33","endCode":"33133","soc":"30%","power":"33","provider":"3"}
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
        /**
         * current : 33
         * totalServiceFee : 33
         * totalPowerPrice : 33
         * totalPrice : 33
         * totalPower : 33
         * totalTime : 33
         * endCode : 33133
         * soc : 30%
         * power : 33
         * provider : 3
         */

        private String current;
        private String totalServiceFee;
        private String totalPowerPrice;
        private String totalPrice;
        private String totalPower;
        private String totalTime;
        private String endCode;
        private String soc;
        private String power;
        private String provider;
        private String providerName;

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public String getTotalServiceFee() {
            return totalServiceFee;
        }

        public void setTotalServiceFee(String totalServiceFee) {
            this.totalServiceFee = totalServiceFee;
        }

        public String getTotalPowerPrice() {
            return totalPowerPrice;
        }

        public void setTotalPowerPrice(String totalPowerPrice) {
            this.totalPowerPrice = totalPowerPrice;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getTotalPower() {
            return totalPower;
        }

        public void setTotalPower(String totalPower) {
            this.totalPower = totalPower;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }

        public String getEndCode() {
            return endCode;
        }

        public void setEndCode(String endCode) {
            this.endCode = endCode;
        }

        public String getSoc() {
            return soc;
        }

        public void setSoc(String soc) {
            this.soc = soc;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }
    }
}
