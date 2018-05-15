package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerAllBrandsActivityCommponent;
import com.haotang.easyshare.di.module.activity.AllBrandsActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.SelectedCarBean;
import com.haotang.easyshare.mvp.presenter.AllBrandsPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.HotPointCarAdapter;
import com.haotang.easyshare.mvp.view.adapter.SelectedCarAdapter;
import com.haotang.easyshare.mvp.view.iview.IAllBrandsView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
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
 * 所有品牌
 */
public class AllBrandsActivity extends BaseActivity<AllBrandsPresenter> implements IAllBrandsView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_allbrands_rmpp)
    RecyclerView rvAllbrandsRmpp;
    @BindView(R.id.rv_allbrands_jxtj)
    RecyclerView rvAllbrandsJxtj;
    private List<HotCarBean.DataBean> carList = new ArrayList<HotCarBean.DataBean>();
    private HotPointCarAdapter hotPointCarAdapter;
    private List<SelectedCarBean> selectedCarList = new ArrayList<SelectedCarBean>();
    private SelectedCarAdapter selectedCarAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_all_brands;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerAllBrandsActivityCommponent.builder().
                allBrandsActivityModule(new AllBrandsActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("所有品牌");
        rvAllbrandsRmpp.setHasFixedSize(true);
        rvAllbrandsRmpp.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                NoScollFullGridLayoutManager(rvAllbrandsRmpp, this, 5, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvAllbrandsRmpp.setLayoutManager(noScollFullGridLayoutManager);
        hotPointCarAdapter = new HotPointCarAdapter(R.layout.item_hotfrag_top_car, carList);
        rvAllbrandsRmpp.setAdapter(hotPointCarAdapter);

        for (int i = 0; i < 20; i++) {
            selectedCarList.add(new SelectedCarBean("http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433",
                    "荣威ERX5", "续航310公里", 20.88));
        }
        rvAllbrandsJxtj.setHasFixedSize(true);
        rvAllbrandsJxtj.setNestedScrollingEnabled(false);
        NoScollFullLinearLayoutManager noScollFullLinearLayoutManager = new NoScollFullLinearLayoutManager(this);
        noScollFullLinearLayoutManager.setScrollEnabled(false);
        rvAllbrandsJxtj.setLayoutManager(noScollFullLinearLayoutManager);
        //添加自定义分割线
        rvAllbrandsJxtj.addItemDecoration(new DividerLinearItemDecoration(this, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(this, 15),
                ContextCompat.getColor(this, R.color.af8f8f8)));
        selectedCarAdapter = new SelectedCarAdapter(R.layout.item_allbrands_selectedcar
                , selectedCarList);
        rvAllbrandsJxtj.setAdapter(selectedCarAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.list();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @Override
    protected void initEvent() {
        hotPointCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(AllBrandsActivity.this, BrandAreaActivity.class));
            }
        });
        selectedCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @OnClick({R.id.iv_titlebar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }

    @Override
    public void listSuccess(List<HotCarBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            carList.addAll(data);
            hotPointCarAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void listFail(int code, String msg) {
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }
}
