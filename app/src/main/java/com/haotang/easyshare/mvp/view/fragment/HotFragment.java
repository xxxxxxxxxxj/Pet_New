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
import com.haotang.easyshare.mvp.model.entity.res.HotFragPoint;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.presenter.HotFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.SendPostActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.HotFragPointAdapter;
import com.haotang.easyshare.mvp.view.adapter.ViewPagerSelectCarAdapter;
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
    private List<HotFragPoint> list = new ArrayList<HotFragPoint>();
    private int mNextRequestPage = 1;
    private int pageSize;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();
    private List<String> imgList = new ArrayList<String>();
    private int postFlag;
    private ViewPager vp_hotfrag_top;
    private HotFragPointAdapter hotFragPointAdapter;

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
        imgList.add("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg");
        imgList.add("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg");
        imgList.add("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg");
        imgList.add("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg");
        imgList.add("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg");
        imgList.add("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg");
        imgList.add("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg");
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 2, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 1, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 2, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 1, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 2, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 1, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 2, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 1, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 2, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 1, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 2, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        list.add(new HotFragPoint("最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc最美大众回归，两个车评美女试驾大众cc", 1, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", imgList, "http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", "剑冲云霄", "59分钟前", "29评论"));
        hotFragPointAdapter = new HotFragPointAdapter(R.layout.item_hotfrag_point, list);
        View top = getLayoutInflater().inflate(R.layout.hotfrag_top_view, (ViewGroup) rvHotfragment.getParent(), false);
        vp_hotfrag_top = (ViewPager) top.findViewById(R.id.vp_hotfrag_top);

        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        bannerList.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549041398388939.jpg", 1, "测试", "测试"));
        vp_hotfrag_top.setAdapter(new ViewPagerSelectCarAdapter(mActivity, bannerList));
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

    @Subscribe
    public void refresh(RefreshEvent data) {
        if (data != null && data.getRefreshIndex() == RefreshEvent.SEND_POST) {
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
            refresh();
        }
    }

    @Override
    protected void initEvent() {
        hotFragPointAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list.size() > 0 && list.size() > position) {
                    HotFragPoint hotFragPoint = list.get(position);
                    if (hotFragPoint != null) {
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
        showDialog();
        srl_hotfragment.setRefreshing(true);
        mNextRequestPage = 1;
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "2").build();
        mPresenter.list(UrlConstants.getMapHeader(mActivity), body);
        mPresenter.hot(UrlConstants.getMapHeader(mActivity));

        MultipartBody body1 = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE).addFormDataPart("page", String.valueOf(mNextRequestPage))
                .build();
        mPresenter.newest(UrlConstants.getMapHeader(mActivity), body1);
    }

    private void loadMore() {
        MultipartBody body1 = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE).addFormDataPart("page", String.valueOf(mNextRequestPage))
                .build();
        mPresenter.newest(UrlConstants.getMapHeader(mActivity), body1);
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
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
    public void newestSuccess(List<HotPoint.DataBean> data) {
        disMissDialog();
    }

    @Override
    public void newestFail(int code, String msg) {
        disMissDialog();
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
                postFlag = 0;
                setPost();
                break;
            case R.id.rl_hotfragment_rmt:
                postFlag = 1;
                setPost();
                break;
            case R.id.rl_hotfragment_wtc:
                postFlag = 2;
                setPost();
                break;
            case R.id.tv_hotfragment_serch:
                break;
        }
    }

    private void setPost() {
        if (postFlag == 0) {
            tvHotfragmentZxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tvHotfragmentRmt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvHotfragmentWtc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            ivHotfragmentZxt.setVisibility(View.VISIBLE);
            ivHotfragmentRmt.setVisibility(View.INVISIBLE);
            ivHotfragmentWtc.setVisibility(View.INVISIBLE);
        } else if (postFlag == 1) {
            tvHotfragmentZxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvHotfragmentRmt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tvHotfragmentWtc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            ivHotfragmentZxt.setVisibility(View.INVISIBLE);
            ivHotfragmentRmt.setVisibility(View.VISIBLE);
            ivHotfragmentWtc.setVisibility(View.INVISIBLE);
        } else if (postFlag == 2) {
            tvHotfragmentZxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvHotfragmentRmt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvHotfragmentWtc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            ivHotfragmentZxt.setVisibility(View.INVISIBLE);
            ivHotfragmentRmt.setVisibility(View.INVISIBLE);
            ivHotfragmentWtc.setVisibility(View.VISIBLE);
        }
    }
}
