package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerHotPostFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.HotPostFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshEvent;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.presenter.HotPostFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.HotFragPointAdapter;
import com.haotang.easyshare.mvp.view.adapter.ViewPagerHotFragAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IHotPostFragmentView;
import com.haotang.easyshare.mvp.view.widget.CardTransformer;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
import com.ljy.devring.other.RingLog;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2019/1/9 17:53
 */
public class HotPostFragment extends BaseFragment<HotPostFragmentPresenter> implements OnBannerListener,
        IHotPostFragmentView {
    SwipeRefreshLayout srl_hotfragment;
    RecyclerView rvHotfragment;
    View vw_hotfragment_shadow;
    private List<HotPoint.DataBean> list = new ArrayList<HotPoint.DataBean>();
    private int mNextRequestPage = 1;
    private int pageSize;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();
    private int postFlag;
    private ViewPager vp_hotfrag_top;
    private HotFragPointAdapter hotFragPointAdapter;
    private ViewPagerHotFragAdapter viewPagerHotFragAdapter;

    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Subscribe
    public void refresh(RefreshEvent data) {
        if (data != null && data.getRefreshIndex() == RefreshEvent.SEND_POST) {
            showDialog();
            MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                    .addFormDataPart("category", "2").build();
            mPresenter.list(UrlConstants.getMapHeader(mActivity), body);
            mNextRequestPage = 1;
            setRequest();
            UmenUtil.UmengEventStatistics(getActivity(), UmenUtil.yxzx4);
        }
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.hotpostfragment;
    }

    @Override
    protected void initView() {
        DaggerHotPostFragmentCommponent.builder()
                .hotPostFragmentModule(new HotPostFragmentModule(this, mActivity))
                .build()
                .inject(this);
        View view = getmContentView();
        if (view != null) {
            srl_hotfragment = (SwipeRefreshLayout) view.findViewById(R.id.srl_hotfragment);
            rvHotfragment = (RecyclerView) view.findViewById(R.id.rv_hotfragment);
            vw_hotfragment_shadow = (View) view.findViewById(R.id.vw_hotfragment_shadow);
            vw_hotfragment_shadow.bringToFront();
            srl_hotfragment.setRefreshing(true);
            srl_hotfragment.setColorSchemeColors(Color.rgb(47, 223, 189));
            rvHotfragment.setHasFixedSize(true);
            rvHotfragment.setLayoutManager(new LinearLayoutManager(mActivity));
            hotFragPointAdapter = new HotFragPointAdapter(R.layout.item_hotfrag_point, list);
            View top = getLayoutInflater().inflate(R.layout.hotfrag_top_view, (ViewGroup) rvHotfragment.getParent(), false);
            vp_hotfrag_top = (ViewPager) top.findViewById(R.id.vp_hotfrag_top);
            viewPagerHotFragAdapter = new ViewPagerHotFragAdapter(mActivity, bannerList);
            vp_hotfrag_top.setAdapter(viewPagerHotFragAdapter);
            vp_hotfrag_top.setOffscreenPageLimit(2);//预加载2个
            vp_hotfrag_top.setPageMargin(-70);//设置viewpage之间的间距
            vp_hotfrag_top.setPageTransformer(true, new CardTransformer());
            hotFragPointAdapter.addHeaderView(top);
            rvHotfragment.setAdapter(hotFragPointAdapter);
            //添加自定义分割线
            rvHotfragment.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.VERTICAL, DensityUtil.dp2px(mActivity, 5),
                    ContextCompat.getColor(mActivity, R.color.af8f8f8)));
        }
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        postFlag = arguments.getInt("index", 0);
        showDialog();
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "2").build();
        mPresenter.list(UrlConstants.getMapHeader(mActivity), body);
        mNextRequestPage = 1;
        setRequest();
    }

    @Override
    protected void initEvent() {
        hotFragPointAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list.size() > 0 && list.size() > position) {
                    HotPoint.DataBean dataBean = list.get(position);
                    if (dataBean != null) {
                        PostBean.DataBean.ShareMap shareMap = dataBean.getShareMap();
                        if (shareMap != null) {
                            Intent intent = new Intent(mActivity, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL_KEY, shareMap.getUrl());
                            intent.putExtra("uuid", dataBean.getUuid());
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        hotFragPointAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
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
    }

    private void refresh() {
        hotFragPointAdapter.setEnableLoadMore(false);
        srl_hotfragment.setRefreshing(true);
        mNextRequestPage = 1;
        setRequest();
    }

    private void setRequest() {
        showDialog();
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE).addFormDataPart("page", String.valueOf(mNextRequestPage))
                .build();
        if (postFlag == 0) {//最新帖
            mPresenter.newest(UrlConstants.getMapHeader(mActivity), body);
        } else if (postFlag == 1) {//热门帖
            mPresenter.hot(UrlConstants.getMapHeader(mActivity), body);
        } else if (postFlag == 2) {//问题车
            mPresenter.problemCar(UrlConstants.getMapHeader(mActivity), body);
        }
    }

    private void loadMore() {
        setRequest();
    }

    @Override
    public void listSuccess(List<AdvertisementBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            bannerList.clear();
            bannerList.addAll(data);
            vp_hotfrag_top.setVisibility(View.VISIBLE);
            viewPagerHotFragAdapter.notifyDataSetChanged();
        } else {
            vp_hotfrag_top.setVisibility(View.GONE);
        }
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "listFail() code = " + code + "---msg = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void newestSuccess(List<HotPoint.DataBean> data) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            srl_hotfragment.setRefreshing(false);
            hotFragPointAdapter.setEnableLoadMore(true);
            list.clear();
        }
        hotFragPointAdapter.loadMoreComplete();
        if (data != null && data.size() > 0) {
            if (mNextRequestPage == 1) {
                pageSize = data.size();
            } else {
                if (data.size() < pageSize) {
                    hotFragPointAdapter.loadMoreEnd(false);
                }
            }
            list.addAll(data);
            mNextRequestPage++;
        } else {
            if (mNextRequestPage == 1) {
                hotFragPointAdapter.loadMoreEnd(true);
                String msg = "";
                if (postFlag == 0) {
                    msg = "暂无最新帖";
                } else if (postFlag == 1) {
                    msg = "暂无热门帖";
                } else if (postFlag == 2) {
                    msg = "暂无问题车帖子";
                }
                hotFragPointAdapter.setEmptyView(setEmptyViewBase(2, msg, R.mipmap.no_data, null));
            } else {
                hotFragPointAdapter.loadMoreEnd(false);
            }
        }
        hotFragPointAdapter.notifyDataSetChanged();
    }

    @Override
    public void newestFail(int code, String msg) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            hotFragPointAdapter.setEnableLoadMore(true);
            srl_hotfragment.setRefreshing(false);
        } else {
            hotFragPointAdapter.loadMoreFail();
        }
        hotFragPointAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "newestFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
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
    public void requestData() {

    }
}
