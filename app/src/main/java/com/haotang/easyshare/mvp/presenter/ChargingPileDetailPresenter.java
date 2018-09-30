package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.ChargeDetailBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IChargingPileDetailModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IChargingPileDetailView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:16
 */
public class ChargingPileDetailPresenter extends BasePresenter<IChargingPileDetailView, IChargingPileDetailModel> {
    public ChargingPileDetailPresenter(IChargingPileDetailView iChargingPileDetailView, IChargingPileDetailModel iChargingPileDetailModel) {
        super(iChargingPileDetailView, iChargingPileDetailModel);
    }

    /**
     * 充电桩详情
     *
     * @param lng
     * @param lat
     * @param uuid
     * @param md5
     */
    public void detail(Map<String, String> headers,double lng, double lat, String uuid, String md5) {
        DevRing.httpManager().commonRequest(mIModel.detail(headers,lng, lat, uuid, md5), new CommonObserver<HttpResult<ChargeDetailBean>>() {
            @Override
            public void onResult(HttpResult<ChargeDetailBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.detailSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.detailFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.detailFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.detailFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 收藏充电桩
     *
     * @param parmMap
     */
    public void follow(Map<String, String> headers,Map<String, String> parmMap) {
        DevRing.httpManager().commonRequest(mIModel.follow(headers,parmMap), new CommonObserver<HttpResult<AddChargeBean>>() {
            @Override
            public void onResult(HttpResult<AddChargeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.followSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.followFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.followFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.followFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 取消收藏充电桩
     *
     * @param parmMap
     */
    public void cancel(Map<String, String> headers,Map<String, String> parmMap) {
        DevRing.httpManager().commonRequest(mIModel.cancel(headers,parmMap), new CommonObserver<HttpResult<AddChargeBean>>() {
            @Override
            public void onResult(HttpResult<AddChargeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.cancelSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.cancelFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.cancelFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.cancelFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 浮窗
     *
     * @param body
     */
    public void list(Map<String, String> headers,MultipartBody body) {
        DevRing.httpManager().commonRequest(mIModel.list(headers,body), new CommonObserver<AdvertisementBean>() {
            @Override
            public void onResult(AdvertisementBean result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.listSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.listFail(result.getCode(), result.getMsg());
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
                    mIView.listFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}