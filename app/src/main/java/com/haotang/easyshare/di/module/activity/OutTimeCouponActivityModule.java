package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.OutTimeCouponModel;
import com.haotang.easyshare.mvp.model.imodel.IOutTimeCouponModel;
import com.haotang.easyshare.mvp.presenter.OutTimeCouponPresenter;
import com.haotang.easyshare.mvp.view.iview.IOutTimeCouponView;
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
 * @date zhoujunxia on 2018/9/21 16:45
 */
@Module
public class OutTimeCouponActivityModule {
    private Context mContext;
    private IOutTimeCouponView mIOutTimeCouponView;

    public OutTimeCouponActivityModule(IOutTimeCouponView iOutTimeCouponView, Context context) {
        mIOutTimeCouponView = iOutTimeCouponView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IOutTimeCouponView iOutTimeCouponView() {
        return mIOutTimeCouponView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IOutTimeCouponModel iOutTimeCouponModel() {
        return new OutTimeCouponModel();
    }

    @Provides
    @ActivityScope
    OutTimeCouponPresenter OutTimeCouponPresenter(IOutTimeCouponView iOutTimeCouponView, IOutTimeCouponModel iOutTimeCouponModel) {
        return new OutTimeCouponPresenter(iOutTimeCouponView, iOutTimeCouponModel);
    }
}
