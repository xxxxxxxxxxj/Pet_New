package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.MyPostModel;
import com.haotang.easyshare.mvp.model.imodel.IMyPostModel;
import com.haotang.easyshare.mvp.presenter.MyPostPresenter;
import com.haotang.easyshare.mvp.view.iview.IMyPostView;
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
 * @date XJ on 2018/5/7 18:06
 */
@Module
public class MyPostActivityModule {
    private Context mContext;
    private IMyPostView mIMyPostView;

    public MyPostActivityModule(IMyPostView iMyPostView, Context context) {
        mIMyPostView = iMyPostView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IMyPostView iMyPostView() {
        return mIMyPostView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IMyPostModel iMyPostModel() {
        return new MyPostModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    MyPostPresenter MyPostPresenter(IMyPostView iMyPostView, IMyPostModel iMyPostModel) {
        return new MyPostPresenter(iMyPostView, iMyPostModel);
    }
}
