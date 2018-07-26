package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.FollowDetailActivityModule;
import com.haotang.easyshare.di.module.activity.InputChargeCodeActivityModule;
import com.haotang.easyshare.mvp.view.activity.InputChargeCodeActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 09:49
 */
@ActivityScope
@Component(modules = InputChargeCodeActivityModule.class)
public interface InputChargeCodeActivityCommponent {
    void inject(InputChargeCodeActivity inputChargeCodeActivity);
}
