package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IEditUserInfoModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IEditUserInfoView;
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
 * @date XJ on 2018/5/23 18:52
 */
public class EditUserInfoPresenter extends BasePresenter<IEditUserInfoView, IEditUserInfoModel> {
    public EditUserInfoPresenter(IEditUserInfoView iEditUserInfoView, IEditUserInfoModel iEditUserInfoModel) {
        super(iEditUserInfoView, iEditUserInfoModel);
    }

    /**
     * 用户主页信息
     */
    public void home(Map<String, String> headers) {
        DevRing.httpManager().commonRequest(mIModel.home(headers),
                new CommonObserver<HttpResult<HomeBean>>() {
                    @Override
                    public void onResult(HttpResult<HomeBean> result) {
                        if (mIView != null) {
                            if (result != null) {
                                if (result.getCode() == 0) {
                                    mIView.homeSuccess(result.getData());
                                } else {
                                    if (StringUtil.isNotEmpty(result.getMsg())) {
                                        mIView.homeFail(result.getCode(), result.getMsg());
                                    } else {
                                        mIView.homeFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG
                                                + "-code=" + result.getCode());
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int errType, String errMessage) {
                        if (mIView != null) {
                            mIView.homeFail(errType, errMessage);
                        }
                    }
                }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 上传用户信息
     *
     * @param body
     */
    public void save(Map<String, String> headers,RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.save(headers,body),
                new CommonObserver<HttpResult<AddChargeBean>>() {
                    @Override
                    public void onResult(HttpResult<AddChargeBean> result) {
                        if (mIView != null) {
                            if (result != null) {
                                if (result.getCode() == 0) {
                                    mIView.saveSuccess
                                            (result.getData());
                                } else {
                                    if (StringUtil.isNotEmpty(result.getMsg())) {
                                        mIView.saveFail(result.getCode(), result.getMsg());
                                    } else {
                                        mIView.saveFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG
                                                + "-code=" + result.getCode());
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
