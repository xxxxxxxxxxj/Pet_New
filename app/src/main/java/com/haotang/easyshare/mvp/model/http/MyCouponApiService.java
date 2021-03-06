package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.MyCoupon;

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
 * @date zhoujunxia on 2018/8/29 13:07
 */
public interface MyCouponApiService {
    /**
     * 我的优惠券列表
     * @param body
     */
    @POST(UrlConstants.MYCOUPON)
    Observable<MyCoupon> list(@HeaderMap Map<String, String> headers, @Body() RequestBody body);
}
