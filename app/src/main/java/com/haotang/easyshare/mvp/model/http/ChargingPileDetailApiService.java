package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.ChargeDetailBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/13 12:04
 */
public interface ChargingPileDetailApiService {
    /**
     * 充电桩详情
     * @param lng
     * @param lat
     * @param uuid
     * @param sign
     */
    @GET(UrlConstants.CHARGEDETAIL)
    Observable<HttpResult<ChargeDetailBean>> detail(@Query("lng") double lng, @Query("lat") double lat,
                                                    @Query("uuid") String uuid, @Query("sign") String sign);
}
