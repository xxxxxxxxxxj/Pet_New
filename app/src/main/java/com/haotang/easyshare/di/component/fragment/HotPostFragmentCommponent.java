package com.haotang.easyshare.di.component.fragment;

import com.haotang.easyshare.di.module.fragment.HotFragmentModule;
import com.haotang.easyshare.di.module.fragment.HotPostFragmentModule;
import com.haotang.easyshare.mvp.view.fragment.HotPostFragment;
import com.ljy.devring.di.scope.FragmentScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/1/9 18:33
 */
@FragmentScope
@Component(modules = HotPostFragmentModule.class)
public interface HotPostFragmentCommponent {
    void inject(HotPostFragment hotPostFragment);
}
