package com.haotang.newpet.mvp.view.activity;

import android.os.Bundle;

import com.haotang.newpet.mvp.model.entity.res.FlashBean;
import com.haotang.newpet.mvp.presenter.FlashPresenter;
import com.haotang.newpet.mvp.view.iview.IFlashView;
import com.haotang.newpet.mvp.view.activity.base.BaseActivity;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 17:47
 */
public class FlashActivity extends BaseActivity<FlashPresenter> implements IFlashView {
    @Override
    public void getFlashSuccess(FlashBean flashBean) {

    }

    @Override
    public void getFlashFail(int status, String desc) {

    }

    @Override
    protected int getContentLayout() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }
}
