package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.LoginBean;
import com.haotang.easyshare.mvp.model.entity.res.SendVerifyCodeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/10 18:49
 */
public interface LoginApiService {
    /**
     * 下发验证码
     */
    @GET(UrlConstants.SENDVERIFYCODE)
    Observable<HttpResult<SendVerifyCodeBean>> sendVerifyCode(@Header("phone") String phone, @Query("phone") String phone1);

    /**
     * 登陆
     */
    @GET(UrlConstants.LOGIN)
    Observable<HttpResult<LoginBean>> login(@Header("phone") String phone, @Query("phone") String phone1, @Query("wxOpenId") String wxOpenId,
                                            @Query("lng") double lng, @Query("lat") double lat,
                                            @Query("registrationId") String registrationId,
                                            @Query("code") String code,
                                            @Query("userName") String userName,
                                            @Query("headImg") String headImg);
}
