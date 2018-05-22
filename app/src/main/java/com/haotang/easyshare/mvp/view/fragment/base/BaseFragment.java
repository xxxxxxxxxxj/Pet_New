package com.haotang.easyshare.mvp.view.fragment.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.BuildConfig;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.base.fragment.FragmentLifeCallback;
import com.ljy.devring.base.fragment.IBaseFragment;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.Preconditions;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * author:    ljy
 * date：     2018/3/19
 * description： Fragment的基类
 * <p>
 * <a>https://www.jianshu.com/p/3d9ee98a9570</a>
 * 此基类的作用：
 * 1.提供延迟加载（懒加载）
 * 2.提供getContentLayout()、initView()等方法子类实现初始化操作
 * 3.销毁Presenter层对View层的引用。
 * 4.实现IBaseFragment接口，以便通过FragmentManager.FragmentLifecycleCallbacks完成部分"基类操作"
 * <p>
 * <p>
 * 由于Java的单继承的限制，DevRing库就不提供基类了，所以把一些基类操作通过FragmentManager.FragmentLifecycleCallbacks来完成
 * 只需你的Fragment需实现IBaseFragment接口，另外如果你的Activity实现了IBaseActivity，那请确保isUseFragment()返回true。
 * 即可完成以下"基类操作"：（具体请查看 {@link FragmentLifeCallback})
 * 1.操作PublishSubject以便控制网络请求的生命周期
 * 2.根据isUseEventBus()来决定EventBus的注册/注销
 * 3.数据的保存与恢复 <a>https://blog.csdn.net/donglynn/article/details/47065999</a>
 * <p>
 * <p>
 * 这种基类实现方式，参考自JessYan
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseFragment {
    protected final static String TAG = BaseFragment.class.getSimpleName();
    private final static boolean DEBUG = BuildConfig.DEBUG;
    protected BaseActivity mActivity;
    //根布局视图
    private View mContentView;
    //视图是否已经初始化完毕
    private boolean isViewReady;
    //fragment是否处于可见状态
    private boolean isFragmentVisible;
    //是否已经初始化加载过
    protected boolean isLoaded;
    //用于butterknife解绑
    private Unbinder unbinder;
    @Inject
    @Nullable
    protected P mPresenter;
    protected Bundle savedInstanceState;

    protected abstract boolean isLazyLoad();//是否使用懒加载 (Fragment可见时才进行初始化操作(以下四个方法))

    protected abstract int getContentLayout();//返回页面布局id

    protected abstract void initView();//做视图相关的初始化工作

    protected abstract void initData();//做数据相关的初始化工作

    protected abstract void initEvent();//做监听事件相关的初始化工作

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    protected View setEmptyViewBase(int flag, String msg, int resId, View.OnClickListener OnClickListener) {//1.无网络2.无数据或数据错误
        View emptyView = View.inflate(mActivity, R.layout.emptyview, null);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        if (mContentView == null) {
            try {
                mContentView = inflater.inflate(getContentLayout(), container, false);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }

            Preconditions.checkNotNull(mContentView, "根布局的id非法导致根布局为空,请检查后重试!");

            unbinder = ButterKnife.bind(this, mContentView);
        }
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //视图准备完毕
        isViewReady = true;
        if (!isLazyLoad() || isFragmentVisible) {
            init();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isFragmentVisible = isVisibleToUser;
        //如果视图准备完毕且Fragment处于可见状态，则开始初始化操作
        if (isLazyLoad() && isViewReady && isFragmentVisible) {
            init();
        }
    }

    public void init() {
        if (!isLoaded) {
            isLoaded = true;
            initView();
            initData();
            initEvent();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife解绑
        if (unbinder != null) unbinder.unbind();
        isViewReady = false;
        isLoaded = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            RingLog.d(TAG, "onDestroy");
        }
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DEBUG) {
            RingLog.d(TAG, "onStart");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DEBUG) {
            RingLog.d(TAG, "onResume");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (DEBUG) {
            RingLog.d(TAG, "onStop");
        }
    }

    @Override
    public void onSaveState(Bundle bundleToSave) {

    }

    @Override
    public void onRestoreState(Bundle bundleToRestore) {

    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }
}
