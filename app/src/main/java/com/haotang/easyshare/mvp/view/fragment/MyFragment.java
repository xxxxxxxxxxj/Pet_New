package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerMyFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.MyFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshBalanceEvent;
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
import com.haotang.easyshare.mvp.view.activity.MyBalanceActivity;
import com.haotang.easyshare.mvp.view.activity.MyCouponActivity;
import com.haotang.easyshare.mvp.view.activity.MyPostActivity;
import com.haotang.easyshare.mvp.view.activity.RechargeRecordActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.MyFragChargePagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IMyFragmentView;
import com.haotang.easyshare.mvp.view.widget.AlertDialogNavAndPost;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
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
    @BindView(R.id.tv_myfragment_yhq)
    TextView tv_myfragment_yhq;
    @BindView(R.id.tv_myfragment_vipjf)
    TextView tvMyfragmentVipjf;
    @BindView(R.id.tv_myfragment_username)
    TextView tvMyfragmentUsername;
    @BindView(R.id.ll_myfragment_mycdz)
    LinearLayout llMyfragmentMycdz;
    @BindView(R.id.rtv_myfragment_tuichu)
    RoundTextView rtvMyfragmentTuichu;
    @BindView(R.id.tv_myfragment_clxx)
    TextView tvMyfragmentClxx;
    @BindView(R.id.tv_myfragment_jjdh)
    TextView tvMyfragmentJjdh;
    @BindView(R.id.vp_myfragment_mycdz)
    ViewPager vpMyfragmentMycdz;
    @BindView(R.id.tv_myfragment_mycharge_title)
    TextView tv_myfragment_mycharge_title;
    @BindView(R.id.iv_myfragment_bjusername)
    ImageView iv_myfragment_bjusername;
    @BindView(R.id.rl_myfragment_mycharge_right)
    RelativeLayout rl_myfragment_mycharge_right;
    @BindView(R.id.ll_myfragment_addstation)
    RelativeLayout ll_myfragment_addstation;
    private ArrayList<BaseFragment> mFragments = new ArrayList<BaseFragment>();
    private String kf_phone = "";
    private String uuid;
    private String vipPrivilege;
    private int pagePosition;
    private List<HomeBean.StationsBean> stations = new ArrayList<HomeBean.StationsBean>();
    private MyFragChargePagerAdapter myFragChargePagerAdapter;
    private double balance;

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    public void requestData() {
    }

    @Subscribe
    public void RefreshBalance(RefreshBalanceEvent event) {//充值返回
        if (event != null) {
            showDialog();
            mPresenter.home(UrlConstants.getMapHeader(mActivity));
        }
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
            mPresenter.home(UrlConstants.getMapHeader(mActivity));
            mPresenter.my(UrlConstants.getMapHeader(mActivity));
            UmenUtil.UmengEventStatistics(getActivity(), UmenUtil.yxzx14);
        }
    }

    @Subscribe
    public void getLoginInfo(LoginBean data) {
        if (data != null) {
            rtvMyfragmentTuichu.setVisibility(View.VISIBLE);
            llMyfragmentMycdz.setVisibility(View.VISIBLE);
            showDialog();
            mPresenter.home(UrlConstants.getMapHeader(mActivity));
            mPresenter.my(UrlConstants.getMapHeader(mActivity));
        }
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
        ivMyfragmentUserimg.bringToFront();
        if (SystemUtil.checkLogin(mActivity)) {
            rtvMyfragmentTuichu.setVisibility(View.VISIBLE);
            llMyfragmentMycdz.setVisibility(View.VISIBLE);
        } else {
            exit();
        }
        myFragChargePagerAdapter = new MyFragChargePagerAdapter(mActivity.getSupportFragmentManager(), mFragments);
        vpMyfragmentMycdz.setAdapter(myFragChargePagerAdapter);
    }

    @Override
    protected void initData() {
        if (SystemUtil.checkLogin(mActivity)) {
            showDialog();
            mPresenter.home(UrlConstants.getMapHeader(mActivity));
            mPresenter.my(UrlConstants.getMapHeader(mActivity));
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

    @OnClick({R.id.iv_myfragment_addstation, R.id.ll_myfragment_clxx,
            R.id.rl_myfragment_hytq, R.id.ll_myfragment_wdtz, R.id.ll_myfragment_sczd,
            R.id.rl_myfragment_jjdh, R.id.rl_myfragment_srgj, R.id.rl_myfragment_gy, R.id.rtv_myfragment_tuichu,
            R.id.tv_myfragment_username, R.id.iv_myfragment_userimg, R.id.rl_myfragment_mycharge_right,
            R.id.iv_myfragment_bjusername, R.id.ll_myfragment_cdjl, R.id.ll_myfragment_yue, R.id.ll_myfragment_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_myfragment_coupon:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, MyCouponActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.ll_myfragment_yue:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, MyBalanceActivity.class).putExtra("balance", balance));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.ll_myfragment_cdjl:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, RechargeRecordActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
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
            case R.id.iv_myfragment_addstation:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, AddChargeActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.ll_myfragment_clxx:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, CarInfoActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_hytq:
                if (SystemUtil.checkLogin(mActivity)) {
                    UmenUtil.UmengEventStatistics(getActivity(), UmenUtil.yxzx18);
                    startActivity(new Intent(mActivity, WebViewActivity.class).
                            putExtra(WebViewActivity.URL_KEY, vipPrivilege));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.ll_myfragment_wdtz:
                if (SystemUtil.checkLogin(mActivity)) {
                    if (StringUtil.isNotEmpty(uuid)) {
                        startActivity(new Intent(mActivity, MyPostActivity.class).putExtra("uuid", uuid));
                    }
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.ll_myfragment_sczd:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, CollectChargeActivity.class));
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
                //startActivity(new Intent(mActivity, TestActivity.class));
                startActivity(new Intent(mActivity, AboutActivity.class));
                break;
            case R.id.rtv_myfragment_tuichu:
                new AlertDialogNavAndPost(getActivity()).builder().setTitle("")
                        .setMsg("确定退出登录吗")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exit();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
        }
    }

    @Override
    public void homeSuccess(HomeBean data) {
        iv_myfragment_bjusername.setVisibility(View.VISIBLE);
        disMissDialog();
        RingLog.e(TAG, "MyFragment homeSuccess()");
        if (data != null) {
            vipPrivilege = data.getVipPrivilege();
            uuid = data.getUuid();
            kf_phone = data.getKf_phone();
            balance = data.getBalance();
            StringUtil.setText(tvMyfragmentUsername, data.getUserName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentYue, String.valueOf(data.getBalance()), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_myfragment_yhq, String.valueOf(data.getBalance()), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentVipjf, String.valueOf(data.getCoins()), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentJjdh, data.getKf_phone(), "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetCircleImg(mActivity, data.getHeadImg(), ivMyfragmentUserimg, R.mipmap.ic_image_load_circle);
            if (data.getStations() != null && data.getStations().size() > 0) {
                if (data.getStations().size() > 1) {
                    rl_myfragment_mycharge_right.setVisibility(View.VISIBLE);
                } else {
                    rl_myfragment_mycharge_right.setVisibility(View.GONE);
                }
                stations.clear();
                stations.addAll(data.getStations());
                ll_myfragment_addstation.setVisibility(View.GONE);
                StringUtil.setText(tv_myfragment_mycharge_title, "我的充电桩 (" + stations.size() + ")", "", View.VISIBLE, View.VISIBLE);
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
                ll_myfragment_addstation.setVisibility(View.VISIBLE);
                llMyfragmentMycdz.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void homeFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "MyFragment homeFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
        exitInfo(code);
    }

    @Override
    public void mySuccess(List<MyCarBean.DataBean> data) {
        iv_myfragment_bjusername.setVisibility(View.VISIBLE);
        disMissDialog();
        if (data != null && data.size() > 0) {
            MyCarBean.DataBean dataBean = data.get(0);
            if (dataBean != null) {
                StringUtil.setText(tvMyfragmentClxx, dataBean.getBrand() + dataBean.getCar(), "车辆信息", View.VISIBLE, View.VISIBLE);
            }
        }
    }

    @Override
    public void myFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "MyFragment myFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
        exitInfo(code);
    }

    private void exitInfo(int code) {
        if (code == AppConfig.EXIT_USER_CODE) {
            exit();
        }
    }

    private void exit() {
        SharedPreferenceUtil.getInstance(mActivity).removeData("cellphone");
        rtvMyfragmentTuichu.setVisibility(View.GONE);
        tvMyfragmentUsername.setText("立即登录");
        llMyfragmentMycdz.setVisibility(View.GONE);
        tvMyfragmentYue.setText("0");
        tv_myfragment_yhq.setText("0");
        tvMyfragmentVipjf.setText("0");
        iv_myfragment_bjusername.setVisibility(View.GONE);
        ivMyfragmentUserimg.setImageResource(R.mipmap.ic_image_load_circle);
        ll_myfragment_addstation.setVisibility(View.VISIBLE);
        tvMyfragmentClxx.setText("车辆信息");
        rtvMyfragmentTuichu.setVisibility(View.GONE);
    }
}
