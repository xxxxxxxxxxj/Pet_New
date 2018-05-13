package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.MainFragChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.ILocalChargingModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ILocalChargingView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:48
 */
public class LocalChargingPresenter extends BasePresenter<ILocalChargingView, ILocalChargingModel> {
    public LocalChargingPresenter(ILocalChargingView iLocalChargingView, ILocalChargingModel iLocalChargingModel) {
        super(iLocalChargingView, iLocalChargingModel);
    }

    /**
     * 附近充电桩
     *
     * @param lng
     * @param lat
     * @param mNextRequestPage
     * @param sign
     */
    public void nearby(double lng, double lat, int mNextRequestPage, String sign) {
        DevRing.httpManager().commonRequest(mIModel.nearby(lng, lat, mNextRequestPage, sign), new CommonObserver<HttpResult<List<MainFragChargeBean>>>() {
            @Override
            public void onResult(HttpResult<List<MainFragChargeBean>> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.nearbySuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.nearbyFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.nearbyFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.nearbyFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}