package com.haotang.easyshare.mvp.model.imodel;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:53
 */
public interface ILoginModel extends IBaseModel {
    /**
     * 下发验证码
     */
    Observable sendVerifyCode(Map<String, String> headers, String phone);

    /**
     * 登陆
     */
    Observable login(Map<String, String> headers,String phone, String wxOpenId, double lng, double lat, String registrationId, String code, String userName, String headImg);

    /**
     * 微信获取WxOpenId
     *
     * @param body
     */
    Observable getWxOpenId(Map<String, String> headers,RequestBody body);

    /**
     * 微信获取用户信息
     *
     * @param body
     */
    Observable getWxUserInfo(Map<String, String> headers,RequestBody body);
}
