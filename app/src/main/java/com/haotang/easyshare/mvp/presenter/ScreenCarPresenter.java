package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.entity.res.ScreenCarItem;
import com.haotang.easyshare.mvp.model.imodel.IScreenCarModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IScreenCarView;
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
 * @date XJ on 2018/7/26 10:33
 */
public class ScreenCarPresenter extends BasePresenter<IScreenCarView, IScreenCarModel> {
    public ScreenCarPresenter(IScreenCarView iScreenCarView, IScreenCarModel iScreenCarModel) {
        super(iScreenCarView, iScreenCarModel);
    }

    /**
     * 车型检索条件
     */
    public void items(Map<String, String> headers) {
        DevRing.httpManager().commonRequest(mIModel.items(headers), new CommonObserver<ScreenCarItem>() {
            @Override
            public void onResult(ScreenCarItem result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.itemsSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.itemsFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.itemsFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.itemsFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 车型检索条件
     */
    public void query(Map<String, String> headers,RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.query(headers,build), new CommonObserver<HotSpecialCarBean>() {
            @Override
            public void onResult(HotSpecialCarBean result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.querySuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.queryFail
                                        (result.getCode(), result.getMsg());
                            } else {
                                mIView.queryFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.queryFail
                            (errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}