package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.AddChargeApiService;
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
     * @param paramsMap
     * @param filedMap
     */
    @Override
    public Observable save(Map<String, String> paramsMap, Map<String, RequestBody> filedMap) {
        return DevRing.httpManager().getService(AddChargeApiService.class).save(filedMap,paramsMap);
    }
}
