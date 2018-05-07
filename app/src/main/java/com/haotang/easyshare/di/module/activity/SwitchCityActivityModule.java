package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.SwitchCityModel;
import com.haotang.easyshare.mvp.model.imodel.ISwitchCityModel;
import com.haotang.easyshare.mvp.presenter.SwitchCityPresenter;
import com.haotang.easyshare.mvp.view.iview.ISwitchCityView;
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
 * @date XJ on 2018/5/7 18:14
 */
@Module
public class SwitchCityActivityModule {
    private Context mContext;
    private ISwitchCityView mISwitchCityView;

    public SwitchCityActivityModule(ISwitchCityView iSwitchCityView, Context context) {
        mISwitchCityView = iSwitchCityView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ISwitchCityView iSwitchCityView() {
        return mISwitchCityView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ISwitchCityModel iSwitchCityModel() {
        return new SwitchCityModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    SwitchCityPresenter SwitchCityPresenter(ISwitchCityView iSwitchCityView, ISwitchCityModel iSwitchCityModel) {
        return new SwitchCityPresenter(iSwitchCityView, iSwitchCityModel);
    }
}
