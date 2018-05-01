package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.widget.ShareBottomDialog;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.verticalbanner.VerticalBannerView;
import com.ljy.devring.DevRing;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充电桩详情
 */
public class ChargingPileDetailActivity extends BaseActivity {
    private final static String TAG = ChargingPileDetailActivity.class.getSimpleName();
    @BindView(R.id.iv_chargingdetail_back)
    ImageView ivChargingdetailBack;
    @BindView(R.id.iv_chargingdetail_sc)
    ImageView ivChargingdetailSc;
    @BindView(R.id.iv_chargingdetail_share)
    ImageView ivChargingdetailShare;
    @BindView(R.id.ll_chargingdetail_barright)
    LinearLayout llChargingdetailBarright;
    @BindView(R.id.rl_chargingdetail_title)
    RelativeLayout rlChargingdetailTitle;
    @BindView(R.id.tv_chargingdetail_pl)
    TextView tvChargingdetailPl;
    @BindView(R.id.ll_chargingdetail_pl)
    LinearLayout llChargingdetailPl;
    @BindView(R.id.iv_chargingdetail_cdcs)
    TextView ivChargingdetailCdcs;
    @BindView(R.id.iv_chargingdetail_img)
    ImageView ivChargingdetailImg;
    @BindView(R.id.rl_chargingdetail_img)
    RelativeLayout rlChargingdetailImg;
    @BindView(R.id.iv_chargingdetail_lt)
    ImageView ivChargingdetailLt;
    @BindView(R.id.iv_chargingdetail_phone)
    ImageView ivChargingdetailPhone;
    @BindView(R.id.ll_chargingdetail_ltdh)
    LinearLayout llChargingdetailLtdh;
    @BindView(R.id.iv_chargingdetail_ggorgr)
    ImageView ivChargingdetailGgorgr;
    @BindView(R.id.tv_chargingdetail_name)
    TextView tvChargingdetailName;
    @BindView(R.id.tv_chargingdetail_cdf)
    TextView tvChargingdetailCdf;
    @BindView(R.id.tv_chargingdetail_fwf)
    TextView tvChargingdetailFwf;
    @BindView(R.id.tv_chargingdetail_kuaichong_num)
    TextView tvChargingdetailKuaichongNum;
    @BindView(R.id.ll_chargingdetail_kuaichong)
    LinearLayout llChargingdetailKuaichong;
    @BindView(R.id.tv_chargingdetail_manchong_num)
    TextView tvChargingdetailManchongNum;
    @BindView(R.id.ll_chargingdetail_manchong)
    LinearLayout llChargingdetailManchong;
    @BindView(R.id.tv_chargingdetail_kongxian_num)
    TextView tvChargingdetailKongxianNum;
    @BindView(R.id.ll_chargingdetail_kongxian)
    LinearLayout llChargingdetailKongxian;
    @BindView(R.id.vbv_chargingdetail)
    VerticalBannerView vbvChargingdetail;
    @BindView(R.id.tv_chargingdetail_juli)
    TextView tvChargingdetailJuli;
    @BindView(R.id.ll_chargingdetail_daohang)
    LinearLayout llChargingdetailDaohang;
    @BindView(R.id.tv_chargingdetail_address)
    TextView tvChargingdetailAddress;
    @BindView(R.id.tv_chargingdetail_yys)
    TextView tvChargingdetailYys;
    @BindView(R.id.tv_chargingdetail_kfsj)
    TextView tvChargingdetailKfsj;
    @BindView(R.id.tv_chargingdetail_zffs)
    TextView tvChargingdetailZffs;
    @BindView(R.id.tv_chargingdetail_tcf)
    TextView tvChargingdetailTcf;
    @BindView(R.id.tv_chargingdetail_zdbz)
    TextView tvChargingdetailZdbz;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_charging_pile_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.iv_chargingdetail_back, R.id.iv_chargingdetail_sc, R.id.iv_chargingdetail_share, R.id.ll_chargingdetail_pl, R.id.iv_chargingdetail_lt, R.id.iv_chargingdetail_phone, R.id.ll_chargingdetail_daohang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_chargingdetail_back:
                finish();
                break;
            case R.id.iv_chargingdetail_sc:
                break;
            case R.id.iv_chargingdetail_share:
                ShareBottomDialog dialog = new ShareBottomDialog();
                dialog.setShareInfo("测试", "测试",
                        "https://www.duba.com", UrlConstants.getServiceBaseUrl() + "/static/icon/shouye.png?3");
                dialog.show(getSupportFragmentManager());
                break;
            case R.id.ll_chargingdetail_pl:
                startActivity(new Intent(ChargingPileDetailActivity.this,CommentActivity.class));
                break;
            case R.id.iv_chargingdetail_lt:
                break;
            case R.id.iv_chargingdetail_phone:
                SystemUtil.cellPhone(ChargingPileDetailActivity.this, "");
                break;
            case R.id.ll_chargingdetail_daohang:
                break;
        }
    }
}
