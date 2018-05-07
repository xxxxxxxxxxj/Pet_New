package com.haotang.easyshare.mvp.view.fragment;

import android.widget.EditText;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerCurrentMessageFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.CurrentMessageFragmentModule;
import com.haotang.easyshare.mvp.presenter.CurrentMessageFragmentPresenter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.ICurrentMessageFragmentView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 15:37
 */
public class CurrentMessageFragment extends BaseFragment<CurrentMessageFragmentPresenter> implements ICurrentMessageFragmentView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.et_currentmsg)
    EditText etCurrentmsg;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.currentmessagefragment;
    }

    @Override
    protected void initView() {
        DaggerCurrentMessageFragmentCommponent.builder()
                .currentMessageFragmentModule(new CurrentMessageFragmentModule(this, mActivity))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}