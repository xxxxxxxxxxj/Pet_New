package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.OutTimeCouponActivityModule;
import com.haotang.easyshare.mvp.view.activity.OutTimeCouponActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/21 16:44
 */
@ActivityScope
@Component(modules = OutTimeCouponActivityModule.class)
public interface OutTimeCouponActivityCommponent {
    void inject(OutTimeCouponActivity outTimeCouponActivity);
}
