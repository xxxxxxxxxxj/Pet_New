package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.CarDetailApiService;
import com.haotang.easyshare.mvp.model.imodel.ICarDetailModel;
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
 * @date zhoujunxia on 2018/9/4 12:58
 */
public class CarDetailModel implements ICarDetailModel {
    /**
     * 车型详情
     * @param body
     */
    @Override
    public Observable detail(Map<String, String> headers, RequestBody body) {
        return DevRing.httpManager().getService(CarDetailApiService.class).detail(headers,body);
    }
}
