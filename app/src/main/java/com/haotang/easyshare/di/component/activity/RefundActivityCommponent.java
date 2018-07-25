package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.RefundActivityModule;
import com.haotang.easyshare.mvp.view.activity.RefundActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 14:12
 */
@ActivityScope
@Component(modules = RefundActivityModule.class)
public interface RefundActivityCommponent {
    void inject(RefundActivity refundActivity);
}
