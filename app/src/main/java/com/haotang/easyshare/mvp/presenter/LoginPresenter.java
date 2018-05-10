package com.haotang.easyshare.mvp.presenter;

import android.app.Activity;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.SendVerifyCodeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.ILoginModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ILoginView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:52
 */
public class LoginPresenter extends BasePresenter<ILoginView, ILoginModel> {
    public LoginPresenter(ILoginView iLoginView, ILoginModel iLoginModel) {
        super(iLoginView, iLoginModel);
    }

    /**
     * 下发验证码
     */
    public void sendVerifyCode(String phone) {
        DevRing.httpManager().commonRequest(mIModel.sendVerifyCode(phone),
                new CommonObserver<HttpResult<SendVerifyCodeBean>>() {
                    @Override
                    public void onResult(HttpResult<SendVerifyCodeBean> result) {
                        if (mIView != null) {
                            if (result != null) {
                                if (result.getCode() == 0) {
                                    mIView.sendVerifyCodeSuccess(result.getData());
                                } else {
                                    if (!StringUtil.isNotEmpty(result.getMsg())) {
                                        mIView.sendVerifyCodeFail(AppConfig.SERVER_ERROR, result.getMsg());
                                    } else {
                                        mIView.sendVerifyCodeFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG
                                                + "-code=" + result.getCode());
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int errType, String errMessage) {
                        if (mIView != null) {
                            mIView.sendVerifyCodeFail(errType, errMessage);
                        }
                    }
                }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}