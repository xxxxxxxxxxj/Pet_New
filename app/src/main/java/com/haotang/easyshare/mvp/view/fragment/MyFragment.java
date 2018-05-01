package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundRelativeLayout;
import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.activity.LoginActivity;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.util.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.haotang.easyshare.R.id.iv_myfragment_add;
import static com.haotang.easyshare.R.id.ll_myfragment_mycdz;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 21:00
 */
public class MyFragment extends BaseFragment {
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
    @BindView(iv_myfragment_add)
    ImageView ivMyfragmentAdd;
    @BindView(R.id.iv_myfragment_cdcs)
    TextView ivMyfragmentCdcs;
    @BindView(R.id.iv_myfragment_img)
    ImageView ivMyfragmentImg;
    @BindView(R.id.rl_myfragment_img)
    RelativeLayout rlMyfragmentImg;
    @BindView(R.id.tv_myfragment_bjcdz)
    TextView tvMyfragmentBjcdz;
    @BindView(R.id.iv_myfragment_ggorgr)
    ImageView ivMyfragmentGgorgr;
    @BindView(R.id.tv_myfragment_name)
    TextView tvMyfragmentName;
    @BindView(R.id.tv_myfragment_cdf)
    TextView tvMyfragmentCdf;
    @BindView(R.id.tv_myfragment_fwf)
    TextView tvMyfragmentFwf;
    @BindView(ll_myfragment_mycdz)
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

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.myfragment;
    }

    @Override
    protected void initView() {
        rllMyfragmentUserinfo.bringToFront();
        ivMyfragmentUserimg.bringToFront();
        if(SystemUtil.checkLogin(mActivity)){
            llMyfragmentMycdz.setVisibility(View.VISIBLE);
            ivMyfragmentAdd.setVisibility(View.GONE);
        }else{
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

    @OnClick({iv_myfragment_add, R.id.tv_myfragment_bjcdz, R.id.rl_myfragment_clxx, R.id.rl_myfragment_sycs,
            R.id.rl_myfragment_hytq, R.id.rl_myfragment_wdtz, R.id.rl_myfragment_scdzd, R.id.rl_myfragment_gzdr,
            R.id.rl_myfragment_jjdh, R.id.rl_myfragment_srgj, R.id.rl_myfragment_gy, R.id.rtv_myfragment_tuichu,
    R.id.tv_myfragment_username})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_myfragment_username:
                if(SystemUtil.checkLogin(mActivity)){

                }else{
                    startActivity(new Intent(mActivity,LoginActivity.class));
                }
                break;
            case iv_myfragment_add:
                break;
            case R.id.tv_myfragment_bjcdz:
                break;
            case R.id.rl_myfragment_clxx:
                break;
            case R.id.rl_myfragment_sycs:
                break;
            case R.id.rl_myfragment_hytq:
                break;
            case R.id.rl_myfragment_wdtz:
                break;
            case R.id.rl_myfragment_scdzd:
                break;
            case R.id.rl_myfragment_gzdr:
                break;
            case R.id.rl_myfragment_jjdh:
                break;
            case R.id.rl_myfragment_srgj:
                break;
            case R.id.rl_myfragment_gy:
                break;
            case R.id.rtv_myfragment_tuichu:
                break;
        }
    }
}
