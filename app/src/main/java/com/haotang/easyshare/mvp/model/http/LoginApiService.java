package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.SendVerifyCodeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;

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
    Observable<HttpResult<SendVerifyCodeBean>> sendVerifyCode(@Field("phone") String phone);
}
