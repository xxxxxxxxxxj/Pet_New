package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.AllBrandsApiService;
import com.haotang.easyshare.mvp.model.http.HotFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.ISelectCarFragmentModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 15:02
 */
public class SelectCarFragmentModel implements ISelectCarFragmentModel {
    /**
     * 热门品牌
     */
    @Override
    public Observable hot() {
        return DevRing.httpManager().getService(HotFragmentApiService.class).hot();
    }

    /**
     * 热门车型
     */
    @Override
    public Observable special() {
        return DevRing.httpManager().getService(AllBrandsApiService.class).special();
    }
}
