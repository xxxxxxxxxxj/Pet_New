package com.haotang.newpet.mvp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haotang.newpet.R;
import com.haotang.newpet.di.component.activity.DaggerFlashActivityCommponent;
import com.haotang.newpet.di.module.activity.FlashActivityModule;
import com.haotang.newpet.mvp.model.entity.res.FlashBean;
import com.haotang.newpet.mvp.presenter.FlashPresenter;
import com.haotang.newpet.mvp.view.activity.base.BaseActivity;
import com.haotang.newpet.mvp.view.iview.IFlashView;
import com.haotang.newpet.mvp.view.widget.PermissionDialog;
import com.haotang.newpet.util.CountdownUtil;
import com.haotang.newpet.util.StringUtil;
import com.haotang.newpet.util.SystemTypeUtil;
import com.haotang.newpet.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.other.permission.PermissionListener;
import com.ljy.devring.util.RingToast;

import javax.inject.Inject;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:欢迎页</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 17:47
 */
public class FlashActivity extends BaseActivity<FlashPresenter> implements IFlashView {
    private final static String TAG = FlashActivity.class.getSimpleName();
    private String backup;
    private String imgUrl;
    private String jumpUrl;
    private int point;
    @Inject
    PermissionDialog permissionDialog;

    @Override
    public void getFlashSuccess(FlashBean flashBean) {
        if (flashBean != null) {
            backup = flashBean.getBackup();
            imgUrl = flashBean.getImgUrl();
            jumpUrl = flashBean.getJumpUrl();
            point = flashBean.getPoint();
            if(StringUtil.isNotEmpty(imgUrl)){
                initTimer(1);
            }else{
                initTimer(0);
            }
        } else {
            initTimer(0);
        }
    }

    @Override
    public void getFlashFail(int status, String desc) {
        initTimer(0);
        RingLog.e(TAG, "FlashActivity getFlashFail() status = " + status + "---desc = " + desc);
        RingToast.show("FlashActivity getFlashFail() status = " + status + "---desc = " + desc);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_flash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //使用Dagger2对本类中相关变量进行初始化
        DaggerFlashActivityCommponent.builder().flashActivityModule(new FlashActivityModule(this, this)).build().inject(this);
        DevRing.activityStackManager().pushOneActivity(this);
        permissionDialog.setMessage(R.string.permission_request_READ_PHONE_STATE);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        SystemUtil.hideBottomUIMenu(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //申请必要权限
        DevRing.permissionManager().requestEach(FlashActivity.this, new PermissionListener() {
            @Override
            public void onGranted(String permissionName) {
                //全部权限都被授予的话，则弹出底部选项
                if (DevRing.cacheManager().spCache().getBoolean("guide", false)) {
                    mPresenter.startPageConfig(FlashActivity.this);
                } else {
                    initTimer(0);
                }
            }

            @Override
            public void onDenied(String permissionName) {
                //如果用户拒绝了其中一个授权请求，则提醒用户
                RingToast.show(R.string.permission_request_READ_PHONE_STATE);
                if (DevRing.cacheManager().spCache().getBoolean("guide", false)) {
                    mPresenter.startPageConfig(FlashActivity.this);
                } else {
                    initTimer(0);
                }
            }

            @Override
            public void onDeniedWithNeverAsk(String permissionName) {
                //如果用户拒绝了其中一个授权请求，且勾选了不再提醒，则需要引导用户到权限管理页面开启
                permissionDialog.show();
            }
        }, Manifest.permission.READ_PHONE_STATE);
    }

    private void initTimer(final int flag) {
        CountdownUtil.getInstance().newTimer(3000, 1000, new CountdownUtil.ICountDown() {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (DevRing.cacheManager().spCache().getBoolean("guide", false)) {
                    if (flag == 1) {
                        goNext(StartPageActivity.class);
                    } else {
                        goNext(MainActivity.class);
                    }
                } else {
                    goNext(GuideActivity.class);
                }
            }
        }, "FLASH_TIMER");
    }

    private void goNext(Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("img_url", imgUrl);
        intent.putExtra("jump_url", jumpUrl);
        intent.putExtra("backup", backup);
        intent.putExtra("point", point);
        startActivity(intent);
        finish();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountdownUtil.getInstance().cancel("FLASH_TIMER");
    }

    @Override
    protected void initEvent() {
        permissionDialog.setPositiveButton(R.string.permission_request_dialog_pos, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
                if (DevRing.cacheManager().spCache().getBoolean("guide", false)) {
                    mPresenter.startPageConfig(FlashActivity.this);
                } else {
                    initTimer(0);
                }
                SystemTypeUtil.goToPermissionManager(FlashActivity.this);
            }
        });
        permissionDialog.setNegativeButton(R.string.permission_request_dialog_nav, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
                if (DevRing.cacheManager().spCache().getBoolean("guide", false)) {
                    mPresenter.startPageConfig(FlashActivity.this);
                } else {
                    initTimer(0);
                }
            }
        });
    }
}
