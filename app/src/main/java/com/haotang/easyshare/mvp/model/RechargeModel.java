package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.RechargeApiService;
import com.haotang.easyshare.mvp.model.imodel.IRechargeModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 16:49
 */
public class RechargeModel implements IRechargeModel {
    /**
     * 可充值模板列表
     */
    @Override
    public Observable list() {
        return DevRing.httpManager().getService(RechargeApiService.class).list();
    }

    /**
     * 发起充值请求
     */
    @Override
    public Observable build(RequestBody body) {
        return DevRing.httpManager().getService(RechargeApiService.class).build(body);
    }
}
