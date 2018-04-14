package com.haotang.newpet.mvp.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.haotang.newpet.R;
import com.haotang.newpet.di.component.activity.DaggerMainActivityCommponent;
import com.haotang.newpet.di.module.activity.MainActivityModule;
import com.haotang.newpet.mvp.model.entity.res.BootmBarBean;
import com.haotang.newpet.mvp.model.entity.res.LastVersionBean;
import com.haotang.newpet.mvp.model.entity.table.MovieCollect;
import com.haotang.newpet.mvp.presenter.MainPresenter;
import com.haotang.newpet.mvp.view.activity.base.BaseActivity;
import com.haotang.newpet.mvp.view.iview.IMainView;
import com.haotang.newpet.mvp.view.widget.PermissionDialog;
import com.haotang.newpet.util.SystemUtil;
import com.haotang.newpet.util.UpdateUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.other.permission.PermissionListener;
import com.ljy.devring.util.RingToast;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;

/**
 * 首页
 */
public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {
    private final static String TAG = MainActivity.class.getSimpleName();
    @BindString(R.string.exit_confirm)
    String mStrExitConfirm;
    @BindView(R.id.vp_mainactivity)
    ViewPager vpMainactivity;
    @BindView(R.id.ctl_mainactivity)
    CommonTabLayout ctlMainactivity;
    private long mExitTime;
    @Inject
    PermissionDialog permissionDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //使用Dagger2对本类中相关变量进行初始化
        DaggerMainActivityCommponent.builder().mainActivityModule(new MainActivityModule(this, this)).build().inject(this);
        permissionDialog.setMessage(R.string.permission_request_WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        DevRing.cacheManager().spCache().put("guide", true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.getBottomBar(MainActivity.this);
        //申请必要权限
        DevRing.permissionManager().requestEach(MainActivity.this, new PermissionListener() {
            @Override
            public void onGranted(String permissionName) {
                //全部权限都被授予的话，则弹出底部选项
                mPresenter.getLatestVersion(MainActivity.this, 2, SystemUtil.getCurrentVersion(MainActivity.this), String.valueOf(System.currentTimeMillis()));
            }

            @Override
            public void onDenied(String permissionName) {
                //如果用户拒绝了其中一个授权请求，则提醒用户
                RingToast.show(R.string.permission_request_WRITE_EXTERNAL_STORAGE);
            }

            @Override
            public void onDeniedWithNeverAsk(String permissionName) {
                //如果用户拒绝了其中一个授权请求，且勾选了不再提醒，则需要引导用户到权限管理页面开启
                permissionDialog.show();
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void initEvent() {
        permissionDialog.setPositiveButton(R.string.permission_request_dialog_pos);
        permissionDialog.setNegativeButton(R.string.permission_request_dialog_nav);
    }

    @Subscribe
    public void onDeleteMovie(MovieCollect movieCollect) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (System.currentTimeMillis() - mExitTime > 2000) {
                    RingToast.show(mStrExitConfirm);
                    mExitTime = System.currentTimeMillis();
                } else {
                    DevRing.activityStackManager().exitApplication();
                    Process.killProcess(Process.myPid());
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void getLatestVersionSuccess(LastVersionBean lastVersionBean) {
        RingLog.d(TAG, "lastVersionBean = " + lastVersionBean);
        if (lastVersionBean != null) {
            String downloadPath = lastVersionBean.getDownload();
            int isUpgrade = lastVersionBean.getMandate();
            String latestVersion = lastVersionBean.getNversion();
            String versionHint = lastVersionBean.getText();
            if (latestVersion != null
                    && !TextUtils.isEmpty(latestVersion)) {
                boolean isLatest = UpdateUtil
                        .compareVersion(
                                latestVersion,
                                SystemUtil.getCurrentVersion(MainActivity.this));
                if (isLatest) {// 需要下载安装最新版
                    if (downloadPath != null
                            && !TextUtils
                            .isEmpty(downloadPath)) {
                        if (isUpgrade == 1) {
                            // 强制升级
                            UpdateUtil.showForceUpgradeDialog(MainActivity.this, versionHint,
                                    downloadPath,
                                    latestVersion);
                        } else if (isUpgrade == 0) {
                            // 非强制升级
                            UpdateUtil.showUpgradeDialog(MainActivity.this, versionHint,
                                    downloadPath,
                                    latestVersion);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void getLatestVersionFail(int status, String desc) {
        RingLog.e(TAG, "MainActivity getLatestVersionFail() status = " + status + "---desc = " + desc);
        RingToast.show("MainActivity getLatestVersionFail() status = " + status + "---desc = " + desc);
    }

    @Override
    public void getBootmBarFail(int status, String desc) {
        RingLog.e(TAG, "MainActivity getBootmBarFail() status = " + status + "---desc = " + desc);
        RingToast.show("MainActivity getBootmBarFail() status = " + status + "---desc = " + desc);
    }

    @Override
    public void getBootmBarSuccess(BootmBarBean bootmBarBean) {
        if (bootmBarBean != null) {
            BootmBarBean.IndexBean index = bootmBarBean.getIndex();
            String mallRedPoint = bootmBarBean.getMallRedPoint();
            if (index != null) {
                BootmBarBean.IndexBean.BottomBean bottom = index.getBottom();
                if (bottom != null) {
                    List<BootmBarBean.IndexBean.BottomBean.ListBean> list = bottom.getList();
                    if (list != null && list.size() > 0) {

                    }
                }
            }
        }
    }
}
