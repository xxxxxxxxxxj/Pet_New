package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.AddChargeApiService;
import com.haotang.easyshare.mvp.model.http.ChargingPileDetailApiService;
import com.haotang.easyshare.mvp.model.imodel.IAddChargeModel;
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
 * @date XJ on 2018/5/7 16:49
 */
public class AddChargeModel implements IAddChargeModel {
    /**
     * 上传充电桩
     */
    @Override
    public Observable save(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(AddChargeApiService.class).save(headers,body);
    }

    /**
     * 充电桩详情
     *
     * @param lng
     * @param lat
     * @param uuid
     * @param md5
     */
    @Override
    public Observable detail(Map<String, String> headers,double lng, double lat, String uuid, String md5) {
        return DevRing.httpManager().getService(ChargingPileDetailApiService.class).detail(headers,lng, lat, uuid, md5);
    }

    /**
     * 编辑充电桩
     */
    @Override
    public Observable update(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(AddChargeApiService.class).update(headers,body);
    }
}
