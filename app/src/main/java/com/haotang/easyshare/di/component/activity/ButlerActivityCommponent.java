package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.ButlerActivityModule;
import com.haotang.easyshare.mvp.view.activity.ButlerActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 15:44
 */
@ActivityScope
@Component(modules = ButlerActivityModule.class)
public interface ButlerActivityCommponent {
    void inject(ButlerActivity butlerActivity);
}
