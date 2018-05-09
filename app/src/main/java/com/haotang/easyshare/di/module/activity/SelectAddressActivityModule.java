package com.haotang.easyshare.di.module.activity;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 16:02
 */

import android.content.Context;

import com.haotang.easyshare.mvp.model.SelectAddressModel;
import com.haotang.easyshare.mvp.model.imodel.ISelectAddressModel;
import com.haotang.easyshare.mvp.presenter.SelectAddressPresenter;
import com.haotang.easyshare.mvp.view.iview.ISelectAddressView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class SelectAddressActivityModule {
    private Context mContext;
    private ISelectAddressView mISelectAddressView;

    public SelectAddressActivityModule(ISelectAddressView iSelectAddressView, Context context) {
        mISelectAddressView = iSelectAddressView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ISelectAddressView iSelectAddressView() {
        return mISelectAddressView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ISelectAddressModel iSelectAddressModel() {
        return new SelectAddressModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    SelectAddressPresenter SelectAddressPresenter(ISelectAddressView iSelectAddressView, ISelectAddressModel iSelectAddressModel) {
        return new SelectAddressPresenter(iSelectAddressView, iSelectAddressModel);
    }
}
