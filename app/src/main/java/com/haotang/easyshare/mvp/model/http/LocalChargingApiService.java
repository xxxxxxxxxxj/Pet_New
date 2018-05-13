package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.MainFragChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/13 10:08
 */
public interface LocalChargingApiService {
    /**
     * 附近充电桩
     *
     * @param lng
     * @param lat
     * @param mNextRequestPage
     * @param sign
     */
    @GET(UrlConstants.NEARBY)
    Observable<HttpResult<List<MainFragChargeBean>>> nearby(@Query("lng") double lng, @Query("lat") double lat,
                                                            @Query("page") int mNextRequestPage, @Query("sign") String sign);
}
