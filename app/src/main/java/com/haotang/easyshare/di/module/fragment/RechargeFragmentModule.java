package com.haotang.easyshare.di.module.fragment;

import android.content.Context;

import com.haotang.easyshare.mvp.model.RechargeFragmentModel;
import com.haotang.easyshare.mvp.model.imodel.IRechargeFragmentModel;
import com.haotang.easyshare.mvp.presenter.RechargeFragmentPresenter;
import com.haotang.easyshare.mvp.view.iview.IRechargeFragmentView;
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
 * @date XJ on 2018/7/25 12:07
 */
@Module
public class RechargeFragmentModule {
    private IRechargeFragmentView mIRechargeFragmentView;
    private Context mContext;

    public RechargeFragmentModule(IRechargeFragmentView mIRechargeFragmentView, Context context) {
        this.mIRechargeFragmentView = mIRechargeFragmentView;
        mContext = context;
    }

    @Provides
    @FragmentScope
    IRechargeFragmentView iRechargeFragmentView() {
        return mIRechargeFragmentView;
    }

    @Provides
    @FragmentScope
    IRechargeFragmentModel iRechargeFragmentModel() {
        return new RechargeFragmentModel();
    }

    @Provides
    @FragmentScope
    Context context() {
        return mContext;
    }

    @Provides
    @FragmentScope
    RechargeFragmentPresenter RechargeFragmentPresenter(IRechargeFragmentView IRechargeFragmentView, IRechargeFragmentModel iRechargeFragmentModel) {
        return new RechargeFragmentPresenter(IRechargeFragmentView, iRechargeFragmentModel);
    }
}
