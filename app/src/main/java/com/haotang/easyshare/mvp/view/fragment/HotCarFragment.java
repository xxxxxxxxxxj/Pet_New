package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
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
public class HotCarFragment extends BaseFragment implements View.OnClickListener {
    private TextView tv_hotcarfrag_ck;
    private ImageView iv_hotcarfrag_bg;
    private TextView tv_hotcarfrag_carname;
    private TextView tv_hotcarfrag_cardesc;
    private int id;

    public HotCarFragment() {
    }

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

    public static HotCarFragment newInstance(CarType.DataBean dataBean) {
        HotCarFragment hotCarFragment = new HotCarFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("selectFragCarBean", dataBean);
        hotCarFragment.setArguments(bundle);
        return hotCarFragment;
    }

    @Override
    protected void initView() {
        View view = getmContentView();
        if (view != null) {
            tv_hotcarfrag_ck = (TextView) view.findViewById(R.id.tv_hotcarfrag_ck);
            iv_hotcarfrag_bg = (ImageView) view.findViewById(R.id.iv_hotcarfrag_bg);
            tv_hotcarfrag_carname = (TextView) view.findViewById(R.id.tv_hotcarfrag_carname);
            tv_hotcarfrag_cardesc = (TextView) view.findViewById(R.id.tv_hotcarfrag_cardesc);
            int windowWidth = ScreenUtil.getWindowWidth(mActivity);
            int dp2px = DensityUtil.dp2px(mActivity, 40);
            double sub = ComputeUtil.sub(windowWidth, dp2px);
            double div = ComputeUtil.div(ComputeUtil.mul(sub, 374), 540);
            ViewGroup.LayoutParams para = iv_hotcarfrag_bg.getLayoutParams();
            para.height = (int) div;
            para.width = (int) sub;
            iv_hotcarfrag_bg.setLayoutParams(para);
        }
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        CarType.DataBean dataBean = arguments.getParcelable("selectFragCarBean");
        if (dataBean != null) {
            RingLog.e("dataBean = " + dataBean.toString());
            id = dataBean.getId();
            StringUtil.setText(tv_hotcarfrag_carname, dataBean.getBrand() + dataBean.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_hotcarfrag_cardesc, dataBean.getCategory(), "", View.VISIBLE, View.VISIBLE);
            if (dataBean.getBanner() != null && dataBean.getBanner().size() > 0) {
                AdvertisementBean.DataBean dataBean1 = dataBean.getBanner().get(0);
                if (dataBean1 != null) {
                    GlideUtil.loadNetImg(mActivity, dataBean1.getImg(), iv_hotcarfrag_bg, R.mipmap.ic_image_load);
                }
            }
        }
    }

    @Override
    protected void initEvent() {
        tv_hotcarfrag_ck.setOnClickListener(this);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hotcarfrag_ck:
                startActivity(new Intent(mActivity, CarDetailActivity.class).putExtra("carId", id));
                break;
        }
    }
}