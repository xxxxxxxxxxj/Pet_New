package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.ScanCodeModel;
import com.haotang.easyshare.mvp.model.imodel.IScanCodeModel;
import com.haotang.easyshare.mvp.presenter.ScanCodePresenter;
import com.haotang.easyshare.mvp.view.iview.IScanCodeView;
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
 * @date XJ on 2018/7/24 11:01
 */
@Module
public class ScanCodeActivityModule {
    private Context mContext;
    private IScanCodeView mIScanCodeView;

    public ScanCodeActivityModule(IScanCodeView iScanCodeView, Context context) {
        mIScanCodeView = iScanCodeView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IScanCodeView iScanCodeView() {
        return mIScanCodeView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IScanCodeModel iScanCodeModel() {
        return new ScanCodeModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    ScanCodePresenter ScanCodePresenter(IScanCodeView iScanCodeView, IScanCodeModel iScanCodeModel) {
        return new ScanCodePresenter(iScanCodeView, iScanCodeModel);
    }
}
