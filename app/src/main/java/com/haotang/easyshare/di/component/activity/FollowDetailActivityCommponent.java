package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.CommentDetailActivityModule;
import com.haotang.easyshare.di.module.activity.FollowDetailActivityModule;
import com.haotang.easyshare.mvp.view.activity.FollowDetailActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:44
 */
@ActivityScope
@Component(modules = FollowDetailActivityModule.class)
public interface FollowDetailActivityCommponent {
    void inject(FollowDetailActivity followDetailActivity);
}
