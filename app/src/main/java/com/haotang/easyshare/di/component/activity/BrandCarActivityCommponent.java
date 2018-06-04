package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.BrandCarActivityModule;
import com.haotang.easyshare.mvp.view.activity.BrandCarActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/6/4 18:09
 */
@ActivityScope
@Component(modules = BrandCarActivityModule.class)
public interface BrandCarActivityCommponent {
    void inject(BrandCarActivity brandCarActivity);
}
