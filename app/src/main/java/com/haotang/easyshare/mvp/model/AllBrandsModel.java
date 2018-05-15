package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.AllBrandsApiService;
import com.haotang.easyshare.mvp.model.http.HotFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.IAllBrandsModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 16:46
 */
public class AllBrandsModel implements IAllBrandsModel {
    /**
     * 所有品牌
     */
    @Override
    public Observable list() {
        return DevRing.httpManager().getService(AllBrandsApiService.class).list();
    }
}
