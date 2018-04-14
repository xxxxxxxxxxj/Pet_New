package com.haotang.newpet.mvp.view.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.haotang.newpet.R;
import com.haotang.newpet.mvp.presenter.base.BasePresenter;
import com.ljy.devring.DevRing;
import com.ljy.devring.base.activity.IBaseActivity;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.ColorBar;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.ButterKnife;

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

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseActivity {
    protected final static String TAG = BaseActivity.class.getSimpleName();
    @BindColor(R.color.colorPrimary)
    protected int mColor;
    @Inject
    @Nullable
    protected P mPresenter;

    protected abstract int getContentLayout();//返回页面布局id

    protected abstract void initView(Bundle savedInstanceState);//做视图相关的初始化工作

    protected abstract void setView(Bundle savedInstanceState);//做视图相关的初始化工作

    protected abstract void initData(Bundle savedInstanceState);//做数据相关的初始化工作

    protected abstract void initEvent();//做监听事件相关的初始化工作

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentLayout() != 0) {
            setContentView(getContentLayout());
            ButterKnife.bind(this);
        }
        DevRing.activityStackManager().pushOneActivity(this);
        initBarColor();//初始化状态栏/导航栏颜色，需在设置了布局后再调用
        initView(savedInstanceState);//由具体的activity实现，做视图相关的初始化
        setView(savedInstanceState);//由具体的activity实现，做视图相关的设置
        initData(savedInstanceState);//由具体的activity实现，做数据的初始化
        initEvent();//由具体的activity实现，做事件监听的初始化
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
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }
}
