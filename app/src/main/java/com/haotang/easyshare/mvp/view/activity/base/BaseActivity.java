package com.haotang.easyshare.mvp.view.activity.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.zackratos.ultimatebar.UltimateBar;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.widget.LoadingProgressDailog;
import com.haotang.easyshare.util.ActivityListManager;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.base.activity.IBaseActivity;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.ColorBar;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation_swipeback.core.ISwipeBackActivity;
import me.yokeyword.fragmentation_swipeback.core.SwipeBackActivityDelegate;

/**
 * author:  ljy
 * date：    2018/3/19
 * description: Activity的基类
 * <p>
 * <a>https://www.jianshu.com/p/3d9ee98a9570</a>
 * 此基类的作用：
 * 1.重写onCreate并提供getContentLayout()、initView()等方法让子类实现。
 * 2.设置状态栏导航栏颜色。
 * 3.销毁Presenter层对View层的引用。
 * 4.实现IBaseActivity接口，以便通过Application.ActivityLifecycleCallbacks完成部分"基类操作"。
 * <p>
 * <p>
 * 由于Java的单继承的限制，DevRing库就不提供基类了，而是把一些基类操作通过Application.ActivityLifecycleCallbacks来完成。
 * 只需你的Activity实现IBaseActivity接口，并在Application中调用DevRing.init(this);
 * 即可完成以下"基类操作"：(具体请查看 {@link  }）
 * 1.操作PublishSubject以便控制网络请求的生命周期
 * 2.根据isUseEventBus()来决定EventBus的注册/注销
 * 3.Activity栈管理的入栈与出栈
 * 4.根据isUseFragment()来决定是否注册FragmentLifecycleCallbacks
 * <p>
 * <p>
 * 这种基类实现方式，参考自JessYan <a>https://www.jianshu.com/p/75a5c24174b2</a>
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements
        IBaseActivity, ISwipeBackActivity {
    protected final static String TAG = BaseActivity.class.getSimpleName();
    final SwipeBackActivityDelegate mDelegate = new SwipeBackActivityDelegate(this);
    @BindColor(R.color.colorPrimary)
    protected int mColor;
    @Inject
    @Nullable
    protected P mPresenter;
    protected ActivityListManager activityListManager = new ActivityListManager();
    protected LoadingProgressDailog dialog;

    protected abstract int getContentLayout();//返回页面布局id

    protected abstract void initView(Bundle savedInstanceState);//做视图相关的初始化工作

    protected abstract void setView(Bundle savedInstanceState);//做视图相关的初始化工作

    protected abstract void initData(Bundle savedInstanceState);//做数据相关的初始化工作

    protected abstract void initEvent();//做监听事件相关的初始化工作

    protected int mWidth;
    protected int mHeight;
    protected float mDensity;
    protected int mDensityDpi;
    protected int mAvatarSize;
    protected float mRatio;

    protected View setEmptyViewBase(int flag, String msg, int resId, View.OnClickListener OnClickListener) {//1.无网络2.无数据或数据错误
        View emptyView = View.inflate(this, R.layout.emptyview, null);
        ImageView iv_emptyview_img = (ImageView) emptyView.findViewById(R.id.iv_emptyview_img);
        TextView tv_emptyview_desc = (TextView) emptyView.findViewById(R.id.tv_emptyview_desc);
        ImageView iv_emptyview_retry = (ImageView) emptyView.findViewById(R.id.iv_emptyview_retry);
        if (flag == 1) {
            iv_emptyview_retry.setVisibility(View.VISIBLE);
            iv_emptyview_retry.setOnClickListener(OnClickListener);
        } else if (flag == 2) {
            iv_emptyview_retry.setVisibility(View.GONE);
        }
        StringUtil.setText(tv_emptyview_desc, msg, "", View.VISIBLE, View.VISIBLE);
        iv_emptyview_img.setImageResource(resId);
        return emptyView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
        if (getContentLayout() != 0) {
            setContentView(getContentLayout());
            ButterKnife.bind(this);
        }
        LoadingProgressDailog.Builder loadBuilder = new LoadingProgressDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        initBarColor();//初始化状态栏/导航栏颜色，需在设置了布局后再调用
        initView(savedInstanceState);//由具体的activity实现，做视图相关的初始化
        setView(savedInstanceState);//由具体的activity实现，做视图相关的设置
        initData(savedInstanceState);//由具体的activity实现，做数据的初始化
        initEvent();//由具体的activity实现，做事件监听的初始化
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_LEFT);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
        mAvatarSize = (int) (50 * mDensity);
        //setStatusBar(getStatusBarColor());
    }

    protected void initWindows() {
        Window window = getWindow();
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
        }
        UltimateBar.newImmersionBuilder()
                .applyNav(false)         // 是否应用到导航栏
                .build(this)
                .apply();
    }

    /**
     * Android 6.0 以上设置状态栏颜色
     */
    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置状态栏底色颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
            // 如果亮色，设置状态栏文字为黑色
            if (isLightColor(color)) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    protected
    @ColorInt
    int getStatusBarColor() {
        return Color.WHITE;
    }

    protected void showDialog() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    protected void disMissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void initBarColor() {
        ViewGroup parent = (ViewGroup) findViewById(android.R.id.content);
        if (parent.getChildAt(0) instanceof DrawerLayout) {
            ColorBar.newDrawerBuilder()
                    .applyNav(true)
                    .navColor(mColor)
                    .navDepth(0)
                    .statusColor(mColor)
                    .statusDepth(0)
                    .build(this)
                    .apply();
        } else {
            ColorBar.newColorBuilder()
                    .applyNav(true)
                    .navColor(mColor)
                    .navDepth(0)
                    .statusColor(mColor)
                    .statusDepth(0)
                    .build(this)
                    .apply();
        }
    }

    protected void setBarColor(int mColor) {
        this.mColor = mColor;
        initBarColor();
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    public boolean isUseFragment() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        RingLog.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RingLog.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        RingLog.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        RingLog.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RingLog.d(TAG, "onDestroy");
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mDelegate.getSwipeBackLayout();
    }

    /**
     * 是否可滑动
     *
     * @param enable
     */
    @Override
    public void setSwipeBackEnable(boolean enable) {
        mDelegate.setSwipeBackEnable(enable);
    }

    @Override
    public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) {
        mDelegate.setEdgeLevel(edgeLevel);
    }

    @Override
    public void setEdgeLevel(int widthPixel) {
        mDelegate.setEdgeLevel(widthPixel);
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity优先滑动退出;  false: Fragment优先滑动退出
     */
    @Override
    public boolean swipeBackPriority() {
        return mDelegate.swipeBackPriority();
    }

}
