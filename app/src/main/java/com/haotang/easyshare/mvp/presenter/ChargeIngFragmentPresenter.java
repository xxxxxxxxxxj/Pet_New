package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.ChargeingState;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IChargeIngFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IChargeIngFragmentView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import java.util.Map;

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
     * 用户主页信息
     */
    public void home(Map<String, String> headers) {
        DevRing.httpManager().commonRequest(mIModel.home(headers),
                new CommonObserver<HttpResult<HomeBean>>() {
                    @Override
                    public void onResult(HttpResult<HomeBean> result) {
                        if (mIView != null) {
                            if (result != null) {
                                if (result.getCode() == 0) {
                                    mIView.homeSuccess(result.getData());
                                } else {
                                    if (StringUtil.isNotEmpty(result.getMsg())) {
                                        mIView.homeFail(result.getCode(), result.getMsg());
                                    } else {
                                        mIView.homeFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG
                                                + "-code=" + result.getCode());
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int errType, String errMessage) {
                        if (mIView != null) {
                            mIView.homeFail(errType, errMessage);
                        }
                    }
                }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 获取进行中的订单
     */
    public void ing(Map<String, String> headers, RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.ing(headers,build), new CommonObserver<StartChargeing>() {
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
     * 结束充电
     *
     * @param build
     */
    public void stop(Map<String, String> headers,RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.stop(headers,build), new CommonObserver<ChargeingState>() {
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
     * 支付账单
     *
     * @param build
     */
    public void pay(Map<String, String> headers,RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.pay(headers,build), new CommonObserver<ChargeingState>() {
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

    /**
     * 故障报修
     *
     * @param build
     */
    public void save(Map<String, String> headers,RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.save(headers,build), new CommonObserver<HttpResult<AddChargeBean>>() {
            @Override
            public void onResult(HttpResult<AddChargeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.saveSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.saveFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.saveFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.saveFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 取消订单
     *
     * @param build
     */
    public void cancelOrder(Map<String, String> headers,RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.cancelOrder(headers,build), new CommonObserver<HttpResult<AddChargeBean>>() {
            @Override
            public void onResult(HttpResult<AddChargeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.cancelOrderSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.cancelOrderFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.cancelOrderFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.cancelOrderFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
