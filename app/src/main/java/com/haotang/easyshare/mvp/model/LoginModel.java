package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.LoginApiService;
import com.haotang.easyshare.mvp.model.imodel.ILoginModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:55
 */
public class LoginModel implements ILoginModel {
    /**
     * 下发验证码
     */
    @Override
    public Observable sendVerifyCode(String phone) {
        return DevRing.httpManager().getService(LoginApiService.class).
                sendVerifyCode(phone,phone);
    }

    /**
     * 登陆
     */
    @Override
    public Observable login(String phone,String wxOpenId, double lng, double lat, String registrationId, String code) {
        return DevRing.httpManager().getService(LoginApiService.class).
                login(phone,phone,wxOpenId, lng, lat, registrationId, code);
    }
}
