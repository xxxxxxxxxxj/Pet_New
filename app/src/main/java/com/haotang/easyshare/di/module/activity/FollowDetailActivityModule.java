package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.FollowDetailModel;
import com.haotang.easyshare.mvp.model.imodel.IFollowDetailModel;
import com.haotang.easyshare.mvp.presenter.FollowDetailPresenter;
import com.haotang.easyshare.mvp.view.iview.IFollowDetailView;
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
 * @date XJ on 2018/5/7 17:45
 */
@Module
public class FollowDetailActivityModule {
    private Context mContext;
    private IFollowDetailView mIFollowDetailView;

    public FollowDetailActivityModule(IFollowDetailView iFollowDetailView, Context context) {
        mIFollowDetailView = iFollowDetailView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IFollowDetailView iFollowDetailView() {
        return mIFollowDetailView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IFollowDetailModel iFollowDetailModel() {
        return new FollowDetailModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    FollowDetailPresenter FollowDetailPresenter(IFollowDetailView iFollowDetailView, IFollowDetailModel iFollowDetailModel) {
        return new FollowDetailPresenter(iFollowDetailView, iFollowDetailModel);
    }
}
