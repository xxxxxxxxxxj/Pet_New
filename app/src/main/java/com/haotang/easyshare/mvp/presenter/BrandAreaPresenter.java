package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.imodel.IBrandAreaModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IBrandAreaView;
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
 * @date XJ on 2018/5/8 16:47
 */
public class BrandAreaPresenter extends BasePresenter<IBrandAreaView, IBrandAreaModel> {
    public BrandAreaPresenter(IBrandAreaView iBrandAreaView, IBrandAreaModel iBrandAreaModel) {
        super(iBrandAreaView, iBrandAreaModel);
    }

    /**
     * 品牌热帖
     */
    public void article(RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.article(body), new CommonObserver<HotPoint>() {
            @Override
            public void onResult(HotPoint result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.articleSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.articleFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.articleFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.articleFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}