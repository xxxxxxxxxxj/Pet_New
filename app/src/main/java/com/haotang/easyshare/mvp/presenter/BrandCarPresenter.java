package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.BrandCarBean;
import com.haotang.easyshare.mvp.model.imodel.IBrandCarModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IBrandCarView;
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
 * @date XJ on 2018/6/4 18:11
 */
public class BrandCarPresenter extends BasePresenter<IBrandCarView, IBrandCarModel> {
    public BrandCarPresenter(IBrandCarView iBrandCarView, IBrandCarModel iBrandCarModel) {
        super(iBrandCarView, iBrandCarModel);
    }

    /**
     * 所有品牌
     */
    public void car(Map<String, String> headers) {
        DevRing.httpManager().commonRequest(mIModel.car(headers), new CommonObserver<BrandCarBean>() {
            @Override
            public void onResult(BrandCarBean result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.carSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.carFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.carFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.carFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
