package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.LoginBean;
import com.haotang.easyshare.mvp.model.entity.res.SendVerifyCodeBean;
import com.haotang.easyshare.mvp.model.entity.res.WxLoginBean;
import com.haotang.easyshare.mvp.model.entity.res.WxUserInfoBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
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
    Observable<HttpResult<SendVerifyCodeBean>> sendVerifyCode(@HeaderMap Map<String, String> headers, @Header("phone") String phone, @Query("phone") String phone1);

    /**
     * 登陆
     */
    @GET(UrlConstants.LOGIN)
    Observable<HttpResult<LoginBean>> login(@HeaderMap Map<String, String> headers,@Header("phone") String phone, @Query("phone") String phone1, @Query("wxOpenId") String wxOpenId,
                                            @Query("lng") double lng, @Query("lat") double lat,
                                            @Query("registrationId") String registrationId,
                                            @Query("code") String code,
                                            @Query("userName") String userName,
                                            @Query("headImg") String headImg);

    /**
     * 获取微信WxOpenId
     *
     * @param body
     */
    @POST(UrlConstants.GET_WXOPENID)
    Observable<HttpResult<WxLoginBean>> getWxOpenId(@HeaderMap Map<String, String> headers,@Body() RequestBody body);

    /**
     * 获取微信用户信息
     *
     * @param body
     */
    @POST(UrlConstants.GET_WX_USERINFO)
    Observable<HttpResult<WxUserInfoBean>> getWxUserInfo(@HeaderMap Map<String, String> headers,@Body() RequestBody body);
}
