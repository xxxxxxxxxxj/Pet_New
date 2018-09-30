package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.CarPersonInfoApiService;
import com.haotang.easyshare.mvp.model.imodel.ICarPersonInfoModel;
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
 * @date zhoujunxia on 2018/9/4 15:50
 */
public class CarPersonInfoModel implements ICarPersonInfoModel {
    /**
     * 车型预定
     */
    @Override
    public Observable save(Map<String, String> headers, RequestBody body) {
        return DevRing.httpManager().getService(CarPersonInfoApiService.class).save(headers,body);
    }
}
