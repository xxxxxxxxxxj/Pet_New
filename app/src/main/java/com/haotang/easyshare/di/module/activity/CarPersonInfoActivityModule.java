package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.CarPersonInfoModel;
import com.haotang.easyshare.mvp.model.imodel.ICarPersonInfoModel;
import com.haotang.easyshare.mvp.presenter.CarPersonInfoPresenter;
import com.haotang.easyshare.mvp.view.iview.ICarPersonInfoView;
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
 * @date zhoujunxia on 2018/9/4 15:49
 */
@Module
public class CarPersonInfoActivityModule {
    private Context mContext;
    private ICarPersonInfoView mICarPersonInfoView;

    public CarPersonInfoActivityModule(ICarPersonInfoView iCarPersonInfoView, Context context) {
        mICarPersonInfoView = iCarPersonInfoView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ICarPersonInfoView iCarPersonInfoView() {
        return mICarPersonInfoView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ICarPersonInfoModel iCarPersonInfoModel() {
        return new CarPersonInfoModel();
    }

    @Provides
    @ActivityScope
    CarPersonInfoPresenter CarPersonInfoPresenter(ICarPersonInfoView iCarPersonInfoView, ICarPersonInfoModel iCarPersonInfoModel) {
        return new CarPersonInfoPresenter(iCarPersonInfoView, iCarPersonInfoModel);
    }
}
