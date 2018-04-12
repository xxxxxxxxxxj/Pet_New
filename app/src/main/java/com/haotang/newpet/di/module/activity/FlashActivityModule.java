package com.haotang.newpet.di.module.activity;

import com.haotang.newpet.mvp.model.FlashModel;
import com.haotang.newpet.mvp.model.imodel.IFlashModel;
import com.haotang.newpet.mvp.presenter.FlashPresenter;
import com.haotang.newpet.mvp.view.iview.IFlashView;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/12 13:54
 */
@Module
public class FlashActivityModule {
    private IFlashView mIFlashView;

    public FlashActivityModule(IFlashView iFlashView) {
        mIFlashView = iFlashView;
    }

    @Provides
    @ActivityScope
    IFlashView iFlashView() {
        return mIFlashView;
    }

    @Provides
    @ActivityScope
    IFlashModel iFlashModel() {
        return new FlashModel();
    }

    @Provides
    @ActivityScope
    FlashPresenter FlashPresenter(IFlashView iFlashView, IFlashModel iFlashModel) {
        return new FlashPresenter(iFlashView, iFlashModel);
    }
}
