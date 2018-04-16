package com.haotang.deving.mvp.view.fragment;

import com.haotang.deving.R;
import com.haotang.deving.mvp.view.fragment.base.BaseFragment;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 20:34
 */
public class MainFragment extends BaseFragment{
    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.mainfragment;
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
