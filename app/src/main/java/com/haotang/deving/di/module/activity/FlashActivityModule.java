package com.haotang.deving.di.module.activity;

import android.content.Context;

import com.haotang.deving.mvp.model.FlashModel;
import com.haotang.deving.mvp.model.imodel.IFlashModel;
import com.haotang.deving.mvp.presenter.FlashPresenter;
import com.haotang.deving.mvp.view.iview.IFlashView;
import com.haotang.deving.mvp.view.widget.PermissionDialog;
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
    private Context mContext;
    private IFlashView mIFlashView;

    public FlashActivityModule(IFlashView iFlashView, Context context) {
        mIFlashView = iFlashView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IFlashView iFlashView() {
        return mIFlashView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IFlashModel iFlashModel() {
        return new FlashModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    FlashPresenter FlashPresenter(IFlashView iFlashView, IFlashModel iFlashModel) {
        return new FlashPresenter(iFlashView, iFlashModel);
    }
}
