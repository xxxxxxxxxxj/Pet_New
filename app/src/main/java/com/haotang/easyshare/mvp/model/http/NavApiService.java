package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/6/12 14:26
 */
public interface NavApiService {
    /**
     * 导航成功回调
     *
     * @return
     */
    @POST(UrlConstants.NAV_CALLBACK)
    Observable<HttpResult<AddChargeBean>> callback(@HeaderMap Map<String, String> headers, @Body() RequestBody body);
}
