package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/3 17:57
 */
public class CollectChargeBean {

    /**
     * code : 0
     * data : [{"serviceFee":2,"headImg":"15258565092619189712.jpg","price":"0.08","title":"远大路1号院A区自用充电桩","uuid":"adb9c744e282402d892db8d8d6de9b48"},{"headImg":"http://static.evyou.cc/imges/carrierimg/nanfang.jpg","price":"未知","title":"霞山供电局充电点","uuid":"027d128a19424121b12d4f7d8d2c37ae"}]
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
         * serviceFee : 2
         * headImg : 15258565092619189712.jpg
         * price : 0.08
         * title : 远大路1号院A区自用充电桩
         * uuid : adb9c744e282402d892db8d8d6de9b48
         */

        private String serviceFee;
        private String headImg;
        private String price;
        private String title;
        private String uuid;

        public String getServiceFee() {
            return serviceFee;
        }

        public void setServiceFee(String serviceFee) {
            this.serviceFee = serviceFee;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
