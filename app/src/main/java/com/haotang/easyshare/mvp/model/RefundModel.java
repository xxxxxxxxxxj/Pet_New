package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.RefundApiService;
import com.haotang.easyshare.mvp.model.imodel.IRefundModel;
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
 * @date XJ on 2018/7/25 14:14
 */
public class RefundModel implements IRefundModel {
    /**
     * 标签列表
     * @param build
     */
    @Override
    public Observable list(Map<String, String> headers, RequestBody build) {
        return DevRing.httpManager().getService(RefundApiService.class).list(headers,build);
    }

    /**
     * 退款说明
     */
    @Override
    public Observable explain(Map<String, String> headers) {
        return DevRing.httpManager().getService(RefundApiService.class).explain(headers);
    }

    /**
     * 发起充值退款
     */
    @Override
    public Observable refund(Map<String, String> headers,RequestBody build) {
        return DevRing.httpManager().getService(RefundApiService.class).refund(headers,build);
    }
}
