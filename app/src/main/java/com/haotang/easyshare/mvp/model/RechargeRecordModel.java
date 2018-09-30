package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.RechargeRecordApiService;
import com.haotang.easyshare.mvp.model.imodel.IRechargeRecordModel;
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
 * @date XJ on 2018/7/23 17:28
 */
public class RechargeRecordModel implements IRechargeRecordModel {
    /**
     * 订单列表
     * @param build
     */
    @Override
    public Observable list(Map<String, String> headers, RequestBody build) {
        return DevRing.httpManager().getService(RechargeRecordApiService.class).list(headers,build);
    }
}
