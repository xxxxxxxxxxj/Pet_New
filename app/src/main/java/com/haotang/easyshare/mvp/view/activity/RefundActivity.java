package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerRefundActivityCommponent;
import com.haotang.easyshare.di.module.activity.RefundActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.ReFundExplain;
import com.haotang.easyshare.mvp.model.entity.res.ReFundSubmit;
import com.haotang.easyshare.mvp.model.entity.res.ReFundTag;
import com.haotang.easyshare.mvp.presenter.RefundPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.RefundResonAdapter;
import com.haotang.easyshare.mvp.view.adapter.RefundSmAdapter;
import com.haotang.easyshare.mvp.view.adapter.RefundTopAdapter;
import com.haotang.easyshare.mvp.view.iview.IRefundView;
import com.haotang.easyshare.mvp.view.widget.AlertDialogNavAndPost;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 退款页
 */
public class RefundActivity extends BaseActivity<RefundPresenter> implements IRefundView {
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_refund_reason)
    RecyclerView rvRefundReason;
    @BindView(R.id.rv_refund_sm)
    RecyclerView rvRefundSm;
    @BindView(R.id.rv_refund_top)
    RecyclerView rv_refund_top;
    @BindView(R.id.btn_refund_submit)
    Button btnRefundSubmit;
    private List<ReFundTag.DataBean> resonList = new ArrayList<ReFundTag.DataBean>();
    private List<String> smList = new ArrayList<String>();
    private List<String> topList = new ArrayList<String>();
    private RefundResonAdapter refundResonAdapter;
    private RefundSmAdapter refundSmAdapter;
    private RefundTopAdapter refundTopAdapter;
    private String id;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_refund;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerRefundActivityCommponent.builder().
                refundActivityModule(new RefundActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("申请退款");

        rvRefundReason.setHasFixedSize(true);
        rvRefundReason.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                NoScollFullGridLayoutManager(rvRefundReason, this, 2, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvRefundReason.setLayoutManager(noScollFullGridLayoutManager);
        rvRefundReason.addItemDecoration(new GridSpacingItemDecoration(2,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing10),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing10),
                true));
        refundResonAdapter = new RefundResonAdapter(R.layout.item_refund_reson
                , resonList);
        rvRefundReason.setAdapter(refundResonAdapter);

        rvRefundSm.setHasFixedSize(true);
        rvRefundSm.setNestedScrollingEnabled(false);
        NoScollFullLinearLayoutManager noScollFullLinearLayoutManager = new NoScollFullLinearLayoutManager(this);
        noScollFullLinearLayoutManager.setScrollEnabled(false);
        rvRefundSm.setLayoutManager(noScollFullLinearLayoutManager);
        refundSmAdapter = new RefundSmAdapter(R.layout.item_refund_sm, smList);
        rvRefundSm.setAdapter(refundSmAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_white_5));
        rvRefundSm.addItemDecoration(divider);

        rv_refund_top.setHasFixedSize(true);
        rv_refund_top.setNestedScrollingEnabled(false);
        NoScollFullLinearLayoutManager noScollFullLinearLayoutManager1 = new NoScollFullLinearLayoutManager(this);
        noScollFullLinearLayoutManager1.setScrollEnabled(false);
        rv_refund_top.setLayoutManager(noScollFullLinearLayoutManager1);
        refundTopAdapter = new RefundTopAdapter(R.layout.item_refund_sm, topList);
        rv_refund_top.setAdapter(refundTopAdapter);
        //添加自定义分割线
        DividerItemDecoration divider1 = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider1.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_white_5));
        rv_refund_top.addItemDecoration(divider1);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("parentId", "1");
        RequestBody build = builder.build();
        mPresenter.list(build);
        mPresenter.explain();
    }

    @Override
    protected void initEvent() {
        refundResonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (resonList.size() > 0 && resonList.size() > position) {
                    ReFundTag.DataBean dataBean = resonList.get(position);
                    if (dataBean != null) {
                        dataBean.setSelect(!dataBean.isSelect());
                    }
                    refundResonAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.iv_titlebar_back, R.id.btn_refund_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.btn_refund_submit:
                id = "";
                if (resonList.size() > 0) {
                    for (int i = 0; i < resonList.size(); i++) {
                        ReFundTag.DataBean dataBean = resonList.get(i);
                        if (dataBean != null && dataBean.isSelect()) {
                            id = id + dataBean.getId() + ",";
                        }
                    }
                }
                if (StringUtil.isNotEmpty(id)) {
                    new AlertDialogNavAndPost(RefundActivity.this).builder().setTitle("")
                            .setMsg("您确认要退款吗？")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showDialog();
                                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                                    builder.addFormDataPart("reason", id.substring(0, id.length() - 1));
                                    RequestBody build = builder.build();
                                    mPresenter.refund(build);
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                } else {
                    RingToast.show("请先选择退款原因");
                }
                break;
        }
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
    }

    @Override
    public void listSuccess(List<ReFundTag.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            resonList.clear();
            resonList.addAll(data);
            refundResonAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void explainFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "explainFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void explainSuccess(ReFundExplain.DataBean data) {
        disMissDialog();
        if (data != null) {
            List<String> bottom = data.getBottom();
            List<String> top = data.getTop();
            if (bottom != null && bottom.size() > 0) {
                smList.clear();
                smList.addAll(bottom);
                refundSmAdapter.notifyDataSetChanged();
            }
            if (top != null && top.size() > 0) {
                topList.clear();
                topList.addAll(top);
                refundTopAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void refundSuccess(ReFundSubmit.DataBean data) {
        disMissDialog();
        if (data != null) {
            String desc = data.getDesc();
            startActivity(new Intent(RefundActivity.this, RefundResultActivity.class).putExtra("desc", desc));
        }
    }

    @Override
    public void refundFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "explainFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }
}
