package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerHotFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.HotFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshEvent;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.presenter.HotFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.SendPostActivity;
import com.haotang.easyshare.mvp.view.activity.SerchPostActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.HotFragPointAdapter;
import com.haotang.easyshare.mvp.view.adapter.ViewPagerHotFragAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IHotFragmentView;
import com.haotang.easyshare.mvp.view.widget.CardTransformer;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
import com.ljy.devring.other.RingLog;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 20:59
 */
public class HotFragment extends BaseFragment<HotFragmentPresenter> implements OnBannerListener,
        IHotFragmentView {
    protected final static String TAG = HotFragment.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.srl_hotfragment)
    SwipeRefreshLayout srl_hotfragment;
    @BindView(R.id.rv_hotfragment)
    RecyclerView rvHotfragment;
    @BindView(R.id.tv_hotfragment_post)
    TextView tvHotfragmentPost;
    @BindView(R.id.tv_hotfragment_zxt)
    TextView tvHotfragmentZxt;
    @BindView(R.id.iv_hotfragment_zxt)
    ImageView ivHotfragmentZxt;
    @BindView(R.id.tv_hotfragment_rmt)
    TextView tvHotfragmentRmt;
    @BindView(R.id.iv_hotfragment_rmt)
    ImageView ivHotfragmentRmt;
    @BindView(R.id.tv_hotfragment_wtc)
    TextView tvHotfragmentWtc;
    @BindView(R.id.iv_hotfragment_wtc)
    ImageView ivHotfragmentWtc;
    @BindView(R.id.tv_hotfragment_serch)
    TextView tvHotfragmentSerch;
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
        rvHotfragment.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.VERTICAL, DensityUtil.dp2px(mActivity, 15),
                ContextCompat.getColor(mActivity, R.color.af8f8f8)));
    }

    @Override
    protected void initData() {
        showDialog();
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "2").build();
        mPresenter.list(UrlConstants.getMapHeader(mActivity), body);
        setPost(0);
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

    @Subscribe
    public void refresh(RefreshEvent data) {
        if (data != null && data.getRefreshIndex() == RefreshEvent.SEND_POST) {
            MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                    .addFormDataPart("category", "2").build();
            mPresenter.list(UrlConstants.getMapHeader(mActivity), body);
            refresh();
            UmenUtil.UmengEventStatistics(getActivity(), UmenUtil.yxzx4);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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
        if (refreshFragmentEvent != null && refreshFragmentEvent.getRefreshIndex() ==
                RefreshFragmentEvent.REFRESH_HOTFRAGMET && mPresenter != null) {
            RingLog.e("REFRESH_HOTFRAGMET");
            MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                    .addFormDataPart("category", "2").build();
            mPresenter.list(UrlConstants.getMapHeader(mActivity), body);
            refresh();
        }
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
            vp_hotfrag_top.setVisibility(View.INVISIBLE);
        }
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

    @OnClick({R.id.tv_hotfragment_post, R.id.rl_hotfragment_zxt, R.id.rl_hotfragment_rmt, R.id.rl_hotfragment_wtc, R.id.tv_hotfragment_serch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hotfragment_post:
                startActivity(new Intent(mActivity, SendPostActivity.class));
                break;
            case R.id.rl_hotfragment_zxt:
                setPost(0);
                break;
            case R.id.rl_hotfragment_rmt:
                setPost(1);
                break;
            case R.id.rl_hotfragment_wtc:
                setPost(2);
                break;
            case R.id.tv_hotfragment_serch:
                startActivity(new Intent(mActivity, SerchPostActivity.class));
                break;
        }
    }

    private void setPost(int flag) {
        if (flag == 0) {
            postFlag = 0;
            tvHotfragmentZxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tvHotfragmentRmt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvHotfragmentWtc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            ivHotfragmentZxt.setVisibility(View.VISIBLE);
            ivHotfragmentRmt.setVisibility(View.INVISIBLE);
            ivHotfragmentWtc.setVisibility(View.INVISIBLE);
        } else if (flag == 1) {
            postFlag = 1;
            tvHotfragmentZxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvHotfragmentRmt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tvHotfragmentWtc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            ivHotfragmentZxt.setVisibility(View.INVISIBLE);
            ivHotfragmentRmt.setVisibility(View.VISIBLE);
            ivHotfragmentWtc.setVisibility(View.INVISIBLE);
        } else if (flag == 2) {
            postFlag = 2;
            tvHotfragmentZxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvHotfragmentRmt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvHotfragmentWtc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            ivHotfragmentZxt.setVisibility(View.INVISIBLE);
            ivHotfragmentRmt.setVisibility(View.INVISIBLE);
            ivHotfragmentWtc.setVisibility(View.VISIBLE);
        }
        mNextRequestPage = 1;
        setRequest();
    }
}
