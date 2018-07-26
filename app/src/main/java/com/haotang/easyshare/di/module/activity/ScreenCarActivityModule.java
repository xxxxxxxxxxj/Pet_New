package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.ScreenCarModel;
import com.haotang.easyshare.mvp.model.imodel.IScreenCarModel;
import com.haotang.easyshare.mvp.presenter.ScreenCarPresenter;
import com.haotang.easyshare.mvp.view.iview.IScreenCarView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 10:35
 */
@Module
public class ScreenCarActivityModule {
    private Context mContext;
    private IScreenCarView mIScreenCarView;

    public ScreenCarActivityModule(IScreenCarView iScreenCarView, Context context) {
        mIScreenCarView = iScreenCarView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IScreenCarView iScreenCarView() {
        return mIScreenCarView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IScreenCarModel iScreenCarModel() {
        return new ScreenCarModel();
    }

    @Provides
    @ActivityScope
    ScreenCarPresenter ScreenCarPresenter(IScreenCarView iScreenCarView, IScreenCarModel iScreenCarModel) {
        return new ScreenCarPresenter(iScreenCarView, iScreenCarModel);
    }
}
