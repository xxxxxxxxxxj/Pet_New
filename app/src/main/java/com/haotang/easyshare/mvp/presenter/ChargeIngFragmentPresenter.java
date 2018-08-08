package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.ChargeingState;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.model.imodel.IChargeIngFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IChargeIngFragmentView;
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
 * @date XJ on 2018/7/20 14:41
 */
public class ChargeIngFragmentPresenter extends BasePresenter<IChargeIngFragmentView, IChargeIngFragmentModel> {
    public ChargeIngFragmentPresenter(IChargeIngFragmentView iChargeIngFragmentView, IChargeIngFragmentModel iChargeIngFragmentModel) {
        super(iChargeIngFragmentView, iChargeIngFragmentModel);
    }

    /**
     * 获取进行中的订单
     */
    public void ing() {
        DevRing.httpManager().commonRequest(mIModel.ing(), new CommonObserver<StartChargeing>() {
            @Override
            public void onResult(StartChargeing result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.ingSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.ingFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.ingFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.ingFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 查询充电状态
     *
     * @param build
     */
    public void state(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.state(build), new CommonObserver<ChargeingState>() {
            @Override
            public void onResult(ChargeingState result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.stateSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.stateFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.stateFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.stateFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 结束充电
     *
     * @param build
     */
    public void stop(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.stop(build), new CommonObserver<ChargeingState>() {
            @Override
            public void onResult(ChargeingState result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.stopSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.stopFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.stopFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.stopFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 获取账单
     *
     * @param build
     */
    public void bill(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.bill(build), new CommonObserver<ChargeingState>() {
            @Override
            public void onResult(ChargeingState result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.billSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.billFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.billFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.billFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 支付账单
     *
     * @param build
     */
    public void pay(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.pay(build), new CommonObserver<ChargeingState>() {
            @Override
            public void onResult(ChargeingState result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.paySuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.payFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.payFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.payFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
