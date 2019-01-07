package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/3 14:23
 */
public class PostBean {

    /**
     * code : 0
     * data : [{"visitors":0,"carPrice":"198.8-209.8万元","comments":0,"carName":"宝马i8新能源","carIcon":"http://img.sayiyinxiang.com/api/brand/imgs/15246549073652388430.jpg","createTime":"2018-05-11 14:49","icon":"http://img.sayiyinxiang.com/api/brand/imgs/15260213587555911563.jpg","title":"《战神》更新🐑拍照模式 自带🆚滤镜还可调整表情","userName":"奎托斯","uuid":"b546948230e84e989b9ca19e061c49b3","praises":0,"carId":31}]
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
         * visitors : 0
         * carPrice : 198.8-209.8万元
         * comments : 0
         * carName : 宝马i8新能源
         * carIcon : http://img.sayiyinxiang.com/api/brand/imgs/15246549073652388430.jpg
         * createTime : 2018-05-11 14:49
         * icon : http://img.sayiyinxiang.com/api/brand/imgs/15260213587555911563.jpg
         * title : 《战神》更新🐑拍照模式 自带🆚滤镜还可调整表情
         * userName : 奎托斯
         * uuid : b546948230e84e989b9ca19e061c49b3
         * praises : 0
         * carId : 31
         */

        private int visitors;
        private String carPrice;
        private int comments;
        private String carName;
        private String carIcon;
        private String createTime;
        private String icon;
        private String title;
        private String userName;
        private String uuid;
        private int praises;
        private int carId;
        private int isPraise;
        private ShareMap shareMap;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public ShareMap getShareMap() {
            return shareMap;
        }

        public void setShareMap(ShareMap shareMap) {
            this.shareMap = shareMap;
        }

        public static class ShareMap {
            private String img;
            private String title;
            private String content;
            private String url;
            private String desc;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public int getIsPraise() {
            return isPraise;
        }

        public void setIsPraise(int isPraise) {
            this.isPraise = isPraise;
        }

        public int getVisitors() {
            return visitors;
        }

        public void setVisitors(int visitors) {
            this.visitors = visitors;
        }

        public String getCarPrice() {
            return carPrice;
        }

        public void setCarPrice(String carPrice) {
            this.carPrice = carPrice;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getCarIcon() {
            return carIcon;
        }

        public void setCarIcon(String carIcon) {
            this.carIcon = carIcon;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getPraises() {
            return praises;
        }

        public void setPraises(int praises) {
            this.praises = praises;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }
    }
}
