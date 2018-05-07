package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.SendPostActivityModule;
import com.haotang.easyshare.di.module.activity.SwitchCityActivityModule;
import com.haotang.easyshare.mvp.view.activity.SwitchCityActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:13
 */
@ActivityScope
@Component(modules = SwitchCityActivityModule.class)
public interface SwitchCityActivityCommponent {
    void inject(SwitchCityActivity switchCityActivity);
}
