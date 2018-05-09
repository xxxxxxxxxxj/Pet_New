package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.AddAddressModel;
import com.haotang.easyshare.mvp.model.imodel.IAddAddressModel;
import com.haotang.easyshare.mvp.presenter.AddAddressPresenter;
import com.haotang.easyshare.mvp.view.iview.IAddAddressView;
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
 * @date XJ on 2018/5/9 16:27
 */
@Module
public class AddAddressActivityModule {
    private Context mContext;
    private IAddAddressView mIAddAddressView;

    public AddAddressActivityModule(IAddAddressView iAddAddressView, Context context) {
        mIAddAddressView = iAddAddressView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IAddAddressView iAddAddressView() {
        return mIAddAddressView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IAddAddressModel iAddAddressModel() {
        return new AddAddressModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    AddAddressPresenter AddAddressPresenter(IAddAddressView iAddAddressView, IAddAddressModel iAddAddressModel) {
        return new AddAddressPresenter(iAddAddressView, iAddAddressModel);
    }
}
