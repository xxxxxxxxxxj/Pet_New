package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.AddChargeActivityModule;
import com.haotang.easyshare.di.module.activity.PostListActivityModule;
import com.haotang.easyshare.mvp.view.activity.PostListActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 16:53
 */
@ActivityScope
@Component(modules = PostListActivityModule.class)
public interface PostListActivityCommponent {
    void inject(PostListActivity postListActivity);
}
