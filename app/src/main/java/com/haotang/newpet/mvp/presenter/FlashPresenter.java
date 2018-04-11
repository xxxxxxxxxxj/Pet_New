package com.haotang.newpet.mvp.presenter;

import com.haotang.newpet.mvp.model.entity.res.FlashBean;
import com.haotang.newpet.mvp.model.entity.res.HttpResult;
import com.haotang.newpet.mvp.model.imodel.IFlashMoel;
import com.haotang.newpet.mvp.presenter.base.BasePresenter;
import com.haotang.newpet.mvp.view.iview.IFlashView;
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
public class FlashPresenter extends BasePresenter<IFlashView, IFlashMoel> {
    public FlashPresenter(IFlashView iFlashView, IFlashMoel iFlashMoel) {
        super(iFlashView, iFlashMoel);
    }

    /**
     * 获取广告页数据
     */
    public void startPageConfig() {
        DevRing.httpManager().commonRequest(mIModel.startPageConfig(), new CommonObserver<HttpResult<FlashBean>>() {
            @Override
            public void onResult(HttpResult<FlashBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            if (result.getSubjects() != null) {
                                mIView.getFlashSuccess(result.getSubjects());
                            }
                        } else {
                            int errType = 9999;
                            String errMessage = "服务器异常";
                            if (result.getMsg() != null && !result.getMsg().isEmpty()) {
                                errMessage = result.getMsg();
                            }
                            mIView.getFlashFail(errType, errMessage);
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
