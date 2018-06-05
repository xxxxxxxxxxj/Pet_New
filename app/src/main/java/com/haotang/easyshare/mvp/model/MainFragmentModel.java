package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.HotFragmentApiService;
import com.haotang.easyshare.mvp.model.http.MainFragApiService;
import com.haotang.easyshare.mvp.model.imodel.IMainFragmentModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 18:18
 */
public class MainFragmentModel implements IMainFragmentModel {
    /**
     * 获取首页数据
     */
    @Override
    public Observable homeIndex(double lng, double lat) {
        return DevRing.httpManager().getService(MainFragApiService.class).homeIndex(lng, lat);
    }

    /**
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    @Override
    public Observable list(RequestBody body) {
        return DevRing.httpManager().getService(HotFragmentApiService.class).list(body);
    }
}
