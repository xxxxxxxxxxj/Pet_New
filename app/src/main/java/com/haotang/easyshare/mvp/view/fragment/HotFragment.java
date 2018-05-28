package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerHotFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.HotFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.presenter.HotFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AllBrandsActivity;
import com.haotang.easyshare.mvp.view.activity.BrandAreaActivity;
import com.haotang.easyshare.mvp.view.activity.MyPostActivity;
import com.haotang.easyshare.mvp.view.activity.PostListActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.HotPointAdapter;
import com.haotang.easyshare.mvp.view.adapter.HotPointCarAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IHotFragmentView;
import com.haotang.easyshare.mvp.view.viewholder.HotFragmenHeader;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.other.RingLog;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import okhttp3.MultipartBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 20:59
 */
public class HotFragment extends BaseFragment<HotFragmentPresenter> implements OnBannerListener, View.OnClickListener, IHotFragmentView {
    protected final static String TAG = HotFragment.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.srl_hotfragment)
    SwipeRefreshLayout srl_hotfragment;
    @BindView(R.id.rv_hotfragment)
    RecyclerView rvHotfragment;
    private List<HotPoint.DataBean> list = new ArrayList<HotPoint.DataBean>();
    private List<HotCarBean.DataBean> carList = new ArrayList<HotCarBean.DataBean>();
    private HotPointAdapter hotPointAdapter;
    private HotFragmenHeader hotFragmenHeader;
    private HotPointCarAdapter hotPointCarAdapter;
    private int mNextRequestPage = 1;
    private int pageSize;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.hotfragment;
    }

    @Override
    protected void initView() {
        DaggerHotFragmentCommponent.builder()
                .hotFragmentModule(new HotFragmentModule(this, mActivity))
                .build()
                .inject(this);
        srl_hotfragment.setRefreshing(true);
        srl_hotfragment.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvHotfragment.setHasFixedSize(true);
        rvHotfragment.setLayoutManager(new LinearLayoutManager(mActivity));
        hotPointAdapter = new HotPointAdapter(R.layout.item_hot_point, list);
        View top = getLayoutInflater().inflate(R.layout.hotfrag_top_view, (ViewGroup) rvHotfragment.getParent(), false);
        hotFragmenHeader = new HotFragmenHeader(top);
        hotPointAdapter.addHeaderView(top);
        rvHotfragment.setAdapter(hotPointAdapter);
        //添加自定义分割线
        rvHotfragment.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.VERTICAL, DensityUtil.dp2px(mActivity, 15),
                ContextCompat.getColor(mActivity, R.color.af8f8f8)));
        hotFragmenHeader.getRvTopHotfrag().setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotFragmenHeader.getRvTopHotfrag().setLayoutManager(linearLayoutManager);
        hotPointCarAdapter = new HotPointCarAdapter(R.layout.item_hotfrag_top_car, carList);
        hotFragmenHeader.getRvTopHotfrag().setAdapter(hotPointCarAdapter);
        //添加自定义分割线
        hotFragmenHeader.getRvTopHotfrag().addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.HORIZONTAL, DensityUtil.dp2px(mActivity, 15),
                ContextCompat.getColor(mActivity, R.color.af8f8f8)));
        hotFragmenHeader.getTv_banner_top_hotfrag().bringToFront();
    }

    private void setBanner(List<AdvertisementBean.DataBean> data) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getImg());
        }
        hotFragmenHeader.getBannerTopHotfrag().setImages(list)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
        if (bannerList != null && bannerList.size() > 0) {
            AdvertisementBean.DataBean dataBean = bannerList.get(0);
            if (dataBean != null) {
                StringUtil.setText(hotFragmenHeader.getTv_banner_top_hotfrag(), dataBean.getTitle(), "", View.VISIBLE, View.VISIBLE);
            }
        }
    }

    @Override
    protected void initData() {
        refresh();
    }

    @Override
    public void OnBannerClick(int position) {
        RingLog.e(TAG, "position:" + position);
        if (bannerList != null && bannerList.size() > 0 && bannerList.size() > position) {
            AdvertisementBean.DataBean dataBean = bannerList.get(position);
            if (dataBean != null) {
                if (dataBean.getDisplay() == 1) {//原生

                } else if (dataBean.getDisplay() == 2) {//H5
                    mActivity.startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(WebViewActivity.URL_KEY, dataBean.getDestination()));
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if (hotFragmenHeader != null) {
            hotFragmenHeader.getBannerTopHotfrag().startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (hotFragmenHeader != null) {
            hotFragmenHeader.getBannerTopHotfrag().stopAutoPlay();
        }
    }

    @Override
    public void requestData() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void RefreshFragment(RefreshFragmentEvent refreshFragmentEvent) {
        if (refreshFragmentEvent != null && refreshFragmentEvent.getRefreshIndex() == RefreshFragmentEvent.REFRESH_HOTFRAGMET) {
            RingLog.e("REFRESH_HOTFRAGMET");

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_top_hotfrag_htpd:
                startActivity(new Intent(mActivity, PostListActivity.class));
                break;
            case R.id.rl_top_hotfrag_rmpp:
                startActivity(new Intent(mActivity, AllBrandsActivity.class));
                break;
        }
    }

    @Override
    protected void initEvent() {
        if (hotFragmenHeader != null) {
            hotFragmenHeader.getRlTopHotfragHtpd().setOnClickListener(this);
            hotFragmenHeader.getRlTopHotfragRmpp().setOnClickListener(this);
        }
        hotPointAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list.size() > 0 && list.size() > position) {
                    HotPoint.DataBean dataBean = list.get(position);
                    if (dataBean != null) {
                        PostBean.DataBean.ShareMap shareMap = dataBean.getShareMap();
                        if (shareMap != null) {
                            startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(WebViewActivity.URL_KEY, shareMap.getUrl()));
                        }
                    }
                }
            }
        });
        hotPointCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (carList != null && carList.size() > 0 && carList.size() > position) {
                    HotCarBean.DataBean dataBean = carList.get(position);
                    if (dataBean != null) {
                        Intent intent = new Intent(mActivity, BrandAreaActivity.class);
                        intent.putExtra("brandId", dataBean.getId());
                        intent.putExtra("brand", dataBean.getBrand());
                        startActivity(intent);
                    }
                }
            }
        });
        hotPointAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srl_hotfragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        hotFragmenHeader.getBannerTopHotfrag().setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (bannerList != null && bannerList.size() > 0 && bannerList.size() > position) {
                    AdvertisementBean.DataBean dataBean = bannerList.get(position);
                    if (dataBean != null) {
                        StringUtil.setText(hotFragmenHeader.getTv_banner_top_hotfrag(), dataBean.getTitle(), "", View.VISIBLE, View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void refresh() {
        hotPointCarAdapter.setEnableLoadMore(false);
        srl_hotfragment.setRefreshing(true);
        mNextRequestPage = 1;
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "2").build();
        mPresenter.list(body);
        mPresenter.hot();

        MultipartBody body1 = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE).addFormDataPart("page", String.valueOf(mNextRequestPage))
                .build();
        mPresenter.newest(body1);
    }

    private void loadMore() {
        MultipartBody body1 = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE).addFormDataPart("page", String.valueOf(mNextRequestPage))
                .build();
        mPresenter.newest(body1);
    }

    @Override
    public void listFail(int code, String msg) {
        hotFragmenHeader.getRl_banner_top_hotfrag().setVisibility(View.GONE);
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void listSuccess(List<AdvertisementBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            bannerList.clear();
            bannerList.addAll(data);
            hotFragmenHeader.getRl_banner_top_hotfrag().setVisibility(View.VISIBLE);
            setBanner(data);
        } else {
            hotFragmenHeader.getRl_banner_top_hotfrag().setVisibility(View.GONE);
        }
    }

    @Override
    public void hotSuccess(List<HotCarBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            carList.clear();
            carList.addAll(data);
            hotPointCarAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void hotFail(int code, String msg) {
        RingLog.e(TAG, "hotFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void newestSuccess(List<HotPoint.DataBean> data) {
        if (mNextRequestPage == 1) {
            srl_hotfragment.setRefreshing(false);
            hotPointAdapter.setEnableLoadMore(true);
            list.clear();
        }
        hotPointAdapter.loadMoreComplete();
        if (data != null && data.size() > 0) {
            if (mNextRequestPage == 1) {
                pageSize = data.size();
            } else {
                if (data.size() < pageSize) {
                    hotPointAdapter.loadMoreEnd(false);
                }
            }
            list.addAll(data);
            mNextRequestPage++;
        } else {
            if (mNextRequestPage == 1) {
                hotPointAdapter.loadMoreEnd(true);
            } else {
                hotPointAdapter.loadMoreEnd(false);
            }
        }
        hotPointAdapter.notifyDataSetChanged();
    }

    @Override
    public void newestFail(int code, String msg) {
        if (mNextRequestPage == 1) {
            hotPointAdapter.setEnableLoadMore(true);
            srl_hotfragment.setRefreshing(false);
        } else {
            hotPointAdapter.loadMoreFail();
        }
        RingLog.e(TAG, "newestFail() status = " + code + "---desc = " + msg);
    }
}
