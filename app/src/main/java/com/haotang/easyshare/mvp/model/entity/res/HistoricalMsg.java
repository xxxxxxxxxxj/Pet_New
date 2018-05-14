package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/14 14:45
 */
public class HistoricalMsg {

    /**
     * code : 0
     * data : [[{"createTime":"05-11 18:47","source":1,"contentType":1,"content":"只是简单的将map的key value 按照 singleValueMap的顺序 连接起来,最终格式类似于 url 的queryString"},{"createTime":"05-11 18:47","source":2,"contentType":1,"content":"数名字param Name=name,param Value=zhangfei,那么返回值是 name=zhangfei"}],[{"createTime":"05-11 18:48","source":1,"contentType":1,"content":"如果singleValueMap有key 是null,将使用 StringUtils.EMPTY 进行拼接"}],[{"createTime":"05-11 18:48","source":2,"contentType":1,"content":"如果singleValueMap有value 是null,将使用 StringUtils.EMPTY 进行拼接"}]]
     * msg : 操作成功
     */

    private int code;
    private String msg;
    private List<List<DataBean>> data;

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

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createTime : 05-11 18:47
         * source : 1
         * contentType : 1
         * content : 只是简单的将map的key value 按照 singleValueMap的顺序 连接起来,最终格式类似于 url 的queryString
         */

        private String createTime;
        private int source;
        private int contentType;
        private String content;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
