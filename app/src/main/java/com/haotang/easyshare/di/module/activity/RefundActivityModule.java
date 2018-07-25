package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.RefundModel;
import com.haotang.easyshare.mvp.model.imodel.IRefundModel;
import com.haotang.easyshare.mvp.presenter.RefundPresenter;
import com.haotang.easyshare.mvp.view.iview.IRefundView;
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
 * @date XJ on 2018/7/25 14:13
 */
@Module
public class RefundActivityModule {
    private Context mContext;
    private IRefundView mIRefundView;

    public RefundActivityModule(IRefundView iRefundView, Context context) {
        mIRefundView = iRefundView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IRefundView iRefundView() {
        return mIRefundView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IRefundModel iRefundModel() {
        return new RefundModel();
    }

    @Provides
    @ActivityScope
    RefundPresenter RefundPresenter(IRefundView iRefundView, IRefundModel iRefundModel) {
        return new RefundPresenter(iRefundView, iRefundModel);
    }
}
