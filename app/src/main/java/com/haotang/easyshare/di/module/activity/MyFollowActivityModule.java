package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.MyFollowModel;
import com.haotang.easyshare.mvp.model.imodel.IMyFollowModel;
import com.haotang.easyshare.mvp.presenter.MyFollowPresenter;
import com.haotang.easyshare.mvp.view.iview.IMyFollowView;
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
 * @date XJ on 2018/5/7 18:02
 */
@Module
public class MyFollowActivityModule {
    private Context mContext;
    private IMyFollowView mIMyFollowView;

    public MyFollowActivityModule(IMyFollowView iMyFollowView, Context context) {
        mIMyFollowView = iMyFollowView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IMyFollowView iMyFollowView() {
        return mIMyFollowView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IMyFollowModel iMyFollowModel() {
        return new MyFollowModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    MyFollowPresenter MyFollowPresenter(IMyFollowView iMyFollowView, IMyFollowModel iMyFollowModel) {
        return new MyFollowPresenter(iMyFollowView, iMyFollowModel);
    }
}
