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
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
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
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.id.list;

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
    private List<HotSpecialCarBean.DataBean> selectedCarList = new ArrayList<HotSpecialCarBean.DataBean>();
    private SelectedCarAdapter selectedCarAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_all_brands;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
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
        showDialog();
        mPresenter.list();
        mPresenter.special();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @Override
    protected void initEvent() {
        hotPointCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (carList != null && carList.size() > 0 && carList.size() > position) {
                    HotCarBean.DataBean dataBean = carList.get(position);
                    if (dataBean != null) {
                        Intent intent = new Intent(AllBrandsActivity.this, BrandAreaActivity.class);
                        intent.putExtra("brandId", dataBean.getId());
                        intent.putExtra("brand", dataBean.getBrand());
                        startActivity(intent);
                    }
                }
            }
        });
        selectedCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (selectedCarList.size() > 0 && selectedCarList.size() > position) {
                    HotSpecialCarBean.DataBean dataBean = selectedCarList.get(position);
                    if (dataBean != null) {
                        PostBean.DataBean.ShareMap shareMap = dataBean.getShareMap();
                        if (shareMap != null) {
                            Intent intent = new Intent(AllBrandsActivity.this, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL_KEY, shareMap.getUrl());
                            intent.putExtra("uuid", dataBean.getUuid());
                            startActivity(intent);
                        }
                    }
                }
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
        disMissDialog();
        if (data != null && data.size() > 0) {
            carList.addAll(data);
            hotPointCarAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void specialSuccess(List<HotSpecialCarBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            selectedCarList.addAll(data);
            selectedCarAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void specialFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "specialFail() status = " + code + "---desc = " + msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
