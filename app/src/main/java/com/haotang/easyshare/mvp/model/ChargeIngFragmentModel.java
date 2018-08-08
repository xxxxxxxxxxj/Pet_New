package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.ChargeingFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.IChargeIngFragmentModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:56
 */
public class ChargeIngFragmentModel implements IChargeIngFragmentModel {
    /**
     * 获取进行中的订单
     */
    @Override
    public Observable ing() {
        return DevRing.httpManager().getService(ChargeingFragmentApiService.class).ing();
    }

    /**
     * 查询充电状态
     *
     * @param build
     */
    @Override
    public Observable state(RequestBody build) {
        return DevRing.httpManager().getService(ChargeingFragmentApiService.class).state(build);
    }

    /**
     * 结束充电
     *
     * @param build
     */
    @Override
    public Observable stop(RequestBody build) {
        return DevRing.httpManager().getService(ChargeingFragmentApiService.class).stop(build);
    }

    /**
     * 获取账单
     *
     * @param build
     */
    @Override
    public Observable bill(RequestBody build) {
        return DevRing.httpManager().getService(ChargeingFragmentApiService.class).bill(build);
    }
}
