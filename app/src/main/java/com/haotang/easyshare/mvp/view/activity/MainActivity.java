package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.os.Process;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerMainActivityCommponent;
import com.haotang.easyshare.di.module.activity.MainActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.MainTabEvent;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.BootmBarBean;
import com.haotang.easyshare.mvp.model.entity.res.ImageTabEntity;
import com.haotang.easyshare.mvp.model.entity.res.LastVersionBean;
import com.haotang.easyshare.mvp.presenter.MainPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MainActivityPagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.ChargeIngFragment;
import com.haotang.easyshare.mvp.view.fragment.HotFragment;
import com.haotang.easyshare.mvp.view.fragment.MainFragment;
import com.haotang.easyshare.mvp.view.fragment.MyFragment;
import com.haotang.easyshare.mvp.view.fragment.SelectCarFragment;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IMainView;
import com.haotang.easyshare.mvp.view.services.ChargeBillService;
import com.haotang.easyshare.mvp.view.services.ChargeStateService;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.PollingUtils;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UpdateUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;

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
    private String[] mTitles = {"易享", "热点", "充电", "选车", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_normal, R.mipmap.tab_hot_normal,
            R.mipmap.tab_charge_normal,
            R.mipmap.tab_selectcar_normal, R.mipmap.tab_my_normal};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_passed, R.mipmap.tab_hot_passed,
            R.mipmap.tab_charge_passed,
            R.mipmap.tab_selectcar_passed, R.mipmap.tab_my_passed};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    @Inject
    MainFragment mainFragment;
    @Inject
    HotFragment hotFragment;
    @Inject
    ChargeIngFragment chargeIngFragment;
    @Inject
    SelectCarFragment selectCarFragment;
    @Inject
    MyFragment myFragment;
    private int currentTabIndex = 0;
    private boolean isRedPoint = true;
    private int sx;
    private int sy;
    int screenWidth, screenHeight;
    private static final float BASE_BOTTOM_DESC = 80;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSwipeBackEnable(false);
        activityListManager.addActivity(this);
        //使用Dagger2对本类中相关变量进行初始化
        DaggerMainActivityCommponent.builder().mainActivityModule(new MainActivityModule(this, this)).build().inject(this);
        permissionDialog.setMessage(R.string.permission_request_WRITE_EXTERNAL_STORAGE);
    }

    @Subscribe
    public void getAddress(MainTabEvent mainTabEvent) {
        if (mainTabEvent != null) {
            currentTabIndex = mainTabEvent.getIndex();
            ctlMainactivity.setCurrentTab(currentTabIndex);
            vpMainactivity.setCurrentItem(currentTabIndex);
        }
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        Display dis = this.getWindowManager().getDefaultDisplay();
        screenWidth = dis.getWidth();
        screenHeight = dis.getHeight();
        SharedPreferenceUtil.getInstance(MainActivity.this).saveBoolean("guide", true);
        permissionDialog.setPositiveButton(R.string.permission_request_dialog_pos);
        permissionDialog.setNegativeButton(R.string.permission_request_dialog_nav);

        mFragments.add(mainFragment);
        mFragments.add(hotFragment);
        mFragments.add(chargeIngFragment);
        mFragments.add(selectCarFragment);
        mFragments.add(myFragment);
        vpMainactivity.setAdapter(new MainActivityPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        vpMainactivity.setOffscreenPageLimit(5);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new ImageTabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        ctlMainactivity.setTabData(mTabEntities);
        ctlMainactivity.setCurrentTab(currentTabIndex);
        vpMainactivity.setCurrentItem(currentTabIndex);
        if (isRedPoint) {
            //设置未读消息红点
            ctlMainactivity.showDot(1);
            MsgView rtv_2_2 = ctlMainactivity.getMsgView(1);
            if (rtv_2_2 != null) {
                UnreadMsgUtils.setSize(rtv_2_2, DensityUtil.dp2px(this, 7.5f));
            }
        } else {
            ctlMainactivity.hideMsg(1);
        }
        DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_MAINFRAGMET));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initEvent() {
        ctlMainactivity.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                RingLog.e(TAG, "onTabSelect position = " + position);
                currentTabIndex = position;
                vpMainactivity.setCurrentItem(currentTabIndex);
            }

            @Override
            public void onTabReselect(int position) {
                RingLog.e(TAG, "onTabReselect position = " + position);
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
                SystemUtil.goneJP(MainActivity.this);
                RingLog.e(TAG, "onPageSelected position = " + position);
                currentTabIndex = position;
                ctlMainactivity.setCurrentTab(currentTabIndex);
                if (position == 0) {
                    DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_MAINFRAGMET));
                } else if (position == 1) {
                    ctlMainactivity.hideMsg(1);
                    DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_HOTFRAGMET));
                } else if (position == 2) {
                    DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_CHARGEINGFRAGMET));
                } else if (position == 3) {
                    DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_SELECTCARFRAGMET));
                } else if (position == 4) {
                    DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_MYFRAGMET));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /*ivMainfragGj.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // event.getRawX(); //获取手指第一次接触屏幕在x方向的坐标
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:// 获取手指第一次接触屏幕
                        sx = (int) event.getRawX();
                        sy = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:// 手指在屏幕上移动对应的事件
                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();
                        // 获取手指移动的距离
                        int dx = x - sx;
                        int dy = y - sy;
                        int top = v.getTop() + dy;
                        int left = v.getLeft() + dx;
                        if (top <= 0) {
                            top = 0;
                        }

                        float bottom_desc = BASE_BOTTOM_DESC * getResources().getDisplayMetrics().density / 3;
                        if (top >= screenHeight - (ivMainfragGj.getHeight() + bottom_desc)) {
                            top = (int) (screenHeight - (ivMainfragGj.getHeight() + bottom_desc));
                        }
                        if (left >= screenWidth - ivMainfragGj.getWidth()) {
                            left = screenWidth - ivMainfragGj.getWidth();
                        }
                        if (left <= 0) {
                            left = 0;
                        }
                        // 更改imageView在窗体的位置
                        ivMainfragGj.layout(left, top, left + ivMainfragGj.getWidth(), top + ivMainfragGj.getHeight());
                        // 获取移动后的位置
                        sx = (int) event.getRawX();
                        sy = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:// 手指离开屏幕对应事件
                        // 记录最后图片在窗体的位置
                        int lastTop = ivMainfragGj.getTop();
                        int lastLeft = ivMainfragGj.getLeft();
                        int lastRight = ivMainfragGj.getRight();
                        int lastBottom = ivMainfragGj.getBottom();
                        SharedPreferenceUtil.getInstance(MainActivity.this).saveInt("lastTop", lastTop);
                        SharedPreferenceUtil.getInstance(MainActivity.this).saveInt("lastLeft", lastLeft);
                        SharedPreferenceUtil.getInstance(MainActivity.this).saveInt("lastRight", lastRight);
                        SharedPreferenceUtil.getInstance(MainActivity.this).saveInt("lastBottom", lastBottom);
                        break;
                }
                return true;// 不会中断触摸事件的返回
            }
        });*/
    }

    private void initWindows() {
        /*Window window = getWindow();
        int color = getResources().getColor(android.R.color.transparent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("TAG", "1");
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("TAG", "2");
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
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
        SystemUtil.Exit(this, status);
    }

    @Override
    public void getBootmBarFail(int status, String desc) {
        RingLog.e(TAG, "MainActivity getBootmBarFail() status = " + status + "---desc = " + desc);
        SystemUtil.Exit(this, status);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitApplication();
        Process.killProcess(Process.myPid());
        PollingUtils.stopPollingService(this, ChargeBillService.class, ChargeBillService.ACTION);
        PollingUtils.stopPollingService(this, ChargeStateService.class, ChargeStateService.ACTION);
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
                ctlMainactivity.showDot(4);
                MsgView rtv_2_2 = ctlMainactivity.getMsgView(4);
                if (rtv_2_2 != null) {
                    UnreadMsgUtils.setSize(rtv_2_2, DensityUtil.dp2px(this, 7.5f));
                }
            } else {
                ctlMainactivity.hideMsg(4);
            }
            if (currentTabIndex == 1) {
                ctlMainactivity.hideMsg(1);
            } else if (currentTabIndex == 4) {
                ctlMainactivity.hideMsg(4);
            }
        }
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
