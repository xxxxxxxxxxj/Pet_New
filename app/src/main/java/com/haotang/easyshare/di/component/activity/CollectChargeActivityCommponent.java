package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.ChargingPileDetailActivityModule;
import com.haotang.easyshare.di.module.activity.CollectChargeActivityModule;
import com.haotang.easyshare.mvp.view.activity.CollectChargeActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:28
 */
@ActivityScope
@Component(modules = CollectChargeActivityModule.class)
public interface CollectChargeActivityCommponent {
    void inject(CollectChargeActivity collectChargeActivity);
}
