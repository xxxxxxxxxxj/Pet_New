package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.RechargeModel;
import com.haotang.easyshare.mvp.model.imodel.IRechargeModel;
import com.haotang.easyshare.mvp.presenter.RechargePresenter;
import com.haotang.easyshare.mvp.view.iview.IRechargeView;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 16:48
 */
@Module
public class RechargeActivityModule {
    private Context mContext;
    private IRechargeView mIRechargeView;

    public RechargeActivityModule(IRechargeView iRechargeView, Context context) {
        mIRechargeView = iRechargeView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IRechargeView iRechargeView() {
        return mIRechargeView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IRechargeModel iRechargeModel() {
        return new RechargeModel();
    }

    @Provides
    @ActivityScope
    RechargePresenter RechargePresenter(IRechargeView iRechargeView, IRechargeModel iRechargeModel) {
        return new RechargePresenter(iRechargeView, iRechargeModel);
    }
}
