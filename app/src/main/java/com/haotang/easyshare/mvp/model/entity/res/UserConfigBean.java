package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/26 09:52
 */
public class UserConfigBean {
    private int code;
    private UserConfig data;
    private String msg;

    public static class UserConfig{
        private String rewardTip;

        public String getRewardTip() {
            return rewardTip;
        }

        public void setRewardTip(String rewardTip) {
            this.rewardTip = rewardTip;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserConfig getData() {
        return data;
    }

    public void setData(UserConfig data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
