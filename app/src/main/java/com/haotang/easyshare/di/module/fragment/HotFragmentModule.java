package com.haotang.easyshare.di.module.fragment;

import android.content.Context;

import com.haotang.easyshare.mvp.model.HotFragmentModel;
import com.haotang.easyshare.mvp.model.imodel.IHotFragmentModel;
import com.haotang.easyshare.mvp.presenter.HotFragmentPresenter;
import com.haotang.easyshare.mvp.view.iview.IHotFragmentView;
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
 * @date XJ on 2018/5/7 18:31
 */
@Module
public class HotFragmentModule {
    private IHotFragmentView mIHotFragmentView;
    private Context mContext;

    public HotFragmentModule(IHotFragmentView mIHotFragmentView, Context context) {
        this.mIHotFragmentView = mIHotFragmentView;
        mContext = context;
    }

    @Provides
    @FragmentScope
    IHotFragmentView iHotFragmentView() {
        return mIHotFragmentView;
    }

    @Provides
    @FragmentScope
    IHotFragmentModel iHotFragmentModel() {
        return new HotFragmentModel();
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
    HotFragmentPresenter HotFragmentPresenter(IHotFragmentView IHotFragmentView, IHotFragmentModel iHotFragmentModel) {
        return new HotFragmentPresenter(IHotFragmentView, iHotFragmentModel);
    }
}
