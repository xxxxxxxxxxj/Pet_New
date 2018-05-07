package com.haotang.easyshare.di.component.fragment;

import com.haotang.easyshare.di.module.fragment.CurrentMessageFragmentModule;
import com.haotang.easyshare.di.module.fragment.MainFragmentModule;
import com.haotang.easyshare.mvp.view.fragment.CurrentMessageFragment;
import com.ljy.devring.di.scope.FragmentScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:22
 */
@FragmentScope
@Component(modules = CurrentMessageFragmentModule.class)
public interface CurrentMessageFragmentCommponent {
    void inject(CurrentMessageFragment currentMessageFragment);
}
