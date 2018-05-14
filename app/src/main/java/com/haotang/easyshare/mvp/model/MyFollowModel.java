package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.MyFollowApiService;
import com.haotang.easyshare.mvp.model.imodel.IMyFollowModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:02
 */
public class MyFollowModel implements IMyFollowModel {
    /**
     * 关注的人列表
     */
    @Override
    public Observable list() {
        return DevRing.httpManager().getService(MyFollowApiService.class).list();
    }
}
