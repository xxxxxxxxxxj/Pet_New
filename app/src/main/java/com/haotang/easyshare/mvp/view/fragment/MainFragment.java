package com.haotang.easyshare.mvp.view.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.flyco.roundview.RoundRelativeLayout;
import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 20:34
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.mv_mainfrag_map)
    MapView mvMainfragMap;
    @BindView(R.id.tv_mainfrag_city)
    TextView tvMainfragCity;
    @BindView(R.id.iv_mainfrag_city)
    ImageView ivMainfragCity;
    @BindView(R.id.ll_mainfrag_city)
    LinearLayout llMainfragCity;
    @BindView(R.id.iv_mainfrag_send_redpoint)
    ImageView ivMainfragSendRedpoint;
    @BindView(R.id.rl_mainfrag_send)
    RelativeLayout rlMainfragSend;
    @BindView(R.id.et_mainfrag_serch)
    EditText etMainfragSerch;
    @BindView(R.id.rll_mainfrag_serch)
    RoundRelativeLayout rllMainfragSerch;
    @BindView(R.id.rtv_mainfrag_local)
    RoundTextView rtvMainfragLocal;
    private AMap aMap;
    private UiSettings mUiSettings;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.mainfragment;
    }

    @Override
    protected void initView() {
        if (aMap == null) {
            aMap = mvMainfragMap.getMap();
            mUiSettings = aMap.getUiSettings();
            setUpMap();
        }
        rtvMainfragLocal.bringToFront();
        rllMainfragSerch.bringToFront();
    }

    private void setUpMap() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.ll_mainfrag_city, R.id.rl_mainfrag_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_mainfrag_city:
                break;
            case R.id.rl_mainfrag_send:
                break;
        }
    }
}
