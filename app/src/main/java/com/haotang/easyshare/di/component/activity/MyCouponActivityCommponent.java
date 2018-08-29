package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.MyCouponActivityModule;
import com.haotang.easyshare.mvp.view.activity.MyCouponActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/29 11:35
 */
@ActivityScope
@Component(modules = MyCouponActivityModule.class)
public interface MyCouponActivityCommponent {
    void inject(MyCouponActivity myCouponActivity);
}
