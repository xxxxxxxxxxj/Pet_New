package com.haotang.easyshare.di.component.fragment;

import com.haotang.easyshare.di.module.fragment.MainFragmentModule;
import com.haotang.easyshare.di.module.fragment.MyFragmentModule;
import com.haotang.easyshare.mvp.view.fragment.MyFragment;
import com.ljy.devring.di.scope.FragmentScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:35
 */
@FragmentScope
@Component(modules = MyFragmentModule.class)
public interface MyFragmentCommponent {
    void inject(MyFragment myFragment);
}
