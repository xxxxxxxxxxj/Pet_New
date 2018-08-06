package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.RechargePayInfo;
import com.haotang.easyshare.mvp.model.entity.res.RechargeTemp;
import com.haotang.easyshare.mvp.model.imodel.IRechargeModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IRechargeView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 16:46
 */
public class RechargePresenter extends BasePresenter<IRechargeView, IRechargeModel> {
    public RechargePresenter(IRechargeView iRechargeView, IRechargeModel iRechargeModel) {
        super(iRechargeView, iRechargeModel);
    }

    /**
     * 可充值模板列表
     */
    public void list() {
        DevRing.httpManager().commonRequest(mIModel.list(), new CommonObserver<RechargeTemp>() {
            @Override
            public void onResult(RechargeTemp result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.listSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.listFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.listFail
                                        (AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.listFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 发起充值请求
     */
    public void build(RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.build(body), new CommonObserver<RechargePayInfo>() {
            @Override
            public void onResult(RechargePayInfo result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.buildSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.buildFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.buildFail
                                        (AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.buildFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}