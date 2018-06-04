package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.BrandCarModel;
import com.haotang.easyshare.mvp.model.imodel.IBrandCarModel;
import com.haotang.easyshare.mvp.presenter.BrandCarPresenter;
import com.haotang.easyshare.mvp.view.iview.IBrandCarView;
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
 * @date XJ on 2018/6/4 18:13
 */
@Module
public class BrandCarActivityModule {
    private Context mContext;
    private IBrandCarView mIBrandCarView;

    public BrandCarActivityModule(IBrandCarView iBrandCarView, Context context) {
        mIBrandCarView = iBrandCarView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IBrandCarView iBrandCarView() {
        return mIBrandCarView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IBrandCarModel iBrandCarModel() {
        return new BrandCarModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    BrandCarPresenter BrandCarPresenter(IBrandCarView iBrandCarView, IBrandCarModel iBrandCarModel) {
        return new BrandCarPresenter(iBrandCarView, iBrandCarModel);
    }
}
