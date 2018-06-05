package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.imodel.IBrandCarModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IBrandCarView;
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
 * @date XJ on 2018/6/4 18:11
 */
public class BrandCarPresenter extends BasePresenter<IBrandCarView, IBrandCarModel> {
    public BrandCarPresenter(IBrandCarView iBrandCarView, IBrandCarModel iBrandCarModel) {
        super(iBrandCarView, iBrandCarModel);
    }

    /**
     * 所有品牌
     */
    public void list() {
        DevRing.httpManager().commonRequest(mIModel.list(), new CommonObserver<HotCarBean>() {
            @Override
            public void onResult(HotCarBean result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.listSuccess(result.getData());
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
                    mIView.listFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 品牌车型
     */
    public void carList(RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.carList(body), new CommonObserver<HotSpecialCarBean>() {
            @Override
            public void onResult(HotSpecialCarBean result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.carListSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.carListFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.carListFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.listFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
