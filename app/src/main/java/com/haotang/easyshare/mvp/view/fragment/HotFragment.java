package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerHotFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.HotFragmentModule;
import com.haotang.easyshare.mvp.model.entity.res.CarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.presenter.HotFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AllBrandsActivity;
import com.haotang.easyshare.mvp.view.activity.BrandAreaActivity;
import com.haotang.easyshare.mvp.view.activity.PostListActivity;
import com.haotang.easyshare.mvp.view.adapter.HotPointAdapter;
import com.haotang.easyshare.mvp.view.adapter.HotPointCarAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IHotFragmentView;
import com.haotang.easyshare.mvp.view.viewholder.HotFragmenHeader;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.ljy.devring.other.RingLog;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

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
    @BindView(R.id.rv_hotfragment)
    RecyclerView rvHotfragment;
    private List<HotPoint> list = new ArrayList<HotPoint>();
    private List<CarBean> carList = new ArrayList<CarBean>();
    private HotPointAdapter hotPointAdapter;
    private HotFragmenHeader hotFragmenHeader;
    private HotPointCarAdapter hotPointCarAdapter;

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
        for (int i = 0; i < 20; i++) {
            list.add(new HotPoint("结婚三周年送给媳妇的小电电，大方的么么哒等哈打了客服结婚三周年送给媳妇的小电电，" +
                    "大方的么么哒等哈打了客服结婚三周年送给媳妇的小电电，大方的么么哒等哈打了客服",
                    "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433",
                    "地方大V", "25分钟前", "28888阅读", "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/sh" +
                    "op/imgs/shopyyc.png?v=433"));
        }
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
        setBanner();

        for (int i = 0; i < 20; i++) {
            carList.add(new CarBean("http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433", "奔驰"));
        }
        hotFragmenHeader.getRvTopHotfrag().setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotFragmenHeader.getRvTopHotfrag().setLayoutManager(linearLayoutManager);
        hotPointCarAdapter = new HotPointCarAdapter(R.layout.item_hotfrag_top_car, carList);
        hotFragmenHeader.getRvTopHotfrag().setAdapter(hotPointCarAdapter);
        //添加自定义分割线
        hotFragmenHeader.getRvTopHotfrag().addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.HORIZONTAL, DensityUtil.dp2px(mActivity, 15),
                ContextCompat.getColor(mActivity, R.color.af8f8f8)));
    }

    private void setBanner() {
        //本地图片数据（资源文件）
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.b1);
        list.add(R.mipmap.b2);
        list.add(R.mipmap.b3);
        hotFragmenHeader.getBannerTopHotfrag().setImages(list)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void OnBannerClick(int position) {
        RingLog.e(TAG, "position:" + position);
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

            }
        });
        hotPointCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity, BrandAreaActivity.class));
            }
        });
    }
}
