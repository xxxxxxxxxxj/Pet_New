package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.HistoryList;
import com.haotang.easyshare.mvp.model.imodel.IRechargeFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IRechargeFragmentView;
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
 * @date XJ on 2018/7/25 12:04
 */
public class RechargeFragmentPresenter extends BasePresenter<IRechargeFragmentView, IRechargeFragmentModel> {
    public RechargeFragmentPresenter(IRechargeFragmentView iRechargeFragmentView, IRechargeFragmentModel iRechargeFragmentModel) {
        super(iRechargeFragmentView, iRechargeFragmentModel);
    }

    /**
     *交易记录列表
     * @param build
     */
    public void list(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.list(build), new CommonObserver<HistoryList>() {
            @Override
            public void onResult(HistoryList result) {
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