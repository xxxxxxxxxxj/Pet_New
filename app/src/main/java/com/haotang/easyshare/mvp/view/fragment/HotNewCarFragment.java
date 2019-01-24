package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CarType;
import com.haotang.easyshare.mvp.view.activity.CarDetailActivity;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.util.ComputeUtil;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.ScreenUtil;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.other.RingLog;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/15 17:09
 */
public class HotNewCarFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_hotcarfrag_ck;
    private ImageView iv_hotcarfrag_bg;
    private TextView tv_hotcarfrag_carname;
    private TextView tv_hotcarfrag_cardesc;
    private int id;

    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.hotcarfragment;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initView() {
        View view = getmContentView();
        if (view != null) {
            ll_hotcarfrag_ck = (LinearLayout) view.findViewById(R.id.ll_hotcarfrag_ck);
            iv_hotcarfrag_bg = (ImageView) view.findViewById(R.id.iv_hotcarfrag_bg);
            tv_hotcarfrag_carname = (TextView) view.findViewById(R.id.tv_hotcarfrag_carname);
            tv_hotcarfrag_cardesc = (TextView) view.findViewById(R.id.tv_hotcarfrag_cardesc);
        }
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        CarType.DataBean dataBean = arguments.getParcelable("newCarBean");
        if (dataBean != null) {
            id = dataBean.getId();
            StringUtil.setText(tv_hotcarfrag_carname, dataBean.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_hotcarfrag_cardesc, dataBean.getCategory(), "", View.VISIBLE, View.VISIBLE);
            float screenDensity = ScreenUtil.getScreenDensity(mActivity);
            RingLog.e("screenDensity = " + screenDensity);
            int windowWidth = ScreenUtil.getWindowWidth(mActivity);
            RingLog.e("windowWidth = " + windowWidth);
            int dp2px = DensityUtil.dp2px(mActivity, 100);
            RingLog.e("dp2px = " + dp2px);
            double sub = ComputeUtil.sub(windowWidth, dp2px);
            RingLog.e("sub = " + sub);
            double div = ComputeUtil.div(ComputeUtil.mul(sub, 374), 540);
            RingLog.e("div = " + div);
            ViewGroup.LayoutParams para = iv_hotcarfrag_bg.getLayoutParams();
            para.height = (int) div;
            para.width = (int) sub;
            iv_hotcarfrag_bg.setLayoutParams(para);
            GlideUtil.loadNetImg(mActivity, dataBean.getIcon(), iv_hotcarfrag_bg, R.mipmap.ic_image_load, (int) sub, (int) div);
        }
    }

    @Override
    protected void initEvent() {
        ll_hotcarfrag_ck.setOnClickListener(this);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_hotcarfrag_ck:
                startActivity(new Intent(mActivity, CarDetailActivity.class).putExtra("carId", id));
                break;
        }
    }
}