package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.RechargeApiService;
import com.haotang.easyshare.mvp.model.http.ScanCodeApiService;
import com.haotang.easyshare.mvp.model.imodel.IScanCodeModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/24 11:01
 */
public class ScanCodeModel implements IScanCodeModel {
    /**
     * 发起充电
     *
     * @param body
     */
    @Override
    public Observable start(RequestBody body) {
        return DevRing.httpManager().getService(ScanCodeApiService.class).start(body);
    }
}
