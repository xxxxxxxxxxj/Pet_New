package com.haotang.easyshare.mvp.view.fragment;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 20:59
 */
public class HotFragment extends BaseFragment {

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

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}