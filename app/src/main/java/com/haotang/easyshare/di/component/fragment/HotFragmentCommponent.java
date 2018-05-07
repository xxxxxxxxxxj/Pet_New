package com.haotang.easyshare.di.component.fragment;

import com.haotang.easyshare.di.module.fragment.HotFragmentModule;
import com.haotang.easyshare.di.module.fragment.MainFragmentModule;
import com.haotang.easyshare.mvp.view.fragment.HotFragment;
import com.ljy.devring.di.scope.FragmentScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:31
 */
@FragmentScope
@Component(modules = HotFragmentModule.class)
public interface HotFragmentCommponent {
    void inject(HotFragment hotFragment);
}
