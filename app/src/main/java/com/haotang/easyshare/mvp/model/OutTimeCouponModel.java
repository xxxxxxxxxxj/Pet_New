package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.MyCouponApiService;
import com.haotang.easyshare.mvp.model.imodel.IOutTimeCouponModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/21 16:50
 */
public class OutTimeCouponModel implements IOutTimeCouponModel {
    /**
     * 过期优惠券列表
     * @param body
     */
    @Override
    public Observable list(RequestBody body) {
        return DevRing.httpManager().getService(MyCouponApiService.class).list(body);
    }
}
