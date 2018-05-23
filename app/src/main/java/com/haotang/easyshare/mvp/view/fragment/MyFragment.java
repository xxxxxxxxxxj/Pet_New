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
import com.haotang.easyshare.mvp.view.activity.MemberActivity;
import com.haotang.easyshare.mvp.view.activity.MyFollowActivity;
import com.haotang.easyshare.mvp.view.activity.MyPostActivity;
import com.haotang.easyshare.mvp.view.activity.TestActivity;
import com.haotang.easyshare.mvp.view.adapter.MyFragChargePagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IMyFragmentView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.haotang.easyshare.R.id.iv_myfragment_add;
import static com.haotang.easyshare.R.id.iv_myfragment_userimg;
import static com.haotang.easyshare.R.id.tv_myfragment_username;


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
    @BindView(iv_myfragment_userimg)
    ImageView ivMyfragmentUserimg;
    @BindView(R.id.tv_myfragment_yue)
    TextView tvMyfragmentYue;
    @BindView(R.id.tv_myfragment_vipjf)
    TextView tvMyfragmentVipjf;
    @BindView(R.id.ll_myfragment_yuejf)
    LinearLayout llMyfragmentYuejf;
    @BindView(tv_myfragment_username)
    TextView tvMyfragmentUsername;
    @BindView(iv_myfragment_add)
    ImageView ivMyfragmentAdd;
    TextView tvMyfragmentFwf;
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
    private ArrayList<BaseFragment> mFragments = new ArrayList<BaseFragment>();
    private String kf_phone = "";
    private String uuid;

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    public void requestData() {
        if (isFragmentVisible && isViewReady) {
            mPresenter.home();
            mPresenter.my();
        }
    }

    @Subscribe
    public void getLoginInfo(LoginBean data) {
        mPresenter.home();
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
            ivMyfragmentAdd.setVisibility(View.GONE);
        } else {
            rtvMyfragmentTuichu.setVisibility(View.GONE);
            tvMyfragmentUsername.setText("立即登录");
            ivMyfragmentAdd.setVisibility(View.VISIBLE);
            llMyfragmentMycdz.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({iv_myfragment_add, R.id.rl_myfragment_clxx, R.id.rl_myfragment_sycs,
            R.id.rl_myfragment_hytq, R.id.rl_myfragment_wdtz, R.id.rl_myfragment_scdzd, R.id.rl_myfragment_gzdr,
            R.id.rl_myfragment_jjdh, R.id.rl_myfragment_srgj, R.id.rl_myfragment_gy, R.id.rtv_myfragment_tuichu,
            tv_myfragment_username})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case iv_myfragment_userimg:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, EditUserInfoActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case tv_myfragment_username:
                if (SystemUtil.checkLogin(mActivity)) {
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case iv_myfragment_add:
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
                    startActivity(new Intent(mActivity, MemberActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rl_myfragment_wdtz:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, MyPostActivity.class).putExtra("uuid", uuid));
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
                if (SystemUtil.checkLogin(mActivity)) {
                    rtvMyfragmentTuichu.setVisibility(View.VISIBLE);
                    llMyfragmentMycdz.setVisibility(View.VISIBLE);
                    ivMyfragmentAdd.setVisibility(View.GONE);
                } else {
                    rtvMyfragmentTuichu.setVisibility(View.GONE);
                    tvMyfragmentUsername.setText("立即登录");
                    ivMyfragmentAdd.setVisibility(View.VISIBLE);
                    llMyfragmentMycdz.setVisibility(View.GONE);
                }
                startActivity(new Intent(mActivity, TestActivity.class));
                break;
        }
    }

    @Override
    public void homeSuccess(HomeBean data) {
        RingLog.e(TAG, "MyFragment homeSuccess()");
        if (data != null) {
            uuid = data.getUuid();
            kf_phone = data.getKf_phone();
            StringUtil.setText(tvMyfragmentUsername, data.getUserName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentYue, String.valueOf(data.getBalance()), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentVipjf, String.valueOf(data.getCoins()), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentClxx, data.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentSycs, data.getTimes() + "次", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentJjdh, data.getKf_phone(), "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetCircleImg(mActivity, data.getHeadImg(), ivMyfragmentUserimg, R.mipmap.ic_image_load_circle);
            List<HomeBean.StationsBean> stations = data.getStations();
            if (stations != null && stations.size() > 0) {
                llMyfragmentMycdz.setVisibility(View.VISIBLE);
                for (HomeBean.StationsBean stationsBean : stations) {
                    ChargeFragment chargeFragment = new ChargeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("stationsBean", stationsBean);
                    chargeFragment.setArguments(bundle);
                    mFragments.add(chargeFragment);
                }
                vpMyfragmentMycdz.setAdapter(new MyFragChargePagerAdapter(mActivity.getSupportFragmentManager(), mFragments));
            } else {
                llMyfragmentMycdz.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void homeFail(int code, String msg) {
        RingLog.e(TAG, "MyFragment homeFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void mySuccess(List<MyCarBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            MyCarBean.DataBean dataBean = data.get(0);
            if (dataBean != null) {
                StringUtil.setText(tvMyfragmentClxx, dataBean.getBrand() + dataBean.getCar(), "", View.VISIBLE, View.VISIBLE);
            }
        }
    }

    @Override
    public void myFail(int code, String msg) {
        RingLog.e(TAG, "MyFragment myFail() status = " + code + "---desc = " + msg);
    }
}
