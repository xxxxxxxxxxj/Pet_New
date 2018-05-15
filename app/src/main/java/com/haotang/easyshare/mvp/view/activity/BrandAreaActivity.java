package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerBrandAreaActivityCommponent;
import com.haotang.easyshare.di.module.activity.BrandAreaActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.BrandAreaBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.SelectedCarBean;
import com.haotang.easyshare.mvp.presenter.BrandAreaPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.BrandAreaAdapter;
import com.haotang.easyshare.mvp.view.iview.IBrandAreaView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 品牌专区
 */
public class BrandAreaActivity extends BaseActivity<BrandAreaPresenter> implements IBrandAreaView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_brand_area)
    RecyclerView rvBrandArea;
    private List<BrandAreaBean> brandAreaList = new ArrayList<BrandAreaBean>();
    private List<BrandAreaBean.BannerBean> bannerList = new ArrayList<BrandAreaBean.BannerBean>();
    private List<BrandAreaBean.AdBean> adList = new ArrayList<BrandAreaBean.AdBean>();
    private BrandAreaAdapter brandAreaAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_brand_area;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerBrandAreaActivityCommponent.builder().
                brandAreaActivityModule(new BrandAreaActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("品牌专区");
        tvTitlebarOther.setText("发帖");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        for (int i = 0; i < 5; i++) {
            bannerList.add(new BrandAreaBean.BannerBean("http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"));
        }
        for (int i = 0; i < 5; i++) {
            adList.add(new BrandAreaBean.AdBean("http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433",2,"http://www.baidu.com"));
        }
        for (int i = 0; i < 50; i++) {
            int itemType = 1;
            if (i >= 0 && i <= 3) {
                itemType = ((i + 1) % 4 == 0) ? 4 : (i + 1);
            } else if (i >= 4 && i <= 7) {
                itemType = ((i - 4 * 1 + 1) % 4 == 0) ? 4 : (i - 4 * 1 + 1);
            } else if (i >= 8 && i <= 11) {
                itemType = ((i - 4 * 2 + 1) % 4 == 0) ? 4 : (i - 4 * 2 + 1);
            } else if (i >= 12 && i <= 15) {
                itemType = ((i - 4 * 3 + 1) % 4 == 0) ? 4 : (i - 4 * 3 + 1);
            } else if (i >= 16 && i <= 19) {
                itemType = ((i - 4 * 4 + 1) % 4 == 0) ? 4 : (i - 4 * 4 + 1);
            } else if (i >= 20 && i <= 23) {
                itemType = ((i - 4 * 5 + 1) % 4 == 0) ? 4 : (i - 4 * 5 + 1);
            } else if (i >= 24 && i <= 27) {
                itemType = ((i - 4 * 6 + 1) % 4 == 0) ? 4 : (i - 4 * 6 + 1);
            } else if (i >= 28 && i <= 31) {
                itemType = ((i - 4 * 7 + 1) % 4 == 0) ? 4 : (i - 4 * 7 + 1);
            } else if (i >= 32 && i <= 35) {
                itemType = ((i - 4 * 8 + 1) % 4 == 0) ? 4 : (i - 4 * 8 + 1);
            } else if (i >= 36 && i <= 39) {
                itemType = ((i - 4 * 9 + 1) % 4 == 0) ? 4 : (i - 4 * 9 + 1);
            } else if (i >= 40 && i <= 43) {
                itemType = ((i - 4 * 10 + 1) % 4 == 0) ? 4 : (i - 4 * 10 + 1);
            } else if (i >= 44 && i <= 47) {
                itemType = ((i - 4 * 11 + 1) % 4 == 0) ? 4 : (i - 4 * 11 + 1);
            } else if (i >= 48 && i <= 51) {
                itemType = ((i - 4 * 12 + 1) % 4 == 0) ? 4 : (i - 4 * 12 + 1);
            } else if (i >= 52 && i <= 55) {
                itemType = ((i - 4 * 13 + 1) % 4 == 0) ? 4 : (i - 4 * 13 + 1);
            } else if (i >= 56 && i <= 59) {
                itemType = ((i - 4 * 14 + 1) % 4 == 0) ? 4 : (i - 4 * 14 + 1);
            } else if (i >= 60 && i <= 63) {
                itemType = ((i - 4 * 15 + 1) % 4 == 0) ? 4 : (i - 4 * 15 + 1);
            } else if (i >= 64 && i <= 67) {
                itemType = ((i - 4 * 16 + 1) % 4 == 0) ? 4 : (i - 4 * 16 + 1);
            } else if (i >= 68 && i <= 71) {
                itemType = ((i - 4 * 17 + 1) % 4 == 0) ? 4 : (i - 4 * 17 + 1);
            } else if (i >= 72 && i <= 75) {
                itemType = ((i - 4 * 18 + 1) % 4 == 0) ? 4 : (i - 4 * 18 + 1);
            } else if (i >= 76 && i <= 79) {
                itemType = ((i - 4 * 19 + 1) % 4 == 0) ? 4 : (i - 4 * 19 + 1);
            } else if (i >= 80 && i <= 83) {
                itemType = ((i - 4 * 20 + 1) % 4 == 0) ? 4 : (i - 4 * 20 + 1);
            } else if (i >= 84 && i <= 87) {
                itemType = ((i - 4 * 21 + 1) % 4 == 0) ? 4 : (i - 4 * 21 + 1);
            } else if (i >= 88 && i <= 91) {
                itemType = ((i - 4 * 22 + 1) % 4 == 0) ? 4 : (i - 4 * 22 + 1);
            } else if (i >= 92 && i <= 95) {
                itemType = ((i - 4 * 23 + 1) % 4 == 0) ? 4 : (i - 4 * 23 + 1);
            } else if (i >= 96 && i <= 99) {
                itemType = ((i - 4 * 24 + 1) % 4 == 0) ? 4 : (i - 4 * 24 + 1);
            }
            RingLog.d("itemType = " + itemType);
        }
        brandAreaList.add(new BrandAreaBean(2, new BrandAreaBean.ReXiaOBean("比亚迪热销51")));
        brandAreaList.add(new BrandAreaBean(2, new BrandAreaBean.ReXiaOBean("比亚迪热销52")));
        brandAreaList.add(new BrandAreaBean(2, new BrandAreaBean.ReXiaOBean("比亚迪热销53")));
        brandAreaList.add(new BrandAreaBean(2, new BrandAreaBean.ReXiaOBean("比亚迪热销54")));
        rvBrandArea.setHasFixedSize(true);
        rvBrandArea.setLayoutManager(new LinearLayoutManager(this));
        //添加自定义分割线
        rvBrandArea.addItemDecoration(new DividerLinearItemDecoration(this, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(this, 15),
                ContextCompat.getColor(this, R.color.af8f8f8)));
        brandAreaAdapter = new BrandAreaAdapter(this
                , brandAreaList);
        rvBrandArea.setAdapter(brandAreaAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @Override
    protected void initEvent() {
        brandAreaAdapter.setOnBannerItemListener(new BrandAreaAdapter.OnBannerItemListener() {
            @Override
            public void OnBannerItem(int position) {
                RingLog.d("position = " + position);
                brandAreaList.remove(position);
                brandAreaAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                startActivity(new Intent(BrandAreaActivity.this, SendPostActivity.class));
                break;
        }
    }
}
