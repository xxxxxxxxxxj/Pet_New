package com.haotang.deving.mvp.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.haotang.deving.R;
import com.haotang.deving.mvp.view.activity.TestActivity;
import com.haotang.deving.mvp.view.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 21:00
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.tv_myfrag)
    TextView tvMyfrag;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.myfragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.tv_myfrag})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_myfrag:
                startActivity(new Intent(mActivity, TestActivity.class));
                break;
        }
    }
}
