package com.haotang.newpet.di.component.activity;

import com.haotang.newpet.di.module.activity.FlashActivityModule;
import com.haotang.newpet.mvp.view.activity.FlashActivity;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/12 13:30
 */
//@Component(modules = FlashActivityModule.class)
public interface FlashActivityCommponent {
    void inject(FlashActivity flashActivity);
}
