package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerSelectCarFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.SelectCarFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.CarType;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.entity.res.ImageTabEntity;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.presenter.SelectCarFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AllBrandsActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.MyFragChargePagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.ISelectCarFragmentView;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
import com.ljy.devring.other.RingLog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:选车主界面</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:36
 */
public class SelectCarFragment extends BaseFragment<SelectCarFragmentPresenter> implements ISelectCarFragmentView, OnBannerListener {
    protected final static String TAG = SelectCarFragment.class.getSimpleName();
    @BindView(R.id.ctl_selectfrag)
    CommonTabLayout ctl_selectfrag;
    @BindView(R.id.banner_selectcar)
    Banner bannerSelectcar;
    @BindView(R.id.vp_selectcar)
    ViewPager vpSelectcar;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();
    private int carFlag;
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private MyFragChargePagerAdapter myFragChargePagerAdapter;
    private List<CarType.DataBean> list = new ArrayList<CarType.DataBean>();
    private String[] mTitles = {"新车", "二手车"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_xc_normal, R.mipmap.tab_esc_normal};
    private int[] mIconSelectIds = {
            R.mipmap.tab_xc_passed, R.mipmap.tab_esc_passed};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.selectfragment;
    }

    @Override
    protected void initView() {
        DaggerSelectCarFragmentCommponent.builder()
                .selectCarFragmentModule(new SelectCarFragmentModule(this, mActivity))
                .build()
                .inject(this);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new ImageTabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        ctl_selectfrag.setTabData(mTabEntities);
        ctl_selectfrag.setCurrentTab(carFlag);
        myFragChargePagerAdapter = new MyFragChargePagerAdapter(mActivity.getSupportFragmentManager(), mFragments);
        vpSelectcar.setAdapter(myFragChargePagerAdapter);
    }

    private void setRequest() {
        showDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (carFlag == 0) {//新车
            builder.addFormDataPart("source", "1");
        } else if (carFlag == 1) {//二手车
            builder.addFormDataPart("source", "2");
        }
        RequestBody body = builder.build();
        mPresenter.carType(UrlConstants.getMapHeader(mActivity), body);
    }

    private void setBanner() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < bannerList.size(); i++) {
            list.add(bannerList.get(i).getImg());
        }
        bannerSelectcar.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @Override
    protected void initData() {
        refresh();
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void RefreshFragment(RefreshFragmentEvent refreshFragmentEvent) {
        if (refreshFragmentEvent != null && refreshFragmentEvent.getRefreshIndex() ==
                RefreshFragmentEvent.REFRESH_SELECTCARFRAGMET && mPresenter != null) {
            RingLog.e("REFRESH_SELECTCARFRAGMET");
            refresh();
            UmenUtil.UmengEventStatistics(getActivity(), UmenUtil.yxzx8);
        }
    }

    private void refresh() {
        showDialog();
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "3").build();
        mPresenter.list(UrlConstants.getMapHeader(mActivity), body);
        carFlag = 0;
        setRequest();
    }

    @Override
    public void specialSuccess(List<HotSpecialCarBean.DataBean> data) {
        disMissDialog();
    }

    @Override
    public void specialFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "specialFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void listSuccess(List<AdvertisementBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            bannerList.clear();
            bannerList.addAll(data);
            bannerSelectcar.setVisibility(View.VISIBLE);
            setBanner();
        } else {
            bannerSelectcar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void carTypeSuccess(List<CarType.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            list.clear();
            mFragments.clear();
            list.addAll(data);
            vpSelectcar.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));//设置viewpage之间的间距
            for (int i = 0; i < data.size(); i++) {
                CarType.DataBean dataBean = data.get(i);
                HotCarFragment hotCarFragment = new HotCarFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("selectFragCarBean", dataBean);
                hotCarFragment.setArguments(bundle);
                mFragments.add(hotCarFragment);
            }
            myFragChargePagerAdapter.notifyDataSetChanged();
            vpSelectcar.setOffscreenPageLimit(data.size());//预加载
        }
    }

    @Override
    public void carTypeFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "hotFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "listFail() code = " + code + "---msg = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void hotSuccess(List<HotCarBean.DataBean> data) {
        disMissDialog();
    }

    @Override
    public void hotFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "hotFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    protected void initEvent() {
        ctl_selectfrag.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                RingLog.e(TAG, "onTabSelect position = " + position);
                carFlag = position;
                setRequest();
            }

            @Override
            public void onTabReselect(int position) {
                RingLog.e(TAG, "onTabReselect position = " + position);
            }
        });
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.tv_selectcar_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_selectcar_more:
                startActivity(new Intent(mActivity, AllBrandsActivity.class).putExtra("flag", 1));
                break;
        }
    }

    @Override
    public void OnBannerClick(int position) {
        RingLog.e(TAG, "position:" + position);
        if (bannerList != null && bannerList.size() > 0 && bannerList.size() > position) {
            AdvertisementBean.DataBean dataBean = bannerList.get(position);
            if (dataBean != null) {
                if (dataBean.getDisplay() == 1) {//原生

                } else if (dataBean.getDisplay() == 2) {//H5
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(WebViewActivity.URL_KEY, dataBean.getDestination()));
                }
            }
        }
    }
}