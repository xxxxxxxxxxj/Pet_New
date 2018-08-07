package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerChargeIngFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.ChargeIngFragmentModule;
import com.haotang.easyshare.mvp.presenter.ChargeIngFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.RechargeActivity;
import com.haotang.easyshare.mvp.view.activity.ScanCodeActivity;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IChargeIngFragmentView;
import com.haotang.easyshare.util.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.haotang.easyshare.R.id.btn_chargeing_submit;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:充电主界面</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:36
 */
public class ChargeIngFragment extends BaseFragment<ChargeIngFragmentPresenter> implements IChargeIngFragmentView {
    protected final static String TAG = ChargeIngFragment.class.getSimpleName();
    @BindView(R.id.tv_chargeing_titlebar_other)
    TextView tvChargeingTitlebarOther;
    @BindView(R.id.tv_chargeing_num)
    TextView tvChargeingNum;
    @BindView(R.id.iv_chargeing)
    ImageView ivChargeing;
    @BindView(R.id.tv_chargeing_money)
    TextView tvChargeingMoney;
    @BindView(R.id.tv_chargeing_ljcz)
    TextView tvChargeingLjcz;
    @BindView(R.id.ll_chargeing_start)
    LinearLayout ll_chargeing_start;
    @BindView(R.id.rl_chargeing_start)
    RelativeLayout rlChargeingStart;
    @BindView(R.id.rl_chargeing_charge_before)
    RelativeLayout rlChargeingChargeBefore;
    @BindView(R.id.tv_chargeing_name)
    TextView tvChargeingName;
    @BindView(R.id.tv_chargeing_gzbx)
    TextView tvChargeingGzbx;
    @BindView(R.id.tv_chargeing_kwh)
    TextView tvChargeingKwh;
    @BindView(R.id.tv_chargeing_status)
    TextView tvChargeingStatus;
    @BindView(R.id.iv_chargeing_ing)
    ImageView ivChargeingIng;
    @BindView(R.id.tv_chargeing_cdf)
    TextView tvChargeingCdf;
    @BindView(R.id.tv_chargeing_fwf)
    TextView tvChargeingFwf;
    @BindView(R.id.tv_chargeing_zfy)
    TextView tvChargeingZfy;
    @BindView(R.id.btn_chargeing_submit)
    Button btnChargeingSubmit;
    @BindView(R.id.rl_chargeing_charge_after)
    RelativeLayout rlChargeingChargeAfter;
    private String phone;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.chargeingfragment;
    }

    @Override
    protected void initView() {
        DaggerChargeIngFragmentCommponent.builder()
                .chargeIngFragmentModule(new ChargeIngFragmentModule(this, mActivity))
                .build()
                .inject(this);
        tvChargeingLjcz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvChargeingLjcz.getPaint().setAntiAlias(true);//抗锯齿
        Glide.with(this).load(R.mipmap.icon_chargeing_gif).asGif().into(ivChargeing);
        ll_chargeing_start.bringToFront();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.tv_chargeing_titlebar_other, R.id.rl_chargeing_start, R.id.tv_chargeing_ljcz, R.id.btn_chargeing_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chargeing_titlebar_other:
                SystemUtil.cellPhone(mActivity, phone);
                break;
            case R.id.rl_chargeing_start:
                startActivity(new Intent(mActivity, ScanCodeActivity.class));
                break;
            case R.id.tv_chargeing_ljcz:
                startActivity(new Intent(mActivity, RechargeActivity.class));
                break;
            case btn_chargeing_submit:
                break;
        }
    }
}
