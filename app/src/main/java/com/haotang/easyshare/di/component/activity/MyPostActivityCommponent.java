package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.MyFollowActivityModule;
import com.haotang.easyshare.di.module.activity.MyPostActivityModule;
import com.haotang.easyshare.mvp.view.activity.MyPostActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:05
 */
@ActivityScope
@Component(modules = MyPostActivityModule.class)
public interface MyPostActivityCommponent {
    void inject(MyPostActivity myPostActivity);
}
