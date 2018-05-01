package com.haotang.easyshare.mvp.view.fragment;

import android.widget.EditText;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;

import butterknife.BindView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 15:37
 */
public class CurrentMessageFragment extends BaseFragment {

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

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}