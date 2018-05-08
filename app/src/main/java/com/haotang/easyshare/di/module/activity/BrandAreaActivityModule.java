package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.BrandAreaModel;
import com.haotang.easyshare.mvp.model.imodel.IBrandAreaModel;
import com.haotang.easyshare.mvp.presenter.BrandAreaPresenter;
import com.haotang.easyshare.mvp.view.iview.IBrandAreaView;
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
 * @date XJ on 2018/5/8 16:49
 */
@Module
public class BrandAreaActivityModule {
    private Context mContext;
    private IBrandAreaView mIBrandAreaView;

    public BrandAreaActivityModule(IBrandAreaView iBrandAreaView, Context context) {
        mIBrandAreaView = iBrandAreaView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IBrandAreaView iBrandAreaView() {
        return mIBrandAreaView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IBrandAreaModel iBrandAreaModel() {
        return new BrandAreaModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    BrandAreaPresenter BrandAreaPresenter(IBrandAreaView iBrandAreaView, IBrandAreaModel iBrandAreaModel) {
        return new BrandAreaPresenter(iBrandAreaView, iBrandAreaModel);
    }
}
