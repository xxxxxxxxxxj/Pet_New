package com.haotang.easyshare.di.module.fragment;

import android.content.Context;

import com.haotang.easyshare.mvp.model.CurrentMessageFragmentModel;
import com.haotang.easyshare.mvp.model.imodel.ICurrentMessageFragmentModel;
import com.haotang.easyshare.mvp.presenter.CurrentMessageFragmentPresenter;
import com.haotang.easyshare.mvp.view.iview.ICurrentMessageFragmentView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:23
 */
@Module
public class CurrentMessageFragmentModule {
    private ICurrentMessageFragmentView mICurrentMessageFragmentView;
    private Context mContext;

    public CurrentMessageFragmentModule(ICurrentMessageFragmentView mICurrentMessageFragmentView, Context context) {
        this.mICurrentMessageFragmentView = mICurrentMessageFragmentView;
        mContext = context;
    }

    @Provides
    @FragmentScope
    ICurrentMessageFragmentView iCurrentMessageFragmentView() {
        return mICurrentMessageFragmentView;
    }

    @Provides
    @FragmentScope
    ICurrentMessageFragmentModel iCurrentMessageFragmentModel() {
        return new CurrentMessageFragmentModel();
    }

    @Provides
    @FragmentScope
    Context context() {
        return mContext;
    }

    @Provides
    @FragmentScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @FragmentScope
    CurrentMessageFragmentPresenter CurrentMessageFragmentPresenter(ICurrentMessageFragmentView ICurrentMessageFragmentView, ICurrentMessageFragmentModel iCurrentMessageFragmentModel) {
        return new CurrentMessageFragmentPresenter(ICurrentMessageFragmentView, iCurrentMessageFragmentModel);
    }
}
