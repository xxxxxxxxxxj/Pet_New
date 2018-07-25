package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.RefundResultActivityModule;
import com.haotang.easyshare.mvp.view.activity.RefundResultActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 16:24
 */
@ActivityScope
@Component(modules = RefundResultActivityModule.class)
public interface RefundResultActivityCommponent {
    void inject(RefundResultActivity refundResultActivity);
}
