package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.MyCouponApiService;
import com.haotang.easyshare.mvp.model.imodel.IMyCouponModel;
import com.ljy.devring.DevRing;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/29 11:42
 */
public class MyCouponModel implements IMyCouponModel {
    /**
     * 我的优惠券列表
     * @param body
     */
    @Override
    public Observable list(Map<String, String> headers, RequestBody body) {
        return DevRing.httpManager().getService(MyCouponApiService.class).list(headers,body);
    }
}
