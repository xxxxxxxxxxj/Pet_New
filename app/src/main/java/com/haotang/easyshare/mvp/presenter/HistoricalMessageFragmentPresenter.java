package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.HistoricalMsg;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IHistoricalMessageFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IHistoricalMessageFragmentView;
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
 * @date XJ on 2018/5/7 18:25
 */
public class HistoricalMessageFragmentPresenter extends BasePresenter<IHistoricalMessageFragmentView, IHistoricalMessageFragmentModel> {
    public HistoricalMessageFragmentPresenter(IHistoricalMessageFragmentView iHistoricalMessageFragmentView, IHistoricalMessageFragmentModel iHistoricalMessageFragmentModel) {
        super(iHistoricalMessageFragmentView, iHistoricalMessageFragmentModel);
    }

    /**
     * 管家留言列表
     */
    public void history() {
        DevRing.httpManager().commonRequest(mIModel.history(),
                new CommonObserver<HistoricalMsg>() {
                    @Override
                    public void onResult(HistoricalMsg result) {
                        if (mIView != null) {
                            if (result != null) {
                                if (result.getCode() == 0) {
                                    mIView.historySuccess(result.getData());
                                } else {
                                    if (StringUtil.isNotEmpty(result.getMsg())) {
                                        mIView.historyFail(result.getCode(), result.getMsg());
                                    } else {
                                        mIView.historyFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG
                                                + "-code=" + result.getCode());
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int errType, String errMessage) {
                        if (mIView != null) {
                            mIView.historyFail(errType, errMessage);
                        }
                    }
                }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
