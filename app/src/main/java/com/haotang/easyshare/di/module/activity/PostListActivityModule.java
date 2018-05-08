package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.PostListModel;
import com.haotang.easyshare.mvp.model.imodel.IPostListModel;
import com.haotang.easyshare.mvp.presenter.PostListPresenter;
import com.haotang.easyshare.mvp.view.iview.IPostListView;
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
 * @date XJ on 2018/5/8 16:53
 */
@Module
public class PostListActivityModule {
    private Context mContext;
    private IPostListView mIPostListView;

    public PostListActivityModule(IPostListView iPostListView, Context context) {
        mIPostListView = iPostListView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IPostListView iPostListView() {
        return mIPostListView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IPostListModel iPostListModel() {
        return new PostListModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    PostListPresenter PostListPresenter(IPostListView iPostListView, IPostListModel iPostListModel) {
        return new PostListPresenter(iPostListView, iPostListModel);
    }
}
