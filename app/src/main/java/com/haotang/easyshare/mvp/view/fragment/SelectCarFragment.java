package com.haotang.easyshare.mvp.view.fragment;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerSelectCarFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.SelectCarFragmentModule;
import com.haotang.easyshare.mvp.presenter.SelectCarFragmentPresenter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.ISelectCarFragmentView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:选车主界面</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:36
 */
public class SelectCarFragment extends BaseFragment<SelectCarFragmentPresenter> implements ISelectCarFragmentView {
    protected final static String TAG = SelectCarFragment.class.getSimpleName();

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.selectfragment;
    }

    @Override
    protected void initView() {
        DaggerSelectCarFragmentCommponent.builder()
                .selectCarFragmentModule(new SelectCarFragmentModule(this, mActivity))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void requestData() {

    }
}