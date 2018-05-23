package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerEditUserInfoActivityCommponent;
import com.haotang.easyshare.di.module.activity.EditUserInfoActivityModule;
import com.haotang.easyshare.mvp.presenter.EditUserInfoPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IEditUserInfoView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

/**
 * 编辑用户资料界面
 */
public class EditUserInfoActivity extends BaseActivity<EditUserInfoPresenter> implements IEditUserInfoView {
    protected final static String TAG = EditUserInfoActivity.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerEditUserInfoActivityCommponent.builder().
                editUserInfoActivityModule(new EditUserInfoActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
