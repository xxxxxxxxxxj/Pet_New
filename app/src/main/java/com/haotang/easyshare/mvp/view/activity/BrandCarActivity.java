package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerBrandCarActivityCommponent;
import com.haotang.easyshare.di.module.activity.BrandCarActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.presenter.BrandCarPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.BrandCarAdapter;
import com.haotang.easyshare.mvp.view.iview.IBrandCarView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.mvp.view.widget.PinyinComparator;
import com.haotang.easyshare.mvp.view.widget.SideBar;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 车型选择界面
 */
public class BrandCarActivity extends BaseActivity<BrandCarPresenter> implements IBrandCarView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.tv_brand_car_hint)
    TextView tvBrandCarHint;
    @BindView(R.id.sb_brand_car_sidebar)
    SideBar sbBrandCarSidebar;
    @BindView(R.id.rv_brand_car)
    RecyclerView rvBrandCar;
    private int brandId;
    private List<HotCarBean.DataBean> list = new ArrayList<HotCarBean.DataBean>();
    private BrandCarAdapter brandCarAdapter;
    private PinyinComparator pinyinComparator;
    private int brandposition;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_brand_car;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
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
    protected void initView(Bundle savedInstanceState) {
        pinyinComparator = new PinyinComparator();
        activityListManager.addActivity(this);
        DaggerBrandCarActivityCommponent.builder().
                brandCarActivityModule(new BrandCarActivityModule(this, this)).build().inject(this);
        brandId = getIntent().getIntExtra("brandId", 0);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("选择车型");
        rvBrandCar.setHasFixedSize(true);
        rvBrandCar.setLayoutManager(new LinearLayoutManager(this));
        brandCarAdapter = new BrandCarAdapter(R.layout.item_brand_car, list);
        rvBrandCar.setAdapter(brandCarAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        mPresenter.list();
    }

    @Override
    protected void initEvent() {
        /*sbBrandCarSidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                try {
                    int position = brandCarAdapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        rvBrandCar.scrollToPosition(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
        brandCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list != null && list.size() > 0 && list.size() > position) {
                    HotCarBean.DataBean dataBean = list.get(position);
                    if (dataBean != null) {
                        brandposition = position;
                        showDialog();
                        //构建body
                        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                        builder.addFormDataPart("brandId", String.valueOf(dataBean.getId()));
                        RequestBody body = builder.build();
                        mPresenter.carList(body);
                    }
                }
            }
        });
        brandCarAdapter.setOnCarItemClickListener(new BrandCarAdapter.OnCarItemClickListener() {
            @Override
            public void OnCarItemClick(HotSpecialCarBean.DataBean dataBean) {
                Intent intent = new Intent(BrandCarActivity.this, SendPostActivity.class);
                intent.putExtra("carId", dataBean.getId());
                intent.putExtra("carName", dataBean.getCar());
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other})
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
            list.addAll(data);
            // 根据a-z进行排序源数据
            //Collections.sort(list, pinyinComparator);
            brandCarAdapter.notifyDataSetChanged();
            rvBrandCar.scrollToPosition(0);
        }
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void carListSuccess(List<HotSpecialCarBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                HotCarBean.DataBean dataBean = list.get(i);
                if (dataBean != null) {
                    dataBean.setCarList(null);
                }
            }
            HotCarBean.DataBean dataBean = list.get(brandposition);
            dataBean.setCarList(data);
            brandCarAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void carListFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }
}
