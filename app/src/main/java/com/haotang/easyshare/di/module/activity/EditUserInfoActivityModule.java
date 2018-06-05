package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.EditUserInfoModel;
import com.haotang.easyshare.mvp.model.imodel.IEditUserInfoModel;
import com.haotang.easyshare.mvp.presenter.EditUserInfoPresenter;
import com.haotang.easyshare.mvp.view.iview.IEditUserInfoView;
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
 * @date XJ on 2018/5/23 18:56
 */
@Module
public class EditUserInfoActivityModule {
    private Context mContext;
    private IEditUserInfoView mIEditUserInfoView;

    public EditUserInfoActivityModule(IEditUserInfoView iEditUserInfoView, Context context) {
        mIEditUserInfoView = iEditUserInfoView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IEditUserInfoView iEditUserInfoView() {
        return mIEditUserInfoView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IEditUserInfoModel iEditUserInfoModel() {
        return new EditUserInfoModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    EditUserInfoPresenter EditUserInfoPresenter(IEditUserInfoView iEditUserInfoView, IEditUserInfoModel iEditUserInfoModel) {
        return new EditUserInfoPresenter(iEditUserInfoView, iEditUserInfoModel);
    }
}
