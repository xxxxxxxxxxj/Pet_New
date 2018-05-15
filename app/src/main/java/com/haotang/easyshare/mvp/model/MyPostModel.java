package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.FollowDetailApiService;
import com.haotang.easyshare.mvp.model.imodel.IMyPostModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:07
 */
public class MyPostModel implements IMyPostModel {
    /**
     * 用户帖子列表
     */
    @Override
    public Observable list(RequestBody body) {
        return DevRing.httpManager().getService(FollowDetailApiService.class).list(body);
    }
}
