package com.haotang.easyshare.mvp.model.entity.res;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/14 19:54
 */
public class AdvertisementBean{

    /**
     * code : 0
     * data : [{"img":"http://img.sayiyinxiang.com/api/activity/imgs/fallout.jpg","display":2,"destination":"http://www.baidu.com"}]
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

    public static class DataBean implements Parcelable {
        /**
         * img : http://img.sayiyinxiang.com/api/activity/imgs/fallout.jpg
         * display : 2
         * destination : http://www.baidu.com
         */

        private String img;
        private int display;
        private String destination;
        private String title;

        public DataBean(String img, int display, String destination, String title) {
            this.img = img;
            this.display = display;
            this.destination = destination;
            this.title = title;
        }

        @Override
        public String   toString() {
            return "DataBean{" +
                    "img='" + img + '\'' +
                    ", display=" + display +
                    ", destination='" + destination + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getDisplay() {
            return display;
        }

        public void setDisplay(int display) {
            this.display = display;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel arg0, int arg1) {
            arg0.writeString(img);
            arg0.writeInt(display);
            arg0.writeString(destination);
            arg0.writeString(title);
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Creator(){

            @Override
            public DataBean createFromParcel(Parcel source) {
                DataBean p = new DataBean(source.readString(),source.readInt(),source.readString(),source.readString());
                return p;
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
