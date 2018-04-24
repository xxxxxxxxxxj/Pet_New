package com.haotang.easyshare.mvp.presenter;

import android.app.Activity;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.FlashBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IFlashModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IFlashView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:欢迎页页面的Presenter层</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 18:10
 */
public class FlashPresenter extends BasePresenter<IFlashView, IFlashModel> {
    public FlashPresenter(IFlashView iFlashView, IFlashModel iFlashModel) {
        super(iFlashView, iFlashModel);
    }

    /**
     * 获取广告页数据
     */
    public void startPageConfig(Activity activity) {
        DevRing.httpManager().commonRequest(mIModel.startPageConfig(activity), new CommonObserver<HttpResult<FlashBean>>() {
            @Override
            public void onResult(HttpResult<FlashBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.getFlashSuccess(result.getData());
                        } else {
                            if (!StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.getFlashFail(AppConfig.SERVER_ERROR, result.getMsg());
                            } else {
                                mIView.getFlashFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.getFlashFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
