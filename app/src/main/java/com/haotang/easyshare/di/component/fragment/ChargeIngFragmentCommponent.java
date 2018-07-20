package com.haotang.easyshare.di.component.fragment;

import com.haotang.easyshare.di.module.fragment.ChargeIngFragmentModule;
import com.haotang.easyshare.mvp.view.fragment.ChargeIngFragment;
import com.ljy.devring.di.scope.FragmentScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:54
 */
@FragmentScope
@Component(modules = ChargeIngFragmentModule.class)
public interface ChargeIngFragmentCommponent {
    void inject(ChargeIngFragment chargeIngFragment);
}
