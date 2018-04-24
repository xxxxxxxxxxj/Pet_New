package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.FlashActivityModule;
import com.haotang.easyshare.mvp.view.activity.FlashActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/12 13:30
 */
@ActivityScope
@Component(modules = FlashActivityModule.class)
public interface FlashActivityCommponent {
    void inject(FlashActivity flashActivity);
}
