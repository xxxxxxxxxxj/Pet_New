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
 * @date XJ on 2018/5/21 18:59
 */
public interface ShareApiService {
    /**
     * 分享帖子成功回调
     *
     * @return
     */
    @POST(UrlConstants.SHARE_POST)
    Observable<HttpResult<AddChargeBean>> callback(@HeaderMap Map<String, String> headers, @Body() RequestBody body);

    /**
     * 分享站点详情成功回调
     *
     * @return
     */
    @POST(UrlConstants.SHARE_CHARGEING)
    Observable<HttpResult<AddChargeBean>> callbackChargeing(@HeaderMap Map<String, String> headers, @Body() RequestBody body);

    /**
     * 分享车辆详情成功回调
     *
     * @return
     */
    @POST(UrlConstants.SHARE_CAR)
    Observable<HttpResult<AddChargeBean>> callbackCar(@HeaderMap Map<String, String> headers, @Body() RequestBody body);

    /**
     * 打开app回调
     *
     * @return
     */
    @POST(UrlConstants.OPENAPP_CALLBACK)
    Observable<HttpResult<AddChargeBean>> callbackOpenApp(@HeaderMap Map<String, String> headers, @Body() RequestBody body);
}
