package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.SerchAddressModel;
import com.haotang.easyshare.mvp.model.imodel.ISerchAddressModel;
import com.haotang.easyshare.mvp.presenter.SerchAddressPresenter;
import com.haotang.easyshare.mvp.view.iview.ISerchAddressView;
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
 * @date XJ on 2018/5/9 16:52
 */
@Module
public class SerchAddressActivityModule {
    private Context mContext;
    private ISerchAddressView mISerchAddressView;

    public SerchAddressActivityModule(ISerchAddressView iSerchAddressView, Context context) {
        mISerchAddressView = iSerchAddressView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ISerchAddressView iSerchAddressView() {
        return mISerchAddressView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ISerchAddressModel iSerchAddressModel() {
        return new SerchAddressModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    SerchAddressPresenter SerchAddressPresenter(ISerchAddressView iSerchAddressView, ISerchAddressModel iSerchAddressModel) {
        return new SerchAddressPresenter(iSerchAddressView, iSerchAddressModel);
    }
}
