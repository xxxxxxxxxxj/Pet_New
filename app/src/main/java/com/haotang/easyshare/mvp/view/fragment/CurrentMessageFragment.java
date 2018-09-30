package com.haotang.easyshare.mvp.view.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerCurrentMessageFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.CurrentMessageFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.presenter.CurrentMessageFragmentPresenter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.ICurrentMessageFragmentView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;

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
        etCurrentmsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtil.isEmpty(StringUtil.checkEditText(etCurrentmsg))&&
                        s.toString().trim().length() >= 500) {
                    RingToast.show("字数不能超过500");
                }
            }
        });
    }

    @Override
    public void requestData() {

    }

    public void saveMsg() {
        if (StringUtil.isEmpty(StringUtil.checkEditText(etCurrentmsg))) {
            RingToast.show("请输入您的问题哦~");
            SystemUtil.goneJP(getActivity());
            return;
        }
        showDialog();
        mPresenter.save(UrlConstants.getMapHeader(mActivity),1, etCurrentmsg.getText().toString().trim());
    }

    @Override
    public void saveSuccess(AddChargeBean data) {
        disMissDialog();
        etCurrentmsg.setText("");
        RingToast.show("发布成功");
        DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_HISTORYMESSAGEFRAGMET));
    }

    @Override
    public void saveFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "historyFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }
}