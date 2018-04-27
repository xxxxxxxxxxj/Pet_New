package com.haotang.easyshare.mvp.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/26 14:49
 */
public class MainFragmenHeader {
    @BindView(R.id.rtv_mainfrag_local)
    RoundTextView rtvMainfragLocal;
    @BindView(R.id.tmv_mainfrag_map)
    MapView tmv_mainfrag_map;
    @BindView(R.id.iv_mainfrag_rmht1)
    ImageView ivMainfragRmht1;
    @BindView(R.id.iv_mainfrag_rmht2)
    ImageView ivMainfragRmht2;
    @BindView(R.id.iv_mainfrag_rmht3)
    ImageView ivMainfragRmht3;
    @BindView(R.id.ll_mainfrag_rmht)
    LinearLayout llMainfragRmht;
    @BindView(R.id.rl_mainfrag_localev_more)
    LinearLayout rlMainfragLocalevMore;
    @BindView(R.id.rl_mainfrag_localev)
    RelativeLayout rlMainfragLocalev;
    @BindView(R.id.tv_mainfrag_localev_gg)
    TextView tvMainfragLocalevGg;
    @BindView(R.id.vw_mainfrag_localev_gg)
    View vwMainfragLocalevGg;
    @BindView(R.id.rl_mainfrag_localev_gg)
    RelativeLayout rlMainfragLocalevGg;
    @BindView(R.id.tv_mainfrag_localev_gr)
    TextView tvMainfragLocalevGr;
    @BindView(R.id.vw_mainfrag_localev_gr)
    View vwMainfragLocalevGr;
    @BindView(R.id.rl_mainfrag_localev_gr)
    RelativeLayout rlMainfragLocalevGr;

    public RoundTextView getRtvMainfragLocal() {
        return rtvMainfragLocal;
    }

    public MapView getTmv_mainfrag_map() {
        return tmv_mainfrag_map;
    }

    public ImageView getIvMainfragRmht1() {
        return ivMainfragRmht1;
    }

    public ImageView getIvMainfragRmht2() {
        return ivMainfragRmht2;
    }

    public ImageView getIvMainfragRmht3() {
        return ivMainfragRmht3;
    }

    public LinearLayout getLlMainfragRmht() {
        return llMainfragRmht;
    }

    public LinearLayout getRlMainfragLocalevMore() {
        return rlMainfragLocalevMore;
    }

    public RelativeLayout getRlMainfragLocalev() {
        return rlMainfragLocalev;
    }

    public TextView getTvMainfragLocalevGg() {
        return tvMainfragLocalevGg;
    }

    public View getVwMainfragLocalevGg() {
        return vwMainfragLocalevGg;
    }

    public RelativeLayout getRlMainfragLocalevGg() {
        return rlMainfragLocalevGg;
    }

    public TextView getTvMainfragLocalevGr() {
        return tvMainfragLocalevGr;
    }

    public View getVwMainfragLocalevGr() {
        return vwMainfragLocalevGr;
    }

    public RelativeLayout getRlMainfragLocalevGr() {
        return rlMainfragLocalevGr;
    }

    public MainFragmenHeader(View headerRootView) {
        ButterKnife.bind(this, headerRootView);
    }
}
