package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CityBean;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CityAdapter;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 切换城市页面
 */
public class SwitchCityActivity extends BaseActivity implements OnBannerListener {
    protected final static String TAG = SwitchCityActivity.class.getSimpleName();
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.banner_switch_city)
    Banner bannerSwitchCity;
    @BindView(R.id.iv_switch_city_serch)
    ImageView ivSwitchCitySerch;
    @BindView(R.id.et_switch_city)
    EditText etSwitchCity;
    @BindView(R.id.rv_switch_city)
    RecyclerView rvSwitchCity;
    private List<CityBean> cityList = new ArrayList<CityBean>();
    private CityAdapter cityAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_switch_city;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("切换城市");
        for (int i = 0; i < 100; i++) {
            if (i == 0) {
                cityList.add(new CityBean(i + 1, "北京" + (i + 1), true));
            } else {
                cityList.add(new CityBean(i + 1, "北京" + (i + 1), false));
            }
        }
        rvSwitchCity.setHasFixedSize(true);
        rvSwitchCity.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new NoScollFullGridLayoutManager(rvSwitchCity, this, 3, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvSwitchCity.setLayoutManager(noScollFullGridLayoutManager);
        rvSwitchCity.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        cityAdapter = new CityAdapter(R.layout.item_city
                , cityList);
        rvSwitchCity.setAdapter(cityAdapter);
        setBanner();
    }

    private void setBanner() {
        //本地图片数据（资源文件）
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.b1);
        list.add(R.mipmap.b2);
        list.add(R.mipmap.b3);
        bannerSwitchCity.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < cityList.size(); i++) {
                    if (i == position) {
                        cityList.get(i).setSelete(true);
                    } else {
                        cityList.get(i).setSelete(false);
                    }
                }
                cityAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.iv_switch_city_serch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.iv_switch_city_serch:
                break;
        }
    }

    @Override
    public void OnBannerClick(int position) {
        RingLog.e(TAG, "position:" + position);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        bannerSwitchCity.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        bannerSwitchCity.stopAutoPlay();
    }
}
