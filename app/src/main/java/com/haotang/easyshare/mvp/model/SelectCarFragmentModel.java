package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.AllBrandsApiService;
import com.haotang.easyshare.mvp.model.http.HotFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.ISelectCarFragmentModel;
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
 * @date XJ on 2018/7/20 15:02
 */
public class SelectCarFragmentModel implements ISelectCarFragmentModel {
    /**
     * 热门品牌
     */
    @Override
    public Observable hot(Map<String, String> headers) {
        return DevRing.httpManager().getService(HotFragmentApiService.class).hot(headers);
    }

    /**
     * 热门车型
     */
    @Override
    public Observable special(Map<String, String> headers) {
        return DevRing.httpManager().getService(AllBrandsApiService.class).special(headers);
    }

    /**
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    @Override
    public Observable list(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(HotFragmentApiService.class).list(headers,body);
    }
}
