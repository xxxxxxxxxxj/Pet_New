package com.haotang.easyshare.mvp.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerMainActivityCommponent;
import com.haotang.easyshare.di.module.activity.MainActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.BootmBarBean;
import com.haotang.easyshare.mvp.model.entity.res.ImageTabEntity;
import com.haotang.easyshare.mvp.model.entity.res.LastVersionBean;
import com.haotang.easyshare.mvp.model.entity.table.MovieCollect;
import com.haotang.easyshare.mvp.presenter.MainPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MainActivityPagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.MainFragment;
import com.haotang.easyshare.mvp.view.fragment.MyFragment;
import com.haotang.easyshare.mvp.view.fragment.PetCircleFragment;
import com.haotang.easyshare.mvp.view.fragment.ShopMarketFragment;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IMainView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UpdateUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.other.permission.PermissionListener;
import com.ljy.devring.util.RingToast;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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
    private String[] mTitles = {"宠物家", "商城", "宠圈", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_normal, R.mipmap.tab_order_normal,
            R.mipmap.tab_knowledge_normal, R.mipmap.tab_my_normal};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_passed, R.mipmap.tab_order_passed,
            R.mipmap.tab_knowledge_passed, R.mipmap.tab_my_passed};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    @Inject
    MainFragment mainFragment;
    @Inject
    ShopMarketFragment shopMarketFragment;
    @Inject
    PetCircleFragment petCircleFragment;
    @Inject
    MyFragment myFragment;
    private int currentTabIndex = 1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        //使用Dagger2对本类中相关变量进行初始化
        DaggerMainActivityCommponent.builder().mainActivityModule(new MainActivityModule(this, this)).build().inject(this);
        permissionDialog.setMessage(R.string.permission_request_WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        DevRing.cacheManager().spCache().put("guide", true);
        permissionDialog.setPositiveButton(R.string.permission_request_dialog_pos);
        permissionDialog.setNegativeButton(R.string.permission_request_dialog_nav);

        mFragments.add(mainFragment);
        mFragments.add(shopMarketFragment);
        mFragments.add(petCircleFragment);
        mFragments.add(myFragment);
        vpMainactivity.setAdapter(new MainActivityPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        vpMainactivity.setOffscreenPageLimit(4);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new ImageTabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        ctlMainactivity.setTabData(mTabEntities);
        ctlMainactivity.setCurrentTab(currentTabIndex);
        vpMainactivity.setCurrentItem(currentTabIndex);
        if (currentTabIndex == 0 || currentTabIndex == 1) {
            setBarColor(getResources().getColor(R.color.aD1494F));
        } else {
            setBarColor(getResources().getColor(R.color.colorPrimary));
        }
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
        ctlMainactivity.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                RingLog.d(TAG, "onTabSelect position = " + position);
                currentTabIndex = position;
                vpMainactivity.setCurrentItem(currentTabIndex);
            }

            @Override
            public void onTabReselect(int position) {
                RingLog.d(TAG, "onTabReselect position = " + position);
                currentTabIndex = position;
                vpMainactivity.setCurrentItem(currentTabIndex);
            }
        });
        vpMainactivity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RingLog.d(TAG, "onPageSelected position = " + position);
                currentTabIndex = position;
                if (position == 0 || position == 1) {
                    setBarColor(getResources().getColor(R.color.aD1494F));
                } else {
                    setBarColor(getResources().getColor(R.color.colorPrimary));
                }
                ctlMainactivity.setCurrentTab(currentTabIndex);
                if (position == 1) {
                    ctlMainactivity.hideMsg(1);
                } else if (position == 2) {
                    ctlMainactivity.hideMsg(2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitApplication();
        Process.killProcess(Process.myPid());
    }

    @Override
    public void getBootmBarSuccess(BootmBarBean bootmBarBean) {
        if (bootmBarBean != null) {
            BootmBarBean.IndexBean index = bootmBarBean.getIndex();
            String mallRedPoint = bootmBarBean.getMallRedPoint();
            int nToBeComment = bootmBarBean.getNToBeComment();
            if (index != null) {
                BootmBarBean.IndexBean.BottomBean bottom = index.getBottom();
                if (bottom != null) {
                    List<BootmBarBean.IndexBean.BottomBean.ListBean> list = bottom.getList();
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            BootmBarBean.IndexBean.BottomBean.ListBean listBean = list.get(i);
                            if (listBean != null) {
                                ImageTabEntity imageTabEntity = (ImageTabEntity) mTabEntities.get(i);
                                imageTabEntity.setSelectedIconStr(listBean.getPicH());
                                imageTabEntity.setUnSelectedIconStr(listBean.getPic());
                            }
                        }
                        ctlMainactivity.setTabData(mTabEntities);
                    }
                }
            }
            if (Integer.parseInt(mallRedPoint) > 0) {
                //设置未读消息红点
                ctlMainactivity.showDot(1);
                MsgView rtv_2_2 = ctlMainactivity.getMsgView(1);
                if (rtv_2_2 != null) {
                    UnreadMsgUtils.setSize(rtv_2_2, DensityUtil.dp2px(this, 7.5f));
                }
            } else {
                ctlMainactivity.hideMsg(1);
            }
            if (nToBeComment > 0) {
                //设置未读消息红点
                ctlMainactivity.showDot(2);
                MsgView rtv_2_2 = ctlMainactivity.getMsgView(2);
                if (rtv_2_2 != null) {
                    UnreadMsgUtils.setSize(rtv_2_2, DensityUtil.dp2px(this, 7.5f));
                }
            } else {
                ctlMainactivity.hideMsg(2);
            }
            if (currentTabIndex == 1) {
                ctlMainactivity.hideMsg(1);
            } else if (currentTabIndex == 2) {
                ctlMainactivity.hideMsg(2);
            }
        }
    }
}
