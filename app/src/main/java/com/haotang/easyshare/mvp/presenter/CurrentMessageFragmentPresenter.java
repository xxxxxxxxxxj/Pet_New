package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.ICurrentMessageFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ICurrentMessageFragmentView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import java.util.Map;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:20
 */
public class CurrentMessageFragmentPresenter extends BasePresenter<ICurrentMessageFragmentView, ICurrentMessageFragmentModel> {
    public CurrentMessageFragmentPresenter(ICurrentMessageFragmentView iCurrentMessageFragmentView, ICurrentMessageFragmentModel iCurrentMessageFragmentModel) {
        super(iCurrentMessageFragmentView, iCurrentMessageFragmentModel);
    }

    /**
     * 发布留言
     */
    public void save(Map<String, String> headers, int contentType, String content) {
        DevRing.httpManager().commonRequest(mIModel.save(headers,contentType,content), new CommonObserver<HttpResult<AddChargeBean>>() {
            @Override
            public void onResult(HttpResult<AddChargeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.saveSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.saveFail(result.getCode(), result.getMsg());
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
                    mIView.saveFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
