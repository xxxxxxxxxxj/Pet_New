package com.haotang.easyshare.di.module.fragment;

import android.content.Context;

import com.haotang.easyshare.mvp.model.MyFragmentModel;
import com.haotang.easyshare.mvp.model.imodel.IMyFragmentModel;
import com.haotang.easyshare.mvp.presenter.MyFragmentPresenter;
import com.haotang.easyshare.mvp.view.iview.IMyFragmentView;
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
 * @date XJ on 2018/5/7 18:35
 */
@Module
public class MyFragmentModule {
    private IMyFragmentView mIMyFragmentView;
    private Context mContext;

    public MyFragmentModule(IMyFragmentView mIMyFragmentView, Context context) {
        this.mIMyFragmentView = mIMyFragmentView;
        mContext = context;
    }

    @Provides
    @FragmentScope
    IMyFragmentView iMyFragmentView() {
        return mIMyFragmentView;
    }

    @Provides
    @FragmentScope
    IMyFragmentModel iMyFragmentModel() {
        return new MyFragmentModel();
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
    MyFragmentPresenter MyFragmentPresenter(IMyFragmentView IMyFragmentView, IMyFragmentModel iMyFragmentModel) {
        return new MyFragmentPresenter(IMyFragmentView, iMyFragmentModel);
    }
}
