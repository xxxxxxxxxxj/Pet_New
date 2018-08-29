package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.ChargingPileDetailApiService;
import com.haotang.easyshare.mvp.model.http.HotFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.IChargingPileDetailModel;
import com.ljy.devring.DevRing;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:21
 */
public class ChargingPileDetailModel implements IChargingPileDetailModel {
    /**
     * 充电桩详情
     *
     * @param lng
     * @param lat
     * @param uuid
     * @param md5
     */
    @Override
    public Observable detail(double lng, double lat, String uuid, String md5) {
        return DevRing.httpManager().getService(ChargingPileDetailApiService.class).detail(lng, lat, uuid, md5);
    }

    /**
     * 收藏充电桩
     *
     * @param parmMap
     */
    @Override
    public Observable follow(Map<String, String> parmMap) {
        return DevRing.httpManager().getService(ChargingPileDetailApiService.class).follow(parmMap);
    }

    /**
     * 取消收藏充电桩
     *
     * @param parmMap
     */
    @Override
    public Observable cancel(Map<String, String> parmMap) {
        return DevRing.httpManager().getService(ChargingPileDetailApiService.class).cancel(parmMap);
    }

    /**
     * 浮窗
     */
    @Override
    public Observable list(MultipartBody body) {
        return DevRing.httpManager().getService(HotFragmentApiService.class).list(body);
    }
}
