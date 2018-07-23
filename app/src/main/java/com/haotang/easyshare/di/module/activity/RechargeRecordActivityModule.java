package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.RechargeRecordModel;
import com.haotang.easyshare.mvp.model.imodel.IRechargeRecordModel;
import com.haotang.easyshare.mvp.presenter.RechargeRecordPresenter;
import com.haotang.easyshare.mvp.view.iview.IRechargeRecordView;
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
 * @date XJ on 2018/7/23 17:27
 */
@Module
public class RechargeRecordActivityModule {
    private Context mContext;
    private IRechargeRecordView mIRechargeRecordView;

    public RechargeRecordActivityModule(IRechargeRecordView iRechargeRecordView, Context context) {
        mIRechargeRecordView = iRechargeRecordView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IRechargeRecordView iRechargeRecordView() {
        return mIRechargeRecordView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IRechargeRecordModel iRechargeRecordModel() {
        return new RechargeRecordModel();
    }

    @Provides
    @ActivityScope
    RechargeRecordPresenter RechargeRecordPresenter(IRechargeRecordView iRechargeRecordView, IRechargeRecordModel iRechargeRecordModel) {
        return new RechargeRecordPresenter(iRechargeRecordView, iRechargeRecordModel);
    }
}
