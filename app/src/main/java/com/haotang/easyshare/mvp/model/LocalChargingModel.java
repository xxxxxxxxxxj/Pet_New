package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.LocalChargingApiService;
import com.haotang.easyshare.mvp.model.imodel.ILocalChargingModel;
import com.ljy.devring.DevRing;

import java.util.Map;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:51
 */
public class LocalChargingModel implements ILocalChargingModel {
    /**
     * 附近充电桩
     * @param lng
     * @param lat
     * @param mNextRequestPage
     * @param sign
     */
    @Override
    public Observable nearby(Map<String, String> headers, double lng, double lat, int mNextRequestPage, String sign) {
        return DevRing.httpManager().getService(LocalChargingApiService.class).nearby(headers,lng, lat,mNextRequestPage,sign);
    }
}
