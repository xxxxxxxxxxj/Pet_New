package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.HotFragmentApiService;
import com.haotang.easyshare.mvp.model.http.PostListApiService;
import com.haotang.easyshare.mvp.model.imodel.IPostListModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 16:54
 */
public class PostListModel implements IPostListModel {
    /**
     * 最新帖子列表
     */
    @Override
    public Observable newest(RequestBody body) {
        return DevRing.httpManager().getService(HotFragmentApiService.class).newest(body);
    }

    /**
     * 热门帖子列表
     */
    @Override
    public Observable hot(RequestBody body) {
        return DevRing.httpManager().getService(PostListApiService.class).hot(body);
    }

    /**
     * 问题车帖子列表
     */
    @Override
    public Observable problemCar(RequestBody body) {
        return DevRing.httpManager().getService(PostListApiService.class).problemCar(body);
    }
}
