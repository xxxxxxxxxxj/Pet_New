package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundRelativeLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerSerchAddressActivityCommponent;
import com.haotang.easyshare.di.module.activity.SerchAddressActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.SerchResult;
import com.haotang.easyshare.mvp.presenter.SerchAddressPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MainSerchResultAdapter;
import com.haotang.easyshare.mvp.view.iview.ISerchAddressView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.util.RingToast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 搜索地址页
 */
public class SerchAddressActivity extends BaseActivity<SerchAddressPresenter> implements ISerchAddressView, PoiSearch.OnPoiSearchListener {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.et_serchaddress)
    EditText etSerchaddress;
    @BindView(R.id.rll_serchaddress_serch)
    RoundRelativeLayout rllSerchaddressSerch;
    @BindView(R.id.rv_serchaddress_result)
    RecyclerView rvSerchaddressResult;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private List<SerchResult> serchList = new ArrayList<SerchResult>();
    private MainSerchResultAdapter mainSerchResultAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_serch_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerSerchAddressActivityCommponent.builder().
                serchAddressActivityModule(new SerchAddressActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        mainSerchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DevRing.busManager().postEvent(serchList.get(position));
                finish();
            }
        });
        etSerchaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                query = new PoiSearch.Query(StringUtil.checkEditText(etSerchaddress), "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
                //query.setPageSize(10);// 设置每页最多返回多少条poiitem
                query.setPageNum(0);// 设置查第一页
                poiSearch = new PoiSearch(SerchAddressActivity.this, query);
                poiSearch.setOnPoiSearchListener(SerchAddressActivity.this);
                poiSearch.searchPOIAsyn();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.tv_serchaddress_other, R.id.iv_serchaddress_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_serchaddress_other:
                finish();
                break;
            case R.id.iv_serchaddress_clear:
                etSerchaddress.setText("");
                rllSerchaddressSerch.setAnimation(SystemUtil.shakeAnimation(5));
                break;
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems != null && poiItems.size() > 0) {
                        serchList.clear();
                        serchList.add(new SerchResult("目的地", "", 0, 0));
                        for (int i = 0; i < poiItems.size(); i++) {
                            PoiItem poiItem = poiItems.get(i);
                            if (poiItem != null) {
                                serchList.add(new SerchResult(poiItem.getAdName(), poiItem.getDirection(),
                                        poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude()));
                            }
                        }
                        rvSerchaddressResult.setHasFixedSize(true);
                        rvSerchaddressResult.setLayoutManager(new LinearLayoutManager(SerchAddressActivity.this));
                        mainSerchResultAdapter = new MainSerchResultAdapter(R.layout.item_mainserchresult, serchList);
                        rvSerchaddressResult.setAdapter(mainSerchResultAdapter);
                        rvSerchaddressResult.addItemDecoration(new DividerItemDecoration(SerchAddressActivity.this, DividerItemDecoration.VERTICAL));
                        mainSerchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                            }
                        });
                    } else {
                        RingToast.show(R.string.no_result);
                    }
                }
            } else {
                RingToast.show(R.string.no_result);
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
