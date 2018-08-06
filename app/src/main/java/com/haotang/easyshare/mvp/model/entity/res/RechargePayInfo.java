package com.haotang.easyshare.mvp.model.entity.res;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/8/6 13:37
 */
public class RechargePayInfo {

    /**
     * code : 0
     * data : {"payInfo":{"package":"Sign=WXPay","appid":"wxdf9f25043651e1e9","sign":"3A117ECEE797A520A77A7313417F6173","partnerid":"1510561151","prepayid":"wx021033595722668eef4eab081664922534","noncestr":"AtfuNFDLYpzhCveJAYkZuOPBWvjfNFFo","timestamp":"1533177239"}}
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
         * payInfo : {"package":"Sign=WXPay","appid":"wxdf9f25043651e1e9","sign":"3A117ECEE797A520A77A7313417F6173","partnerid":"1510561151","prepayid":"wx021033595722668eef4eab081664922534","noncestr":"AtfuNFDLYpzhCveJAYkZuOPBWvjfNFFo","timestamp":"1533177239"}
         */

        private PayInfoBean payInfo;

        public PayInfoBean getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(PayInfoBean payInfo) {
            this.payInfo = payInfo;
        }

        public static class PayInfoBean {
            /**
             * package : Sign=WXPay
             * appid : wxdf9f25043651e1e9
             * sign : 3A117ECEE797A520A77A7313417F6173
             * partnerid : 1510561151
             * prepayid : wx021033595722668eef4eab081664922534
             * noncestr : AtfuNFDLYpzhCveJAYkZuOPBWvjfNFFo
             * timestamp : 1533177239
             */

            @SerializedName("package")
            private String packageX;
            private String appid;
            private String sign;
            private String partnerid;
            private String prepayid;
            private String noncestr;
            private String timestamp;
            private String orderStr;

            public String getOrderStr() {
                return orderStr;
            }

            public void setOrderStr(String orderStr) {
                this.orderStr = orderStr;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
