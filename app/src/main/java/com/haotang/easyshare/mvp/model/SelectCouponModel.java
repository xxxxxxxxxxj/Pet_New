package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.SelectCouponApiService;
import com.haotang.easyshare.mvp.model.imodel.ISelectCouponModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/4 16:54
 */
public class SelectCouponModel implements ISelectCouponModel {
    /**
     * 匹配可用优惠券列表
     * @param body
     */
    @Override
    public Observable match(RequestBody body) {
        return DevRing.httpManager().getService(SelectCouponApiService.class).match(body);
    }
}
