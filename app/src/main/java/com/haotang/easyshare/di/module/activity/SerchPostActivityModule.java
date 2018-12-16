package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.SerchPostModel;
import com.haotang.easyshare.mvp.model.imodel.ISerchPostModel;
import com.haotang.easyshare.mvp.presenter.SerchPostPresenter;
import com.haotang.easyshare.mvp.view.iview.ISerchPostView;
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
 * @date zhoujunxia on 2018/12/16 10:03
 */
@Module
public class SerchPostActivityModule {
    private Context mContext;
    private ISerchPostView mISerchPostView;

    public SerchPostActivityModule(ISerchPostView iSerchPostView, Context context) {
        mISerchPostView = iSerchPostView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ISerchPostView iSerchPostView() {
        return mISerchPostView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ISerchPostModel iSerchPostModel() {
        return new SerchPostModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    SerchPostPresenter SerchPostPresenter(ISerchPostView iSerchPostView, ISerchPostModel iSerchPostModel) {
        return new SerchPostPresenter(iSerchPostView, iSerchPostModel);
    }
}
