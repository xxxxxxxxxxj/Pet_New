package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.FollowDetailApiService;
import com.haotang.easyshare.mvp.model.imodel.IFollowDetailModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:46
 */
public class FollowDetailModel implements IFollowDetailModel {
    /**
     * 用户信息
     *
     * @param uuid
     */
    @Override
    public Observable info(String uuid) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).info(uuid);
    }

    /**
     * 用户帖子列表
     */
    @Override
    public Observable list(RequestBody body) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).list(body);
    }

    /**
     * 关注用户
     *
     * @param build
     */
    @Override
    public Observable follow(RequestBody build) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).follow(build);
    }

    /**
     * 取消关注用户
     */
    @Override
    public Observable cancel(RequestBody body) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).cancel(body);
    }

    /**
     * 评价用户
     */
    @Override
    public Observable eval(RequestBody body) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).eval(body);
    }

    /**
     * 点赞
     */
    @Override
    public Observable praise(RequestBody body) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).praise(body);
    }

    /**
     * 评价星级
     */
    @Override
    public Observable stars() {
        return DevRing.httpManager().getService(FollowDetailApiService.class).stars();
    }
}
