package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.CommentDetailModel;
import com.haotang.easyshare.mvp.model.imodel.ICommentDetailModel;
import com.haotang.easyshare.mvp.presenter.CommentDetailPresenter;
import com.haotang.easyshare.mvp.view.iview.ICommentDetailView;
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
 * @date XJ on 2018/5/7 17:39
 */
@Module
public class CommentDetailActivityModule {
    private Context mContext;
    private ICommentDetailView mICommentDetailView;

    public CommentDetailActivityModule(ICommentDetailView iCommentDetailView, Context context) {
        mICommentDetailView = iCommentDetailView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ICommentDetailView iCommentDetailView() {
        return mICommentDetailView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ICommentDetailModel iCommentDetailModel() {
        return new CommentDetailModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    CommentDetailPresenter CommentDetailPresenter(ICommentDetailView iCommentDetailView, ICommentDetailModel iCommentDetailModel) {
        return new CommentDetailPresenter(iCommentDetailView, iCommentDetailModel);
    }
}
