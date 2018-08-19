package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.ChargeList;
import com.haotang.easyshare.mvp.model.imodel.IRechargeRecordModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IRechargeRecordView;
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
 * @date XJ on 2018/7/23 17:25
 */
public class RechargeRecordPresenter extends BasePresenter<IRechargeRecordView, IRechargeRecordModel> {
    public RechargeRecordPresenter(IRechargeRecordView iRechargeRecordView, IRechargeRecordModel iRechargeRecordModel) {
        super(iRechargeRecordView, iRechargeRecordModel);
    }

    /**
     * 订单列表
     * @param build
     */
    public void list(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.list(build), new CommonObserver<ChargeList>() {
            @Override
            public void onResult(ChargeList
                                         result) {
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
}