package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.CarInfoModel;
import com.haotang.easyshare.mvp.model.imodel.ICarInfoModel;
import com.haotang.easyshare.mvp.presenter.CarInfoPresenter;
import com.haotang.easyshare.mvp.view.iview.ICarInfoView;
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
 * @date XJ on 2018/5/7 17:14
 */
@Module
public class CarInfoActivityModule {
    private Context mContext;
    private ICarInfoView mICarInfoView;

    public CarInfoActivityModule(ICarInfoView iCarInfoView, Context context) {
        mICarInfoView = iCarInfoView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ICarInfoView iCarInfoView() {
        return mICarInfoView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ICarInfoModel iCarInfoModel() {
        return new CarInfoModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    CarInfoPresenter CarInfoPresenter(ICarInfoView iCarInfoView, ICarInfoModel iCarInfoModel) {
        return new CarInfoPresenter(iCarInfoView, iCarInfoModel);
    }
}
