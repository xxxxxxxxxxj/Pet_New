package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.CarDetailActivityModule;
import com.haotang.easyshare.mvp.view.activity.CarDetailActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/4 12:56
 */
@ActivityScope
@Component(modules = CarDetailActivityModule.class)
public interface CarDetailActivityCommponent {
    void inject(CarDetailActivity carDetailActivity);
}
