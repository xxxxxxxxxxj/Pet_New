package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.ChargeingFragmentApiService;
import com.haotang.easyshare.mvp.model.http.MyFragmentApiService;
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
     * 结束充电
     *
     * @param build
     */
    @Override
    public Observable stop(RequestBody build) {
        return DevRing.httpManager().getService(ChargeingFragmentApiService.class).stop(build);
    }

    /**
     * 支付账单
     *
     * @param build
     */
    @Override
    public Observable pay(RequestBody build) {
        return DevRing.httpManager().getService(ChargeingFragmentApiService.class).pay(build);
    }

    /**
     * 故障报修
     *
     * @param build
     */
    @Override
    public Observable save(RequestBody build) {
        return DevRing.httpManager().getService(ChargeingFragmentApiService.class).save(build);
    }

    /**
     * 用户主页信息
     */
    @Override
    public Observable home() {
        return DevRing.httpManager().getService(MyFragmentApiService.class).home();
    }
}
