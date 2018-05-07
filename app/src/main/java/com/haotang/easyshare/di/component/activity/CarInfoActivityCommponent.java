package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.CarInfoActivityModule;
import com.haotang.easyshare.mvp.view.activity.CarInfoActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:13
 */
@ActivityScope
@Component(modules = CarInfoActivityModule.class)
public interface CarInfoActivityCommponent {
    void inject(CarInfoActivity carInfoActivity);
}
