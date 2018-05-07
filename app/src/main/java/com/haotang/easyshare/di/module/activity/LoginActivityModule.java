package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.LoginModel;
import com.haotang.easyshare.mvp.model.imodel.ILoginModel;
import com.haotang.easyshare.mvp.presenter.LoginPresenter;
import com.haotang.easyshare.mvp.view.iview.ILoginView;
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
 * @date XJ on 2018/5/7 17:54
 */
@Module
public class LoginActivityModule {
    private Context mContext;
    private ILoginView mILoginView;

    public LoginActivityModule(ILoginView iLoginView, Context context) {
        mILoginView = iLoginView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ILoginView iLoginView() {
        return mILoginView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ILoginModel iLoginModel() {
        return new LoginModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    LoginPresenter LoginPresenter(ILoginView iLoginView, ILoginModel iLoginModel) {
        return new LoginPresenter(iLoginView, iLoginModel);
    }
}
