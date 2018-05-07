package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.SendPostModel;
import com.haotang.easyshare.mvp.model.imodel.ISendPostModel;
import com.haotang.easyshare.mvp.presenter.SendPostPresenter;
import com.haotang.easyshare.mvp.view.iview.ISendPostView;
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
 * @date XJ on 2018/5/7 18:09
 */
@Module
public class SendPostActivityModule {
    private Context mContext;
    private ISendPostView mISendPostView;

    public SendPostActivityModule(ISendPostView iSendPostView, Context context) {
        mISendPostView = iSendPostView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ISendPostView iSendPostView() {
        return mISendPostView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ISendPostModel iSendPostModel() {
        return new SendPostModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    SendPostPresenter SendPostPresenter(ISendPostView iSendPostView, ISendPostModel iSendPostModel) {
        return new SendPostPresenter(iSendPostView, iSendPostModel);
    }
}
