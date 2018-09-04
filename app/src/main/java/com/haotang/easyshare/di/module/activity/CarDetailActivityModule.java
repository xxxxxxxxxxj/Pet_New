package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.CarDetailModel;
import com.haotang.easyshare.mvp.model.imodel.ICarDetailModel;
import com.haotang.easyshare.mvp.presenter.CarDetailPresenter;
import com.haotang.easyshare.mvp.view.iview.ICarDetailView;
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
 * @date zhoujunxia on 2018/9/4 12:57
 */
@Module
public class CarDetailActivityModule {
    private Context mContext;
    private ICarDetailView mICarDetailView;

    public CarDetailActivityModule(ICarDetailView iCarDetailView, Context context) {
        mICarDetailView = iCarDetailView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ICarDetailView iCarDetailView() {
        return mICarDetailView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ICarDetailModel iCarDetailModel() {
        return new CarDetailModel();
    }

    @Provides
    @ActivityScope
    CarDetailPresenter CarDetailPresenter(ICarDetailView iCarDetailView, ICarDetailModel iCarDetailModel) {
        return new CarDetailPresenter(iCarDetailView, iCarDetailModel);
    }
}
