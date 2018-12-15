package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CarType;
import com.haotang.easyshare.mvp.view.activity.CarDetailActivity;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

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

    public HotCarFragment() {
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.hotcarfragment;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initView() {
        View view = getmContentView();
        if (view != null) {
            tv_hotcarfrag_ck = (TextView) view.findViewById(R.id.tv_hotcarfrag_ck);
            iv_hotcarfrag_bg = (ImageView) view.findViewById(R.id.iv_hotcarfrag_bg);
            tv_hotcarfrag_carname = (TextView) view.findViewById(R.id.tv_hotcarfrag_carname);
            tv_hotcarfrag_cardesc = (TextView) view.findViewById(R.id.tv_hotcarfrag_cardesc);
        }
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        CarType.DataBean dataBean = arguments.getParcelable("selectFragCarBean");
        if (dataBean != null) {
            StringUtil.setText(tv_hotcarfrag_carname, dataBean.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_hotcarfrag_cardesc, dataBean.getDesc(), "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetImg(mActivity, dataBean.getIcon(), iv_hotcarfrag_bg, R.mipmap.ic_image_load);
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
                startActivity(new Intent(mActivity, CarDetailActivity.class));
                break;
        }
    }
}