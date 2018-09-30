package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.model.imodel.IInputChargeCodeModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IInputChargeCodeView;
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
 * @date XJ on 2018/7/26 09:48
 */
public class InputChargeCodePresenter extends BasePresenter<IInputChargeCodeView, IInputChargeCodeModel> {
    public InputChargeCodePresenter(IInputChargeCodeView iInputChargeCodeView, IInputChargeCodeModel iInputChargeCodeModel) {
        super(iInputChargeCodeView, iInputChargeCodeModel);
    }

    /**
     * 发起充电
     *
     * @param body
     */
    public void start(Map<String, String> headers, RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.start(headers,body), new CommonObserver<StartChargeing>() {
            @Override
            public void onResult(StartChargeing result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.startSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.startFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.startFail
                                        (AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.startFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}