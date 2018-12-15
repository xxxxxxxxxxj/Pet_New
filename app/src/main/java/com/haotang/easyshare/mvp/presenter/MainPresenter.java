package com.haotang.easyshare.mvp.presenter;

import android.app.Activity;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.BootmBarBean;
import com.haotang.easyshare.mvp.model.entity.res.LastVersionBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IMainModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IMainView;
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
 * @date XJ on 2018/4/13 14:49
 */
public class MainPresenter extends BasePresenter<IMainView, IMainModel> {
    public MainPresenter(IMainView iMainView, IMainModel iMainModel) {
        super(iMainView, iMainModel);
    }

    /**
     * 获取最新版本
     */
    public void getLatestVersion(Map<String, String> headers) {
        DevRing.httpManager().commonRequest(mIModel.getLatestVersion(headers),
                new CommonObserver<HttpResult<LastVersionBean>>() {
            @Override
            public void onResult(HttpResult<LastVersionBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.getLatestVersionSuccess(result.getData());
                        } else {
                            if (!StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.getLatestVersionFail(AppConfig.SERVER_ERROR, result.getMsg());
                            } else {
                                mIView.getLatestVersionFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 获取底部bar
     */
    public void getBottomBar(Map<String, String> headers,Activity activity) {
        DevRing.httpManager().commonRequest(mIModel.getBottomBar(headers,activity),
                new CommonObserver<HttpResult<BootmBarBean>>() {
            @Override
            public void onResult(HttpResult<BootmBarBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.getBootmBarSuccess(result.getData());
                        } else {
                            if (!StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.getBootmBarFail(AppConfig.SERVER_ERROR, result.getMsg());
                            } else {
                                mIView.getBootmBarFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.getBootmBarFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
