package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.BrandAreaApiService;
import com.haotang.easyshare.mvp.model.imodel.IBrandAreaModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 16:50
 */
public class BrandAreaModel implements IBrandAreaModel {
    /**
     * 品牌热帖
     */
    @Override
    public Observable article(RequestBody body) {
        return DevRing.httpManager().getService(BrandAreaApiService.class).article(body);
    }
}
