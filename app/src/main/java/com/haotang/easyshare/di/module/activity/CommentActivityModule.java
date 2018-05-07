package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.CommentModel;
import com.haotang.easyshare.mvp.model.imodel.ICommentModel;
import com.haotang.easyshare.mvp.presenter.CommentPresenter;
import com.haotang.easyshare.mvp.view.iview.ICommentView;
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
 * @date XJ on 2018/5/7 17:34
 */
@Module
public class CommentActivityModule {
    private Context mContext;
    private ICommentView mICommentView;

    public CommentActivityModule(ICommentView iCommentView, Context context) {
        mICommentView = iCommentView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ICommentView iCommentView() {
        return mICommentView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ICommentModel iCommentModel() {
        return new CommentModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    CommentPresenter CommentPresenter(ICommentView iCommentView, ICommentModel iCommentModel) {
        return new CommentPresenter(iCommentView, iCommentModel);
    }
}
