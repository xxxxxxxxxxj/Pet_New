package com.haotang.easyshare.di.component.activity;

import com.haotang.easyshare.di.module.activity.SerchPostActivityModule;
import com.haotang.easyshare.mvp.view.activity.SerchPostActivity;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Component;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/16 10:02
 */
@ActivityScope
@Component(modules = SerchPostActivityModule.class)
public interface SerchPostActivityCommponent {
    void inject(SerchPostActivity serchPostActivity);
}
