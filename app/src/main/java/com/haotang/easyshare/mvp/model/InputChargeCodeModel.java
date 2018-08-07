package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.ScanCodeApiService;
import com.haotang.easyshare.mvp.model.imodel.IInputChargeCodeModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 09:51
 */
public class InputChargeCodeModel implements IInputChargeCodeModel {
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
