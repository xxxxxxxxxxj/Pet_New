package com.haotang.easyshare.di.module.fragment;

import android.content.Context;

import com.haotang.easyshare.mvp.model.SelectCarFragmentModel;
import com.haotang.easyshare.mvp.model.imodel.ISelectCarFragmentModel;
import com.haotang.easyshare.mvp.presenter.SelectCarFragmentPresenter;
import com.haotang.easyshare.mvp.view.iview.ISelectCarFragmentView;
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
 * @date XJ on 2018/7/20 15:01
 */
@Module
public class SelectCarFragmentModule {
    private ISelectCarFragmentView mISelectCarFragmentView;
    private Context mContext;

    public SelectCarFragmentModule(ISelectCarFragmentView mISelectCarFragmentView, Context context) {
        this.mISelectCarFragmentView = mISelectCarFragmentView;
        mContext = context;
    }

    @Provides
    @FragmentScope
    ISelectCarFragmentView iSelectCarFragmentView() {
        return mISelectCarFragmentView;
    }

    @Provides
    @FragmentScope
    ISelectCarFragmentModel iSelectCarFragmentModel() {
        return new SelectCarFragmentModel();
    }

    @Provides
    @FragmentScope
    Context context() {
        return mContext;
    }

    @Provides
    @FragmentScope
    SelectCarFragmentPresenter SelectCarFragmentPresenter(ISelectCarFragmentView ISelectCarFragmentView, ISelectCarFragmentModel iSelectCarFragmentModel) {
        return new SelectCarFragmentPresenter(ISelectCarFragmentView, iSelectCarFragmentModel);
    }
}
