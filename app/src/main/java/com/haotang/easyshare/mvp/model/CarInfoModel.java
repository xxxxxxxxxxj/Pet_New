package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.MyFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.ICarInfoModel;
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
 * @date XJ on 2018/5/7 17:11
 */
public class CarInfoModel implements ICarInfoModel {
    /**
     * 用户车辆信息
     */
    @Override
    public Observable my(Map<String, String> headers) {
        return DevRing.httpManager().getService(MyFragmentApiService.class).my(headers);
    }

    /**
     * 保存或修改用户车辆信息
     */
    @Override
    public Observable save(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(MyFragmentApiService.class).save(headers,body);
    }
}
