package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.ChargeDetailBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IChargingPileDetailModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IChargingPileDetailView;
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
 * @date XJ on 2018/5/7 17:16
 */
public class ChargingPileDetailPresenter extends BasePresenter<IChargingPileDetailView, IChargingPileDetailModel> {
    public ChargingPileDetailPresenter(IChargingPileDetailView iChargingPileDetailView, IChargingPileDetailModel iChargingPileDetailModel) {
        super(iChargingPileDetailView, iChargingPileDetailModel);
    }

    /**
     * 充电桩详情
     * @param lng
     * @param lat
     * @param uuid
     * @param md5
     */
    public void detail(double lng, double lat, String uuid, String md5) {
        DevRing.httpManager().commonRequest(mIModel.detail(lng, lat, uuid, md5), new CommonObserver<HttpResult<ChargeDetailBean>>() {
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
}