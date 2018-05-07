package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.ChargingPileDetailModel;
import com.haotang.easyshare.mvp.model.imodel.IChargingPileDetailModel;
import com.haotang.easyshare.mvp.presenter.ChargingPileDetailPresenter;
import com.haotang.easyshare.mvp.view.iview.IChargingPileDetailView;
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
 * @date XJ on 2018/5/7 17:20
 */
@Module
public class ChargingPileDetailActivityModule {
    private Context mContext;
    private IChargingPileDetailView mIChargingPileDetailView;

    public ChargingPileDetailActivityModule(IChargingPileDetailView iChargingPileDetailView, Context context) {
        mIChargingPileDetailView = iChargingPileDetailView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IChargingPileDetailView iChargingPileDetailView() {
        return mIChargingPileDetailView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IChargingPileDetailModel iChargingPileDetailModel() {
        return new ChargingPileDetailModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    ChargingPileDetailPresenter ChargingPileDetailPresenter(IChargingPileDetailView iChargingPileDetailView, IChargingPileDetailModel iChargingPileDetailModel) {
        return new ChargingPileDetailPresenter(iChargingPileDetailView, iChargingPileDetailModel);
    }
}
