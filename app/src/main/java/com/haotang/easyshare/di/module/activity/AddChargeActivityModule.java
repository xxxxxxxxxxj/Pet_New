package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.AddChargeModel;
import com.haotang.easyshare.mvp.model.imodel.IAddChargeModel;
import com.haotang.easyshare.mvp.presenter.AddChargePresenter;
import com.haotang.easyshare.mvp.view.iview.IAddChargeView;
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
 * @date XJ on 2018/5/7 16:46
 */
@Module
public class AddChargeActivityModule {
    private Context mContext;
    private IAddChargeView mIAddChargeView;

    public AddChargeActivityModule(IAddChargeView iAddChargeView, Context context) {
        mIAddChargeView = iAddChargeView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IAddChargeView iAddChargeView() {
        return mIAddChargeView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IAddChargeModel iAddChargeModel() {
        return new AddChargeModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    AddChargePresenter AddChargePresenter(IAddChargeView iAddChargeView, IAddChargeModel iAddChargeModel) {
        return new AddChargePresenter(iAddChargeView, iAddChargeModel);
    }
}
