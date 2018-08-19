package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.ReFundExplain;
import com.haotang.easyshare.mvp.model.entity.res.ReFundSubmit;
import com.haotang.easyshare.mvp.model.entity.res.ReFundTag;
import com.haotang.easyshare.mvp.model.imodel.IRefundModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IRefundView;
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
 * @date XJ on 2018/7/25 14:11
 */
public class RefundPresenter extends BasePresenter<IRefundView, IRefundModel> {
    public RefundPresenter(IRefundView iRefundView, IRefundModel iRefundModel) {
        super(iRefundView, iRefundModel);
    }

    /**
     * 标签列表
     * @param build
     */
    public void list(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.list(build), new CommonObserver<ReFundTag>() {
            @Override
            public void onResult(ReFundTag result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.listSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.listFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.listFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.listFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 退款说明
     */
    public void explain() {
        DevRing.httpManager().commonRequest(mIModel.explain(), new CommonObserver<ReFundExplain>() {
            @Override
            public void onResult(ReFundExplain result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.explainSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.explainFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.explainFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.explainFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 发起充值退款
     */
    public void refund(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.refund(build), new CommonObserver<ReFundSubmit>() {
            @Override
            public void onResult(ReFundSubmit result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.refundSuccess

                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.refundFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.refundFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.explainFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}