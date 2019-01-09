package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.HotFragmentApiService;
import com.haotang.easyshare.mvp.model.http.PostListApiService;
import com.haotang.easyshare.mvp.model.imodel.IHotPostFragmentModel;
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
 * @date zhoujunxia on 2019/1/9 18:35
 */
public class HotPostFragmentModel implements IHotPostFragmentModel {
    /**
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    @Override
    public Observable list(Map<String, String> headers, RequestBody body) {
        return DevRing.httpManager().getService(HotFragmentApiService.class).list(headers,body);
    }

    /**
     * 最新帖子列表
     */
    @Override
    public Observable newest(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(HotFragmentApiService.class).newest(headers,body);
    }

    /**
     * 热门帖子列表
     */
    @Override
    public Observable hot(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(PostListApiService.class).hot(headers,body);
    }

    /**
     * 问题车帖子列表
     */
    @Override
    public Observable problemCar(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(PostListApiService.class).problemCar(headers,body);
    }
}
