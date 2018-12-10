package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerSelectCarFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.SelectCarFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.presenter.SelectCarFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AllBrandsActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.ViewPagerSelectCarAdapter;
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
    @BindView(R.id.tv_selectcar_xinche)
    TextView tvSelectcarXinche;
    @BindView(R.id.iv_selectcar_xinche)
    ImageView ivSelectcarXinche;
    @BindView(R.id.tv_selectcar_esc)
    TextView tvSelectcarEsc;
    @BindView(R.id.iv_selectcar_esc)
    ImageView ivSelectcarEsc;
    @BindView(R.id.banner_selectcar)
    Banner bannerSelectcar;
    @BindView(R.id.vp_selectcar)
    ViewPager vpSelectcar;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();
    private int carFlag;

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

        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        if (bannerList != null && bannerList.size() > 0) {
            bannerSelectcar.setVisibility(View.VISIBLE);
            setBanner();
        } else {
            bannerSelectcar.setVisibility(View.INVISIBLE);
        }

        vpSelectcar.setAdapter(new ViewPagerSelectCarAdapter(mActivity, bannerList));
        vpSelectcar.setOffscreenPageLimit(2);//预加载2个
        vpSelectcar.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));//设置viewpage之间的间距
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
        mPresenter.hot(UrlConstants.getMapHeader(mActivity));
        mPresenter.special(UrlConstants.getMapHeader(mActivity));
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "3").build();
        mPresenter.list(UrlConstants.getMapHeader(mActivity), body);
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
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "specialFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void listSuccess(List<AdvertisementBean.DataBean> data) {
        disMissDialog();
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
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.tv_selectcar_more, R.id.rl_selectcar_xinche, R.id.rl_selectcar_esc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_selectcar_more:
                startActivity(new Intent(mActivity, AllBrandsActivity.class));
                break;
            case R.id.rl_selectcar_xinche:
                carFlag = 0;
                setCar();
                break;
            case R.id.rl_selectcar_esc:
                carFlag = 1;
                setCar();
                break;
        }
    }

    private void setCar() {
        if (carFlag == 0) {
            tvSelectcarXinche.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            tvSelectcarEsc.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            ivSelectcarXinche.setVisibility(View.VISIBLE);
            ivSelectcarEsc.setVisibility(View.INVISIBLE);
        } else if (carFlag == 1) {
            tvSelectcarXinche.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            tvSelectcarEsc.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            ivSelectcarXinche.setVisibility(View.INVISIBLE);
            ivSelectcarEsc.setVisibility(View.VISIBLE);
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