package com.haotang.newpet.di.component.activity;

import com.haotang.newpet.di.module.activity.FlashActivityModule;
import com.haotang.newpet.di.module.activity.MainActivityModule;
import com.haotang.newpet.mvp.view.activity.MainActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/13 17:22
 */
@ActivityScope
@Component(modules = MainActivityModule.class)
public interface MainActivityCommponent {
    void inject(MainActivity mainActivity);
}
