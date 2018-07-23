package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.RechargeActivityModule;
import com.haotang.easyshare.di.module.activity.RechargeRecordActivityModule;
import com.haotang.easyshare.mvp.view.activity.RechargeRecordActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 17:26
 */
@ActivityScope
@Component(modules = RechargeRecordActivityModule.class)
public interface RechargeRecordActivityCommponent {
    void inject(RechargeRecordActivity rechargeRecordActivity);
}
