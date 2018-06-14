package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerBrandCarActivityCommponent;
import com.haotang.easyshare.di.module.activity.BrandCarActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.BrandCarBean;
import com.haotang.easyshare.mvp.presenter.BrandCarPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.BrandCarForFirstLetterAdapter;
import com.haotang.easyshare.mvp.view.iview.IBrandCarView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.mvp.view.widget.SideBar;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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
    private List<BrandCarBean.DataBean> list = new ArrayList<BrandCarBean.DataBean>();
    private BrandCarForFirstLetterAdapter brandCarForFirstLetterAdapter;

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
        activityListManager.addActivity(this);
        DaggerBrandCarActivityCommponent.builder().
                brandCarActivityModule(new BrandCarActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        sbBrandCarSidebar.bringToFront();
        sbBrandCarSidebar.setTextView(tvBrandCarHint);
        tvTitlebarTitle.setText("选择车型");
        rvBrandCar.setHasFixedSize(true);
        rvBrandCar.setLayoutManager(new LinearLayoutManager(this));
        brandCarForFirstLetterAdapter = new BrandCarForFirstLetterAdapter(R.layout.item_brand_car_firstletter
                , list);
        rvBrandCar.setAdapter(brandCarForFirstLetterAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        mPresenter.car();
    }

    @Override
    protected void initEvent() {
        sbBrandCarSidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = brandCarForFirstLetterAdapter.getPositionForSection(s.charAt(0));
                if (list != null && list.size() > 0 && position >= 0 && list.size() > position) {
                    rvBrandCar.scrollToPosition(position);
                }
            }
        });
    }

    @Subscribe
    public void getCar(BrandCarBean.DataBean.DatasetBean.CarsBean carsBean) {
        if (carsBean != null) {
            Intent intent = new Intent();
            intent.putExtra("carId", carsBean.getId());
            intent.putExtra("carName", carsBean.getCar());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public boolean isUseEventBus() {
        return true;
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
    public void carSuccess(List<BrandCarBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            list.clear();
            list.addAll(data);
            brandCarForFirstLetterAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void carFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "carFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this,code);
    }
}
