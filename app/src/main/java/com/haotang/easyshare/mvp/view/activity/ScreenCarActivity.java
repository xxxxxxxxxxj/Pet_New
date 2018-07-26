package com.haotang.easyshare.mvp.view.activity;

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
import com.haotang.easyshare.di.component.activity.DaggerScreenCarActivityCommponent;
import com.haotang.easyshare.di.module.activity.ScreenCarActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.MainTabEvent;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.entity.res.ScreenCarCondition;
import com.haotang.easyshare.mvp.presenter.ScreenCarPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.ScreenCarCategoryAdapter;
import com.haotang.easyshare.mvp.view.adapter.ScreenCarModelAdapter;
import com.haotang.easyshare.mvp.view.adapter.ScreenCarPriceAdapter;
import com.haotang.easyshare.mvp.view.adapter.ScreenCarRenewalAdapter;
import com.haotang.easyshare.mvp.view.adapter.SelectedCarAdapter;
import com.haotang.easyshare.mvp.view.adapter.SelectedCarTopTagAdapter;
import com.haotang.easyshare.mvp.view.iview.IScreenCarView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.util.DensityUtil;
import com.umeng.analytics.MobclickAgent;
import com.yyydjk.library.DropDownMenu;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private String headers[] = {"价格", "车型", "续航", "二手车"};
    private List<View> popupViews = new ArrayList<>();
    private View contentView;
    private List<ScreenCarCondition> priceList = new ArrayList<ScreenCarCondition>();
    private List<ScreenCarCondition> modelList = new ArrayList<ScreenCarCondition>();
    private List<ScreenCarCondition> renewalList = new ArrayList<ScreenCarCondition>();
    private List<ScreenCarCondition> categoryList = new ArrayList<ScreenCarCondition>();
    private List<ScreenCarCondition> tagList = new ArrayList<ScreenCarCondition>();
    private ScreenCarCategoryAdapter screenCarCategoryAdapter;
    private ScreenCarPriceAdapter screenCarPriceAdapter;
    private ScreenCarModelAdapter screenCarModelAdapter;
    private ScreenCarRenewalAdapter screenCarRenewalAdapter;
    private SwipeRefreshLayout srl_screencar;
    private RecyclerView rv_screencar;
    private List<HotSpecialCarBean.DataBean> selectedCarList = new ArrayList<HotSpecialCarBean.DataBean>();
    private SelectedCarAdapter selectedCarAdapter;
    private RecyclerView rv_screencar_toptag;
    private SelectedCarTopTagAdapter selectedCarTopTagAdapter;

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
                NoScollFullGridLayoutManager(rv_screencar_toptag, this, 4, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rv_screencar_toptag.setLayoutManager(noScollFullGridLayoutManager);
        rv_screencar_toptag.addItemDecoration(new GridSpacingItemDecoration(4,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
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
        for (int i = 0; i < 10; i++) {
            selectedCarList.add(new HotSpecialCarBean.DataBean("宝马5系新能源", "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg"
                    , i + 1, "49.89-49.89万元", "49.89-49.89万元", "49.89-49.89万元", null));
        }
        selectedCarAdapter = new SelectedCarAdapter(R.layout.item_allbrands_selectedcar
                , selectedCarList);
        rv_screencar.setAdapter(selectedCarAdapter);

        RecyclerView rvPrice = new RecyclerView(this);
        rvPrice.setBackgroundColor(getResources().getColor(R.color.white));
        rvPrice.setHasFixedSize(true);
        rvPrice.setLayoutManager(new
                GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rvPrice.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                priceList.add(new ScreenCarCondition(i + 1, 1, "不限", false));
            } else {
                priceList.add(new ScreenCarCondition(i + 1, 1, "100万以上", false));
            }
        }
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
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                modelList.add(new ScreenCarCondition(i + 1, 2, "不限", false));
            } else {
                modelList.add(new ScreenCarCondition(i + 1, 2, "大型车", false));
            }
        }
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
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                renewalList.add(new ScreenCarCondition(i + 1, 3, "不限", false));
            } else {
                renewalList.add(new ScreenCarCondition(i + 1, 3, "400公里以上", false));
            }
        }
        screenCarRenewalAdapter = new ScreenCarRenewalAdapter(R.layout.item_screensar_condition
                , renewalList);
        rvRenewal.setAdapter(screenCarRenewalAdapter);

        RecyclerView rvCategory = new RecyclerView(this);
        rvCategory.setBackgroundColor(getResources().getColor(R.color.white));
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new
                GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rvCategory.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                categoryList.add(new ScreenCarCondition(i + 1, 4, "不限", false));
            } else {
                categoryList.add(new ScreenCarCondition(i + 1, 4, "二手车", false));
            }
        }
        screenCarCategoryAdapter = new ScreenCarCategoryAdapter(R.layout.item_screensar_condition
                , categoryList);
        rvCategory.setAdapter(screenCarCategoryAdapter);

        popupViews.add(rvPrice);
        popupViews.add(rvModel);
        popupViews.add(rvRenewal);
        popupViews.add(rvCategory);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

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
                        } else if (screenCarCondition.getClassId() == 2) {
                            for (int i = 0; i < modelList.size(); i++) {
                                ScreenCarCondition screenCarCondition1 = modelList.get(i);
                                if (screenCarCondition1 != null && screenCarCondition1.getId() ==
                                        screenCarCondition.getId()) {
                                    modelList.remove(i);
                                    break;
                                }
                            }
                            dropDownMenu.setTabText(headers[1]);
                            screenCarModelAdapter.notifyDataSetChanged();
                        } else if (screenCarCondition.getClassId() == 3) {
                            for (int i = 0; i < renewalList.size(); i++) {
                                ScreenCarCondition screenCarCondition1 = renewalList.get(i);
                                if (screenCarCondition1 != null && screenCarCondition1.getId() ==
                                        screenCarCondition.getId()) {
                                    renewalList.remove(i);
                                    break;
                                }
                            }
                            dropDownMenu.setTabText(headers[2]);
                            screenCarRenewalAdapter.notifyDataSetChanged();
                        } else if (screenCarCondition.getClassId() == 4) {
                            for (int i = 0; i < categoryList.size(); i++) {
                                ScreenCarCondition screenCarCondition1 = categoryList.get(i);
                                if (screenCarCondition1 != null && screenCarCondition1.getId() ==
                                        screenCarCondition.getId()) {
                                    categoryList.remove(i);
                                    break;
                                }
                            }
                            dropDownMenu.setTabText(headers[3]);
                            screenCarCategoryAdapter.notifyDataSetChanged();
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
                    dropDownMenu.setTabText(position == 0 ? headers[0] : priceList.get(position).getName());
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
                    if (position > 0) {
                        tagList.add(screenCarCondition);
                    }
                    selectedCarTopTagAdapter.notifyDataSetChanged();
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
                    dropDownMenu.setTabText(position == 0 ? headers[1] : modelList.get(position).getName());
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
                    if (position > 0) {
                        tagList.add(screenCarCondition);
                    }
                    selectedCarTopTagAdapter.notifyDataSetChanged();
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
                    dropDownMenu.setTabText(position == 0 ? headers[2] : renewalList.get(position).getName());
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
                    if (position > 0) {
                        tagList.add(screenCarCondition);
                    }
                    selectedCarTopTagAdapter.notifyDataSetChanged();
                }
            }
        });
        screenCarCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (categoryList.size() > position) {
                    for (int i = 0; i < categoryList.size(); i++) {
                        ScreenCarCondition screenCarCondition = categoryList.get(i);
                        if (screenCarCondition != null && screenCarCondition.isSelect()) {
                            screenCarCondition.setSelect(false);
                        }
                    }
                    ScreenCarCondition screenCarCondition = categoryList.get(position);
                    if (screenCarCondition != null) {
                        screenCarCondition.setSelect(true);
                    }
                    screenCarCategoryAdapter.notifyDataSetChanged();
                    dropDownMenu.setTabText(position == 0 ? headers[3] : categoryList.get(position).getName());
                    dropDownMenu.closeMenu();
                    if (tagList.size() > 0) {
                        for (int i = 0; i < tagList.size(); i++) {
                            ScreenCarCondition screenCarCondition1 = tagList.get(i);
                            if (screenCarCondition1 != null && screenCarCondition1.getClassId() == 4) {
                                tagList.remove(i);
                                break;
                            }
                        }
                    }
                    if (position > 0) {
                        tagList.add(screenCarCondition);
                    }
                    selectedCarTopTagAdapter.notifyDataSetChanged();
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
}
