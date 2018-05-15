package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.AddChargeApiService;
import com.haotang.easyshare.mvp.model.http.SendPostApiService;
import com.haotang.easyshare.mvp.model.imodel.ISendPostModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:10
 */
public class SendPostModel implements ISendPostModel {
    /**
     * 发帖
     */
    @Override
    public Observable save(RequestBody body) {
        return DevRing.httpManager().getService(SendPostApiService.class).save(body);
    }
}
