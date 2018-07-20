package com.haotang.easyshare.mvp.view.fragment;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerChargeIngFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.ChargeIngFragmentModule;
import com.haotang.easyshare.mvp.presenter.ChargeIngFragmentPresenter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IChargeIngFragmentView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:充电主界面</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:36
 */
public class ChargeIngFragment extends BaseFragment<ChargeIngFragmentPresenter> implements IChargeIngFragmentView {
    protected final static String TAG = ChargeIngFragment.class.getSimpleName();

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.chargeingfragment;
    }

    @Override
    protected void initView() {
        DaggerChargeIngFragmentCommponent.builder()
                .chargeIngFragmentModule(new ChargeIngFragmentModule(this, mActivity))
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
