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
 * @date zhoujunxia on 2018/9/4 17:15
 */
public interface SelectCouponApiService {
    /**
     * 匹配可用优惠券列表
     * @param body
     */
    @POST(UrlConstants.MATCH_COUPON)
    Observable<MyCoupon> match(@HeaderMap Map<String, String> headers, @Body() RequestBody body);
}
