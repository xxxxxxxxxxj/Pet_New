package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.MyCouponModel;
import com.haotang.easyshare.mvp.model.imodel.IMyCouponModel;
import com.haotang.easyshare.mvp.presenter.MyCouponPresenter;
import com.haotang.easyshare.mvp.view.iview.IMyCouponView;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/29 11:40
 */
@Module
public class MyCouponActivityModule {
    private Context mContext;
    private IMyCouponView mIMyCouponView;

    public MyCouponActivityModule(IMyCouponView iMyCouponView, Context context) {
        mIMyCouponView = iMyCouponView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IMyCouponView iMyCouponView() {
        return mIMyCouponView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IMyCouponModel iMyCouponModel() {
        return new MyCouponModel();
    }

    @Provides
    @ActivityScope
    MyCouponPresenter MyCouponPresenter(IMyCouponView iMyCouponView, IMyCouponModel iMyCouponModel) {
        return new MyCouponPresenter(iMyCouponView, iMyCouponModel);
    }
}
