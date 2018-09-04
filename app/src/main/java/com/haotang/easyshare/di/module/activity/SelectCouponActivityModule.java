package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.SelectCouponModel;
import com.haotang.easyshare.mvp.model.imodel.ISelectCouponModel;
import com.haotang.easyshare.mvp.presenter.SelectCouponPresenter;
import com.haotang.easyshare.mvp.view.iview.ISelectCouponView;
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
 * @date zhoujunxia on 2018/9/4 16:52
 */
@Module
public class SelectCouponActivityModule {
    private Context mContext;
    private ISelectCouponView mISelectCouponView;

    public SelectCouponActivityModule(ISelectCouponView iSelectCouponView, Context context) {
        mISelectCouponView = iSelectCouponView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ISelectCouponView iSelectCouponView() {
        return mISelectCouponView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ISelectCouponModel iSelectCouponModel() {
        return new SelectCouponModel();
    }

    @Provides
    @ActivityScope
    SelectCouponPresenter SelectCouponPresenter(ISelectCouponView iSelectCouponView, ISelectCouponModel iSelectCouponModel) {
        return new SelectCouponPresenter(iSelectCouponView, iSelectCouponModel);
    }
}
