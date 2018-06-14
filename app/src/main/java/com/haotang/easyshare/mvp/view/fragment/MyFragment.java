package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundRelativeLayout;
import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerMyFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.MyFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.LoginBean;
import com.haotang.easyshare.mvp.model.entity.res.MyCarBean;
import com.haotang.easyshare.mvp.presenter.MyFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AboutActivity;
import com.haotang.easyshare.mvp.view.activity.AddChargeActivity;
import com.haotang.easyshare.mvp.view.activity.ButlerActivity;
import com.haotang.easyshare.mvp.view.activity.CarInfoActivity;
import com.haotang.easyshare.mvp.view.activity.CollectChargeActivity;
import com.haotang.easyshare.mvp.view.activity.EditUserInfoActivity;
import com.haotang.easyshare.mvp.view.activity.LoginActivity;
import com.haotang.easyshare.mvp.view.activity.MyFollowActivity;
import com.haotang.easyshare.mvp.view.activity.MyPostActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.MyFragChargePagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IMyFragmentView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
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
 * @date zhoujunxia on 2018/4/14 21:00
 */
public class MyFragment extends BaseFragment<MyFragmentPresenter> implements IMyFragmentView {
    private final static String TAG = MyFragment.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_myfragment_userimg)
    ImageView ivMyfragmentUserimg;
    @BindView(R.id.tv_myfragment_yue)
    TextView tvMyfragmentYue;
    @BindView(R.id.tv_myfragment_vipjf)
    TextView tvMyfragmentVipjf;
    @BindView(R.id.ll_myfragment_yuejf)
    LinearLayout llMyfragmentYuejf;
    @BindView(R.id.tv_myfragment_username)
    TextView tvMyfragmentUsername;
    @BindView(R.id.ll_myfragment_mycdz)
    LinearLayout llMyfragmentMycdz;
    @BindView(R.id.ll_myfragment_jqqd)
    RoundLinearLayout llMyfragmentJqqd;
    @BindView(R.id.rl_myfragment_clxx)
    RelativeLayout rlMyfragmentClxx;
    @BindView(R.id.rl_myfragment_sycs)
    RelativeLayout rlMyfragmentSycs;
    @BindView(R.id.rl_myfragment_hytq)
    RelativeLayout rlMyfragmentHytq;
    @BindView(R.id.rl_myfragment_wdtz)
    RelativeLayout rlMyfragmentWdtz;
    @BindView(R.id.rl_myfragment_scdzd)
    RelativeLayout rlMyfragmentScdzd;
    @BindView(R.id.rl_myfragment_gzdr)
    RelativeLayout rlMyfragmentGzdr;
    @BindView(R.id.rl_myfragment_jjdh)
    RelativeLayout rlMyfragmentJjdh;
    @BindView(R.id.rl_myfragment_srgj)
    RelativeLayout rlMyfragmentSrgj;
    @BindView(R.id.rl_myfragment_gy)
    RelativeLayout rlMyfragmentGy;
    @BindView(R.id.rtv_myfragment_tuichu)
    RoundTextView rtvMyfragmentTuichu;
    @BindView(R.id.rll_myfragment_userinfo)
    RoundRelativeLayout rllMyfragmentUserinfo;
    @BindView(R.id.tv_myfragment_clxx)
    TextView tvMyfragmentClxx;
    @BindView(R.id.tv_myfragment_sycs)
    TextView tvMyfragmentSycs;
    @BindView(R.id.tv_myfragment_jjdh)
    TextView tvMyfragmentJjdh;
    @BindView(R.id.vp_myfragment_mycdz)
    ViewPager vpMyfragmentMycdz;
    @BindView(R.id.tv_myfragment_mycharge_num)
    TextView tv_myfragment_mycharge_num;
    @BindView(R.id.iv_myfragment_add)
    ImageView iv_myfragment_add;
    @BindView(R.id.iv_myfragment_bjusername)
    ImageView iv_myfragment_bjusername;
    private ArrayList<BaseFragment> mFragments = new ArrayList<BaseFragment>();
    private String kf_phone = "";
    private String uuid;
    private String vipPrivilege;
    private int pagePosition;
    private List<HomeBean.StationsBean> stations = new ArrayList<HomeBean.StationsBean>();
    private MyFragChargePagerAdapter myFragChargePagerAdapter;

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    public void requestData() {
    }

    @Subscribe
    public void RefreshFragment(RefreshFragmentEvent refreshFragmentEvent) {
        if (SystemUtil.checkLogin(mActivity) && refreshFragmentEvent != null &&
                refreshFragmentEvent.getRefreshIndex() == RefreshFragmentEvent.REFRESH_MYFRAGMET
                && mPresenter != null) {
            RingLog.e("REFRESH_MYFRAGMET");
            rtvMyfragmentTuichu.setVisibility(View.VISIBLE);
            llMyfragmentMycdz.setVisibility(View.VISIBLE);
            showDialog();
            mPresenter.home();
            mPresenter.my();
        }
    }

    @Subscribe
    public void getLoginInfo(LoginBean data) {
        rtvMyfragmentTuichu.setVisibility(View.VISIBLE);
        llMyfragmentMycdz.setVisibility(View.VISIBLE);
        showDialog();
        mPresenter.home();
        mPresenter.my();
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.myfragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        DaggerMyFragmentCommponent.builder()
                .myFragmentModule(new MyFragmentModule(this, mActivity))
                .build()
                .inject(this);
        rllMyfragmentUserinfo.bringToFront();
        ivMyfragmentUserimg.bringToFront();
        if (SystemUtil.checkLogin(mActivity)) {
            rtvMyfragmentTuichu.setVisibility(View.VISIBLE);
            llMyfragmentMycdz.setVisibility(View.VISIBLE);
        } else {
            rtvMyfragmentTuichu.setVisibility(View.GONE);
            tvMyfragmentUsername.setText("立即登录");
            llMyfragmentMycdz.setVisibility(View.GONE);
        }
        myFragChargePagerAdapter = new MyFragChargePagerAdapter(mActivity.getSupportFragmentManager(), mFragments);
        vpMyfragmentMycdz.setAdapter(myFragChargePagerAdapter);
    }

    @Override
    protected void initData() {
        if (SystemUtil.checkLogin(mActivity)) {
            showDialog();
            mPresenter.home();
            mPresenter.my();
        }
    }

    @Override
    protected void initEvent() {
        vpMyfragmentMycdz.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagePosition = position;
                RingLog.e(TAG, "onPageSelected position = " + position);
                if (stations.size() > 0 && pagePosition >= 0 && stations.size() > pagePosition) {
                    HomeBean.StationsBean stationsBean = stations.get(pagePosition);
                    if (stationsBean != null) {
                        DevRing.busManager().postEvent(stationsBean);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_myfragment_add, R.id.rl_myfragment_clxx, R.id.rl_myfragment_sycs,
            R.id.rl_myfragment_hytq, R.id.rl_myfragment_wdtz, R.id.rl_myfragment_scdzd, R.id.rl_myfragment_gzdr,
            R.id.rl_myfragment_jjdh, R.id.rl_myfragment_srgj, R.id.rl_myfragment_gy, R.id.rtv_myfragment_tuichu,
            R.id.tv_myfragment_username, R.id.iv_myfragment_userimg, R.id.rl_myfragment_mycharge_right,
            R.id.iv_myfragment_bjusername})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_myfragment_bjusername:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, EditUserInfoActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_mycharge_right:
                if (stations != null && stations.size() > 0) {
                    if ((stations.size() - 1) > pagePosition) {
                        pagePosition++;
                    } else {
                        pagePosition = 0;
                    }
                    vpMyfragmentMycdz.setCurrentItem(pagePosition);
                }
                break;
            case R.id.iv_myfragment_userimg:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, EditUserInfoActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.tv_myfragment_username:
                if (SystemUtil.checkLogin(mActivity)) {
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.iv_myfragment_add:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, AddChargeActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_clxx:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, CarInfoActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_hytq:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).
                            putExtra(WebViewActivity.URL_KEY, vipPrivilege));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_wdtz:
                if (SystemUtil.checkLogin(mActivity)) {
                    if (StringUtil.isNotEmpty(uuid)) {
                        startActivity(new Intent(mActivity, MyPostActivity.class).putExtra("uuid", uuid));
                    }
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_scdzd:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, CollectChargeActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_gzdr:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, MyFollowActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_jjdh:
                SystemUtil.cellPhone(mActivity, kf_phone);
                break;
            case R.id.rl_myfragment_srgj:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, ButlerActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_gy:
                startActivity(new Intent(mActivity, AboutActivity.class));
                break;
            case R.id.rtv_myfragment_tuichu:
                SharedPreferenceUtil.getInstance(mActivity).removeData("cellphone");
                DevRing.configureHttp().getMapHeader().put("phone", "");
                rtvMyfragmentTuichu.setVisibility(View.GONE);
                tvMyfragmentUsername.setText("立即登录");
                llMyfragmentMycdz.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void homeSuccess(HomeBean data) {
        disMissDialog();
        RingLog.e(TAG, "MyFragment homeSuccess()");
        if (data != null) {
            vipPrivilege = data.getVipPrivilege();
            uuid = data.getUuid();
            kf_phone = data.getKf_phone();
            StringUtil.setText(tvMyfragmentUsername, data.getUserName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentYue, String.valueOf(data.getBalance()), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentVipjf, String.valueOf(data.getCoins()), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentSycs, data.getTimes() + "次", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentJjdh, data.getKf_phone(), "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetCircleImg(mActivity, data.getHeadImg(), ivMyfragmentUserimg, R.mipmap.ic_image_load_circle);
            if (data.getStations() != null && data.getStations().size() > 0) {
                stations.clear();
                stations.addAll(data.getStations());
                iv_myfragment_add.setVisibility(View.GONE);
                StringUtil.setText(tv_myfragment_mycharge_num, "(" + stations.size() + ")", "", View.VISIBLE, View.VISIBLE);
                mFragments.clear();
                llMyfragmentMycdz.setVisibility(View.VISIBLE);
                for (HomeBean.StationsBean stationsBean : stations) {
                    ChargeFragment chargeFragment = new ChargeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("stationsBean", stationsBean);
                    chargeFragment.setArguments(bundle);
                    mFragments.add(chargeFragment);
                }
                myFragChargePagerAdapter.notifyDataSetChanged();
                if (stations.size() > pagePosition) {
                    HomeBean.StationsBean stationsBean = stations.get(pagePosition);
                    if (stationsBean != null) {
                        DevRing.busManager().postEvent(stationsBean);
                    }
                }
            } else {
                iv_myfragment_add.setVisibility(View.VISIBLE);
                llMyfragmentMycdz.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void homeFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "MyFragment homeFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity,code);
    }

    @Override
    public void mySuccess(List<MyCarBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            MyCarBean.DataBean dataBean = data.get(0);
            if (dataBean != null) {
                StringUtil.setText(tvMyfragmentClxx, dataBean.getBrand() + dataBean.getCar(), "", View.VISIBLE, View.VISIBLE);
            }
        }
    }

    @Override
    public void myFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "MyFragment myFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity,code);
    }
}
