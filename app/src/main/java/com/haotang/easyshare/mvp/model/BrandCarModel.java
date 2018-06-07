package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.BrandCarApiService;
import com.haotang.easyshare.mvp.model.imodel.IBrandCarModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

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
     * 品牌车型
     */
    @Override
    public Observable car() {
        return DevRing.httpManager().getService(BrandCarApiService.class).car();
    }
}
