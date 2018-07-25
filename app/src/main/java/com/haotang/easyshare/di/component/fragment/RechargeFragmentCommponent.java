package com.haotang.easyshare.di.component.fragment;

import com.haotang.easyshare.di.module.fragment.RechargeFragmentModule;
import com.haotang.easyshare.mvp.view.fragment.RechargeFragment;
import com.ljy.devring.di.scope.FragmentScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 12:06
 */
@FragmentScope
@Component(modules = RechargeFragmentModule.class)
public interface RechargeFragmentCommponent {
    void inject(RechargeFragment rechargeFragment);
}
