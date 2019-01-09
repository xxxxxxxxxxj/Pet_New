package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerHotFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.HotFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.ImageTabEntity;
import com.haotang.easyshare.mvp.model.entity.res.SerchKeysBean;
import com.haotang.easyshare.mvp.presenter.HotFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.LoginActivity;
import com.haotang.easyshare.mvp.view.activity.SendPostActivity;
import com.haotang.easyshare.mvp.view.activity.SerchPostActivity;
import com.haotang.easyshare.mvp.view.adapter.MyFragChargePagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IHotFragmentView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 20:59
 */
public class HotFragment extends BaseFragment<HotFragmentPresenter> implements IHotFragmentView {
    protected final static String TAG = HotFragment.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_hotfragment_post)
    TextView tvHotfragmentPost;
    @BindView(R.id.tv_hotfragment_serch)
    TextView tvHotfragmentSerch;
    @BindView(R.id.ctl_hotfrag)
    CommonTabLayout ctl_hotfrag;
    @BindView(R.id.vp_hotfragment)
    ViewPager vp_hotfragment;
    
    private String[] mTitles = {"最新帖", "热门贴", "问题车"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_zxt_normal, R.mipmap.tab_rmt_normal,
            R.mipmap.tab_wtc_normal};
    private int[] mIconSelectIds = {
            R.mipmap.tab_zxt_passed, R.mipmap.tab_rmt_passed,
            R.mipmap.tab_wtc_passed};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private MyFragChargePagerAdapter myFragChargePagerAdapter;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.hotfragment;
    }

    @Override
    protected void initView() {
        DaggerHotFragmentCommponent.builder()
                .hotFragmentModule(new HotFragmentModule(this, mActivity))
                .build()
                .inject(this);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new ImageTabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        ctl_hotfrag.setTabData(mTabEntities);
        ctl_hotfrag.setCurrentTab(0);
        vp_hotfragment.setCurrentItem(0);
        
        mFragments.clear();
        for (int i = 0; i < 3; i++) {
            HotPostFragment hotPostFragment = new HotPostFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            hotPostFragment.setArguments(bundle);
            mFragments.add(hotPostFragment);
        }
        myFragChargePagerAdapter = new MyFragChargePagerAdapter(mActivity.getSupportFragmentManager(), mFragments);
        vp_hotfragment.setAdapter(myFragChargePagerAdapter);
        vp_hotfragment.setOffscreenPageLimit(3);
    }

    @Override
    protected void initData() {
        showDialog();
        mPresenter.keys(UrlConstants.getMapHeader(mActivity));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void requestData() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void RefreshFragment(RefreshFragmentEvent refreshFragmentEvent) {
        if (refreshFragmentEvent != null && refreshFragmentEvent.getRefreshIndex() ==
                RefreshFragmentEvent.REFRESH_HOTFRAGMET && mPresenter != null) {
            RingLog.e("REFRESH_HOTFRAGMET");
            showDialog();
            mPresenter.keys(UrlConstants.getMapHeader(mActivity));
        }
    }

    @Override
    protected void initEvent() {
        vp_hotfragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ctl_hotfrag.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ctl_hotfrag.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                RingLog.e(TAG, "onTabSelect position = " + position);
                vp_hotfragment.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                RingLog.e(TAG, "onTabReselect position = " + position);
            }
        });
    }

    @Override
    public void listSuccess(List<AdvertisementBean.DataBean> data) {
        disMissDialog();
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "listFail() code = " + code + "---msg = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void hotSuccess(List<HotCarBean.DataBean> data) {
        disMissDialog();
    }

    @Override
    public void hotFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "hotFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void newestSuccess(List<HotPoint.DataBean> data) {
        disMissDialog();
    }

    @Override
    public void newestFail(int code, String msg) {
        disMissDialog();
    }

    @Override
    public void keysSuccess(List<SerchKeysBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            SerchKeysBean.DataBean dataBean = data.get(0);
            if (dataBean != null) {
                StringUtil.setText(tvHotfragmentSerch, dataBean.getKey(), "", View.VISIBLE, View.VISIBLE);
            }
        }
    }

    @Override
    public void keysFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "keysFail() code = " + code + "---msg = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @OnClick({R.id.tv_hotfragment_post, R.id.tv_hotfragment_serch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hotfragment_post:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, SendPostActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.tv_hotfragment_serch:
                startActivity(new Intent(mActivity, SerchPostActivity.class));
                break;
        }
    }
}
