package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.AddChargeApiService;
import com.haotang.easyshare.mvp.model.imodel.IAddChargeModel;
import com.ljy.devring.DevRing;

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
    public Observable save(RequestBody body) {
        return DevRing.httpManager().getService(AddChargeApiService.class).save(body);
    }
}
