package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.FollowDetailApiService;
import com.haotang.easyshare.mvp.model.imodel.IFollowDetailModel;
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
 * @date XJ on 2018/5/7 17:46
 */
public class FollowDetailModel implements IFollowDetailModel {
    /**
     * 用户信息
     *
     * @param uuid
     */
    @Override
    public Observable info(Map<String, String> headers, String uuid) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).info(headers,uuid);
    }

    /**
     * 用户帖子列表
     */
    @Override
    public Observable list(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).list(headers,body);
    }

    /**
     * 关注用户
     *
     * @param build
     */
    @Override
    public Observable follow(Map<String, String> headers,RequestBody build) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).follow(headers,build);
    }

    /**
     * 取消关注用户
     */
    @Override
    public Observable cancel(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).cancel(headers,body);
    }

    /**
     * 评价用户
     */
    @Override
    public Observable eval(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).eval(headers,body);
    }

    /**
     * 点赞
     */
    @Override
    public Observable praise(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).praise(headers,body);
    }

    /**
     * 评价星级
     */
    @Override
    public Observable stars(Map<String, String> headers) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).stars(headers);
    }
}
