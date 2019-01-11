package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerSerchPostActivityCommponent;
import com.haotang.easyshare.di.module.activity.SerchPostActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.model.entity.res.SerchKeysBean;
import com.haotang.easyshare.mvp.presenter.SerchPostPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.HotFragPointAdapter;
import com.haotang.easyshare.mvp.view.iview.ISerchPostView;
import com.haotang.easyshare.mvp.view.widget.ClearEditText;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 搜索文章界面
 */
public class SerchPostActivity extends BaseActivity<SerchPostPresenter> implements ISerchPostView {

    @BindView(R.id.cet_serch_post)
    ClearEditText cetSerchPost;
    @BindView(R.id.tfl_serch_post_rmss)
    TagFlowLayout tflSerchPostRmss;
    @BindView(R.id.ll_serch_post_rmss)
    LinearLayout llSerchPostRmss;
    @BindView(R.id.rv_serch_post)
    RecyclerView rvSerchPost;
    @BindView(R.id.srl_serch_post)
    SwipeRefreshLayout srlSerchPost;
    private List<HotPoint.DataBean> list = new ArrayList<HotPoint.DataBean>();
    private List<String> keyList = new ArrayList<String>();
    private int mNextRequestPage = 1;
    private int pageSize;
    private HotFragPointAdapter hotFragPointAdapter;
    private java.lang.String serchStr;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_serch_post;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerSerchPostActivityCommponent.builder().
                serchPostActivityModule(new SerchPostActivityModule(this, this)).build().inject(this);
        srlSerchPost.setRefreshing(true);
        srlSerchPost.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvSerchPost.setHasFixedSize(true);
        rvSerchPost.setLayoutManager(new LinearLayoutManager(this));
        hotFragPointAdapter = new HotFragPointAdapter(R.layout.item_hotfrag_point, list);
        rvSerchPost.setAdapter(hotFragPointAdapter);
        //添加自定义分割线
        rvSerchPost.addItemDecoration(new DividerLinearItemDecoration(this, LinearLayoutManager.VERTICAL, DensityUtil.dp2px(this, 5),
                ContextCompat.getColor(this, R.color.af8f8f8)));
    }

    private void loadMore() {
        setRequest();
    }

    private void setRequest() {
        showDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("page", String.valueOf(mNextRequestPage));
        builder.addFormDataPart("title", serchStr);
        RequestBody build = builder.build();
        mPresenter.list(UrlConstants.getMapHeader(this), build);
    }

    private void refresh() {
        hotFragPointAdapter.setEnableLoadMore(false);
        srlSerchPost.setRefreshing(true);
        mNextRequestPage = 1;
        setRequest();
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setLayout(1);
        showDialog();
        mPresenter.keys(UrlConstants.getMapHeader(this));
    }

    @Override
    protected void initEvent() {
        cetSerchPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                RingLog.e("s = " + s.toString());
                if (StringUtil.isEmpty(StringUtil.checkEditText(cetSerchPost))) {
                    RingLog.e("清空s = " + s.toString());
                    setLayout(1);
                }
            }
        });
        hotFragPointAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list.size() > 0 && list.size() > position) {
                    HotPoint.DataBean dataBean = list.get(position);
                    if (dataBean != null) {
                        PostBean.DataBean.ShareMap shareMap = dataBean.getShareMap();
                        if (shareMap != null) {
                            Intent intent = new Intent(SerchPostActivity.this, WebViewActivity.class);
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
        srlSerchPost.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
        SystemUtil.goneJP(this);
    }

    private void setLayout(int flag) {
        if (flag == 1) {
            llSerchPostRmss.setVisibility(View.VISIBLE);
            srlSerchPost.setVisibility(View.GONE);
        } else if (flag == 2) {
            llSerchPostRmss.setVisibility(View.GONE);
            srlSerchPost.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                SystemUtil.goneJP(this);
                finish();
                break;
            case R.id.tv_titlebar_other:
                if (StringUtil.isEmpty(StringUtil.checkEditText(cetSerchPost))) {
                    RingToast.show("请输入搜索内容");
                    cetSerchPost.setShakeAnimation();
                } else {
                    setLayout(2);
                    serchStr = cetSerchPost.getText().toString().trim();
                    refresh();
                }
                break;
        }
    }

    @Override
    public void keysSuccess(List<SerchKeysBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            tflSerchPostRmss.setVisibility(View.VISIBLE);
            keyList.clear();
            for (int i = 0; i < data.size(); i++) {
                keyList.add(data.get(i).getKey());
            }
            tflSerchPostRmss.setAdapter(new TagAdapter<String>(keyList) {
                @Override
                public View getView(FlowLayout parent, int position, final String s) {
                    View view = (View) View.inflate(SerchPostActivity.this, R.layout.item_serchpost_bq,
                            null);
                    TextView tv_item_serchpost_bq = (TextView) view.findViewById(R.id.tv_item_serchpost_bq);
                    tv_item_serchpost_bq.setText(s);
                    tv_item_serchpost_bq.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cetSerchPost.setText(s);
                            setLayout(2);
                            serchStr = s;
                            refresh();
                        }
                    });
                    return view;
                }
            });
        }
    }

    @Override
    public void keysFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "keysFail() code = " + code + "---msg = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void listSuccess(List<HotPoint.DataBean> data) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            srlSerchPost.setRefreshing(false);
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
                hotFragPointAdapter.setEmptyView(setEmptyViewBase(2, "当前搜索无结果", R.mipmap.no_data, null));
            } else {
                hotFragPointAdapter.loadMoreEnd(false);
            }
        }
        hotFragPointAdapter.notifyDataSetChanged();
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            hotFragPointAdapter.setEnableLoadMore(true);
            srlSerchPost.setRefreshing(false);
        } else {
            hotFragPointAdapter.loadMoreFail();
        }
        hotFragPointAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }
}
