package com.haotang.easyshare.di.module.fragment;

import android.content.Context;

import com.haotang.easyshare.mvp.model.ChargeIngFragmentModel;
import com.haotang.easyshare.mvp.model.imodel.IChargeIngFragmentModel;
import com.haotang.easyshare.mvp.presenter.ChargeIngFragmentPresenter;
import com.haotang.easyshare.mvp.view.iview.IChargeIngFragmentView;
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
 * @date XJ on 2018/7/20 14:55
 */
@Module
public class ChargeIngFragmentModule {
    private IChargeIngFragmentView mIChargeIngFragmentView;
    private Context mContext;

    public ChargeIngFragmentModule(IChargeIngFragmentView mIChargeIngFragmentView, Context context) {
        this.mIChargeIngFragmentView = mIChargeIngFragmentView;
        mContext = context;
    }

    @Provides
    @FragmentScope
    IChargeIngFragmentView iChargeIngFragmentView() {
        return mIChargeIngFragmentView;
    }

    @Provides
    @FragmentScope
    IChargeIngFragmentModel iChargeIngFragmentModel() {
        return new ChargeIngFragmentModel();
    }

    @Provides
    @FragmentScope
    Context context() {
        return mContext;
    }

    @Provides
    @FragmentScope
    ChargeIngFragmentPresenter ChargeIngFragmentPresenter(IChargeIngFragmentView IChargeIngFragmentView, IChargeIngFragmentModel iChargeIngFragmentModel) {
        return new ChargeIngFragmentPresenter(IChargeIngFragmentView, iChargeIngFragmentModel);
    }
}
