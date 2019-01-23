package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerScreenCarActivityCommponent;
import com.haotang.easyshare.di.module.activity.ScreenCarActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.entity.res.ScreenCarCondition;
import com.haotang.easyshare.mvp.model.entity.res.ScreenCarItem;
import com.haotang.easyshare.mvp.presenter.ScreenCarPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.ScreenCarAdapter;
import com.haotang.easyshare.mvp.view.adapter.ScreenCarModelAdapter;
import com.haotang.easyshare.mvp.view.adapter.ScreenCarPriceAdapter;
import com.haotang.easyshare.mvp.view.adapter.ScreenCarRenewalAdapter;
import com.haotang.easyshare.mvp.view.adapter.SelectedCarTopTagAdapter;
import com.haotang.easyshare.mvp.view.iview.IScreenCarView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 筛选车界面
 */
public class ScreenCarActivity extends BaseActivity<ScreenCarPresenter> implements IScreenCarView {
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private int brandId;
    private int mNextRequestPage = 1;
    private int pageSize;
    private String brand;
    private String headers[] = {"价格", "车型", "续航"};
    private List<View> popupViews = new ArrayList<>();
    private View contentView;
    private List<ScreenCarCondition> priceList = new ArrayList<ScreenCarCondition>();
    private List<ScreenCarCondition> modelList = new ArrayList<ScreenCarCondition>();
    private List<ScreenCarCondition> renewalList = new ArrayList<ScreenCarCondition>();
    private List<ScreenCarCondition> tagList = new ArrayList<ScreenCarCondition>();
    private ScreenCarPriceAdapter screenCarPriceAdapter;
    private ScreenCarModelAdapter screenCarModelAdapter;
    private ScreenCarRenewalAdapter screenCarRenewalAdapter;
    private SwipeRefreshLayout srl_screencar;
    private RecyclerView rv_screencar;
    private List<HotSpecialCarBean.DataBean> selectedCarList = new ArrayList<HotSpecialCarBean.DataBean>();
    private ScreenCarAdapter screenCarAdapter;
    private RecyclerView rv_screencar_toptag;
    private SelectedCarTopTagAdapter selectedCarTopTagAdapter;
    private String price;
    private String batteryLife;
    private String car;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_screen_car;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        brandId = getIntent().getIntExtra("brandId", 0);
        brand = getIntent().getStringExtra("brand");
        activityListManager.addActivity(this);
        DaggerScreenCarActivityCommponent.builder().
                screenCarActivityModule(new ScreenCarActivityModule(this, this)).build().inject(this);
        contentView = View.inflate(this, R.layout.screencar_contentview, null);
        srl_screencar = (SwipeRefreshLayout) contentView.findViewById(R.id.srl_screencar);
        rv_screencar = (RecyclerView) contentView.findViewById(R.id.rv_screencar);
        rv_screencar_toptag = (RecyclerView) contentView.findViewById(R.id.rv_screencar_toptag);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText(brand);

        rv_screencar_toptag.setHasFixedSize(true);
        rv_screencar_toptag.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                NoScollFullGridLayoutManager(rv_screencar_toptag, this, 3, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rv_screencar_toptag.setLayoutManager(noScollFullGridLayoutManager);
        rv_screencar_toptag.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing10),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing10),
                true));
        selectedCarTopTagAdapter = new SelectedCarTopTagAdapter(R.layout.item_screensar_toptag
                , tagList);
        rv_screencar_toptag.setAdapter(selectedCarTopTagAdapter);

        srl_screencar.setRefreshing(true);
        srl_screencar.setColorSchemeColors(Color.rgb(47, 223, 189));
        rv_screencar.setHasFixedSize(true);
        rv_screencar.setLayoutManager(new LinearLayoutManager(this));
        //添加自定义分割线
        rv_screencar.addItemDecoration(new DividerLinearItemDecoration(this, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(this, 15),
                ContextCompat.getColor(this, R.color.af8f8f8)));
        screenCarAdapter = new ScreenCarAdapter(R.layout.item_screencar
                , selectedCarList);
        rv_screencar.setAdapter(screenCarAdapter);

        RecyclerView rvPrice = new RecyclerView(this);
        rvPrice.setBackgroundColor(getResources().getColor(R.color.white));
        rvPrice.setHasFixedSize(true);
        rvPrice.setLayoutManager(new
                GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rvPrice.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        screenCarPriceAdapter = new ScreenCarPriceAdapter(R.layout.item_screensar_condition
                , priceList);
        rvPrice.setAdapter(screenCarPriceAdapter);

        RecyclerView rvModel = new RecyclerView(this);
        rvModel.setBackgroundColor(getResources().getColor(R.color.white));
        rvModel.setHasFixedSize(true);
        rvModel.setLayoutManager(new
                GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rvModel.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        screenCarModelAdapter = new ScreenCarModelAdapter(R.layout.item_screensar_condition
                , modelList);
        rvModel.setAdapter(screenCarModelAdapter);

        RecyclerView rvRenewal = new RecyclerView(this);
        rvRenewal.setBackgroundColor(getResources().getColor(R.color.white));
        rvRenewal.setHasFixedSize(true);
        rvRenewal.setLayoutManager(new
                GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rvRenewal.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        screenCarRenewalAdapter = new ScreenCarRenewalAdapter(R.layout.item_screensar_condition
                , renewalList);
        rvRenewal.setAdapter(screenCarRenewalAdapter);

        popupViews.add(rvPrice);
        popupViews.add(rvModel);
        popupViews.add(rvRenewal);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        mPresenter.items(UrlConstants.getMapHeader(this));
        refresh();
    }

    private void refresh() {
        showDialog();
        screenCarAdapter.setEnableLoadMore(false);
        srl_screencar.setRefreshing(true);
        mNextRequestPage = 1;
        getCarList();
    }

    private void loadMore() {
        getCarList();
    }

    private void getCarList() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("page", mNextRequestPage + "");
        if (StringUtil.isNotEmpty(price)) {
            builder.addFormDataPart("price", price);
        }
        if (StringUtil.isNotEmpty(batteryLife)) {
            builder.addFormDataPart("batteryLife", batteryLife);
        }
        if (StringUtil.isNotEmpty(car)) {
            builder.addFormDataPart("car", car);
        }
        builder.addFormDataPart("brandId", brandId + "");
        RequestBody build = builder.build();
        mPresenter.query(UrlConstants.getMapHeader(this), build);
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
    protected void initEvent() {
        screenCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (selectedCarList.size() > position) {
                    HotSpecialCarBean.DataBean dataBean = selectedCarList.get(position);
                    Intent intent = new Intent(ScreenCarActivity.this, CarDetailActivity.class);
                    intent.putExtra("carId", dataBean.getId());
                    startActivity(intent);
                }
            }
        });
        screenCarAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srl_screencar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        selectedCarTopTagAdapter.setOnTagDeleteListener(new SelectedCarTopTagAdapter.OnTagDeleteListener() {
            @Override
            public void OnTagDelete(int position) {
                if (tagList.size() > position) {
                    ScreenCarCondition screenCarCondition = tagList.get(position);
                    if (screenCarCondition != null) {
                        if (screenCarCondition.getClassId() == 1) {
                            for (int i = 0; i < priceList.size(); i++) {
                                ScreenCarCondition screenCarCondition1 = priceList.get(i);
                                if (screenCarCondition1 != null && screenCarCondition1.getId() ==
                                        screenCarCondition.getId()) {
                                    priceList.get(i).setSelect(false);
                                    break;
                                }
                            }
                            dropDownMenu.setTabText(headers[0]);
                            screenCarPriceAdapter.notifyDataSetChanged();
                            price = null;
                            refresh();
                        } else if (screenCarCondition.getClassId() == 2) {
                            for (int i = 0; i < modelList.size(); i++) {
                                ScreenCarCondition screenCarCondition1 = modelList.get(i);
                                if (screenCarCondition1 != null && screenCarCondition1.getId() ==
                                        screenCarCondition.getId()) {
                                    modelList.get(i).setSelect(false);
                                    break;
                                }
                            }
                            dropDownMenu.setTabText(headers[1]);
                            screenCarModelAdapter.notifyDataSetChanged();
                            car = null;
                            refresh();
                        } else if (screenCarCondition.getClassId() == 3) {
                            for (int i = 0; i < renewalList.size(); i++) {
                                ScreenCarCondition screenCarCondition1 = renewalList.get(i);
                                if (screenCarCondition1 != null && screenCarCondition1.getId() ==
                                        screenCarCondition.getId()) {
                                    renewalList.get(i).setSelect(false);
                                    break;
                                }
                            }
                            dropDownMenu.setTabText(headers[2]);
                            screenCarRenewalAdapter.notifyDataSetChanged();
                            batteryLife = null;
                            refresh();
                        }
                        tagList.remove(position);
                        selectedCarTopTagAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        screenCarPriceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (priceList.size() > position) {
                    for (int i = 0; i < priceList.size(); i++) {
                        ScreenCarCondition screenCarCondition = priceList.get(i);
                        if (screenCarCondition != null && screenCarCondition.isSelect()) {
                            screenCarCondition.setSelect(false);
                        }
                    }
                    ScreenCarCondition screenCarCondition = priceList.get(position);
                    if (screenCarCondition != null) {
                        screenCarCondition.setSelect(true);
                    }
                    screenCarPriceAdapter.notifyDataSetChanged();
                    dropDownMenu.closeMenu();
                    if (tagList.size() > 0) {
                        for (int i = 0; i < tagList.size(); i++) {
                            ScreenCarCondition screenCarCondition1 = tagList.get(i);
                            if (screenCarCondition1 != null && screenCarCondition1.getClassId() == 1) {
                                tagList.remove(i);
                                break;
                            }
                        }
                    }
                    tagList.add(screenCarCondition);
                    selectedCarTopTagAdapter.notifyDataSetChanged();
                    price = priceList.get(position).getValue();
                    refresh();
                }
            }
        });
        screenCarModelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (modelList.size() > position) {
                    for (int i = 0; i < modelList.size(); i++) {
                        ScreenCarCondition screenCarCondition = modelList.get(i);
                        if (screenCarCondition != null && screenCarCondition.isSelect()) {
                            screenCarCondition.setSelect(false);
                        }
                    }
                    ScreenCarCondition screenCarCondition = modelList.get(position);
                    if (screenCarCondition != null) {
                        screenCarCondition.setSelect(true);
                    }
                    screenCarModelAdapter.notifyDataSetChanged();
                    dropDownMenu.closeMenu();
                    if (tagList.size() > 0) {
                        for (int i = 0; i < tagList.size(); i++) {
                            ScreenCarCondition screenCarCondition1 = tagList.get(i);
                            if (screenCarCondition1 != null && screenCarCondition1.getClassId() == 2) {
                                tagList.remove(i);
                                break;
                            }
                        }
                    }
                    tagList.add(screenCarCondition);
                    selectedCarTopTagAdapter.notifyDataSetChanged();
                    car = modelList.get(position).getValue();
                    refresh();
                }
            }
        });
        screenCarRenewalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (renewalList.size() > position) {
                    for (int i = 0; i < renewalList.size(); i++) {
                        ScreenCarCondition screenCarCondition = renewalList.get(i);
                        if (screenCarCondition != null && screenCarCondition.isSelect()) {
                            screenCarCondition.setSelect(false);
                        }
                    }
                    ScreenCarCondition screenCarCondition = renewalList.get(position);
                    if (screenCarCondition != null) {
                        screenCarCondition.setSelect(true);
                    }
                    screenCarRenewalAdapter.notifyDataSetChanged();
                    dropDownMenu.closeMenu();
                    if (tagList.size() > 0) {
                        for (int i = 0; i < tagList.size(); i++) {
                            ScreenCarCondition screenCarCondition1 = tagList.get(i);
                            if (screenCarCondition1 != null && screenCarCondition1.getClassId() == 3) {
                                tagList.remove(i);
                                break;
                            }
                        }
                    }
                    tagList.add(screenCarCondition);
                    selectedCarTopTagAdapter.notifyDataSetChanged();
                    batteryLife = renewalList.get(position).getValue();
                    refresh();
                }
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @Override
    public void itemsSuccess(ScreenCarItem.DataBean data) {
        disMissDialog();
        if (data != null) {
            List<ScreenCarItem.DataBean.BatteryLifesBean> batteryLifes = data.getBatteryLifes();
            List<ScreenCarItem.DataBean.CarsBean> cars = data.getCars();
            List<ScreenCarItem.DataBean.PricesBean> prices = data.getPrices();
            if (prices != null && prices.size() > 0) {
                priceList.clear();
                for (int i = 0; i < prices.size(); i++) {
                    priceList.add(new ScreenCarCondition(i + 1, 1, prices.get(i).getName(), prices.get(i).getValue(), false));
                }
                screenCarPriceAdapter.notifyDataSetChanged();
            }
            if (cars != null && cars.size() > 0) {
                modelList.clear();
                for (int i = 0; i < cars.size(); i++) {
                    modelList.add(new ScreenCarCondition(i + 1, 2, cars.get(i).getName(), cars.get(i).getValue(), false));
                }
                screenCarModelAdapter.notifyDataSetChanged();
            }
            if (batteryLifes != null && batteryLifes.size() > 0) {
                renewalList.clear();
                for (int i = 0; i < batteryLifes.size(); i++) {
                    renewalList.add(new ScreenCarCondition(i + 1, 3, batteryLifes.get(i).getName(), batteryLifes.get(i).getValue(), false));
                }
                screenCarRenewalAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void itemsFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "itemsFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void querySuccess(List<HotSpecialCarBean.DataBean> data) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            srl_screencar.setRefreshing(false);
            screenCarAdapter.setEnableLoadMore(true);
            selectedCarList.clear();
        }
        screenCarAdapter.loadMoreComplete();
        if (data != null && data.size() > 0) {
            if (mNextRequestPage == 1) {
                pageSize = data.size();
            } else {
                if (data.size() < pageSize) {
                    screenCarAdapter.loadMoreEnd(false);
                }
            }
            selectedCarList.addAll(data);
            mNextRequestPage++;
        } else {
            if (mNextRequestPage == 1) {
                screenCarAdapter.loadMoreEnd(true);
                screenCarAdapter.setEmptyView(setEmptyViewBase(2, "暂无数据", R.mipmap.no_data, null));
            } else {
                screenCarAdapter.loadMoreEnd(false);
            }
        }
        screenCarAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryFail(int code, String msg) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            screenCarAdapter.setEnableLoadMore(true);
            srl_screencar.setRefreshing(false);
        } else {
            screenCarAdapter.loadMoreFail();
        }
        screenCarAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "queryFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }
}
