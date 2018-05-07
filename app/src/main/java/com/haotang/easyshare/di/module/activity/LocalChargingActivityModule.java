package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.LocalChargingModel;
import com.haotang.easyshare.mvp.model.imodel.ILocalChargingModel;
import com.haotang.easyshare.mvp.presenter.LocalChargingPresenter;
import com.haotang.easyshare.mvp.view.iview.ILocalChargingView;
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
 * @date XJ on 2018/5/7 17:50
 */
@Module
public class LocalChargingActivityModule {
    private Context mContext;
    private ILocalChargingView mILocalChargingView;

    public LocalChargingActivityModule(ILocalChargingView iLocalChargingView, Context context) {
        mILocalChargingView = iLocalChargingView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ILocalChargingView iLocalChargingView() {
        return mILocalChargingView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ILocalChargingModel iLocalChargingModel() {
        return new LocalChargingModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    LocalChargingPresenter LocalChargingPresenter(ILocalChargingView iLocalChargingView, ILocalChargingModel iLocalChargingModel) {
        return new LocalChargingPresenter(iLocalChargingView, iLocalChargingModel);
    }
}
