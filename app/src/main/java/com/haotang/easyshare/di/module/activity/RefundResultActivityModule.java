package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.RefundResultModel;
import com.haotang.easyshare.mvp.model.imodel.IRefundResultModel;
import com.haotang.easyshare.mvp.presenter.RefundResultPresenter;
import com.haotang.easyshare.mvp.view.iview.IRefundResultView;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 16:24
 */
@Module
public class RefundResultActivityModule {
    private Context mContext;
    private IRefundResultView mIRefundResultView;

    public RefundResultActivityModule(IRefundResultView iRefundResultView, Context context) {
        mIRefundResultView = iRefundResultView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IRefundResultView iRefundResultView() {
        return mIRefundResultView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IRefundResultModel iRefundResultModel() {
        return new RefundResultModel();
    }

    @Provides
    @ActivityScope
    RefundResultPresenter RefundResultPresenter(IRefundResultView iRefundResultView, IRefundResultModel iRefundResultModel) {
        return new RefundResultPresenter(iRefundResultView, iRefundResultModel);
    }
}
