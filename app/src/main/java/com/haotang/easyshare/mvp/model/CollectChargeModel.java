package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.ChargingPileDetailApiService;
import com.haotang.easyshare.mvp.model.http.CollectChargeApiService;
import com.haotang.easyshare.mvp.model.imodel.ICollectChargeModel;
import com.ljy.devring.DevRing;

import java.util.Map;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:26
 */
public class CollectChargeModel implements ICollectChargeModel {
    /**
     * 收藏的充电桩列表
     */
    @Override
    public Observable list(Map<String, String> headers) {
        return DevRing.httpManager().getService(CollectChargeApiService.class).list(headers);
    }

    /**
     * 取消收藏充电桩
     *
     * @param parmMap
     */
    @Override
    public Observable cancel(Map<String, String> headers,Map<String, String> parmMap) {
        return DevRing.httpManager().getService(ChargingPileDetailApiService.class).cancel(headers,parmMap);
    }
}
