package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.AllBrandsApiService;
import com.haotang.easyshare.mvp.model.http.BrandCarApiService;
import com.haotang.easyshare.mvp.model.imodel.IBrandCarModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/6/4 18:14
 */
public class BrandCarModel implements IBrandCarModel {
    /**
     * 所有品牌
     */
    @Override
    public Observable list() {
        return DevRing.httpManager().getService(AllBrandsApiService.class).list();
    }

    /**
     * 品牌车型
     */
    @Override
    public Observable carList(RequestBody body) {
        return DevRing.httpManager().getService(BrandCarApiService.class).carList(body);
    }
}
