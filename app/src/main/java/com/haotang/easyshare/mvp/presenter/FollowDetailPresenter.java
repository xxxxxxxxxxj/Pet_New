package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IFollowDetailModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IFollowDetailView;
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
 * @date XJ on 2018/5/7 17:40
 */
public class FollowDetailPresenter extends BasePresenter<IFollowDetailView, IFollowDetailModel> {
    public FollowDetailPresenter(IFollowDetailView iFollowDetailView, IFollowDetailModel iFollowDetailModel) {
        super(iFollowDetailView, iFollowDetailModel);
    }

    /**
     * 用户信息
     *
     * @param uuid
     */
    public void info(String uuid) {
        DevRing.httpManager().commonRequest(mIModel.info(uuid), new CommonObserver<HttpResult<HomeBean>>() {
            @Override
            public void onResult(HttpResult<HomeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.infoSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.infoFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.infoFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.infoFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 用户帖子列表
     */
    public void list(Map<String, String> parms) {
        DevRing.httpManager().commonRequest(mIModel.list(parms), new CommonObserver<PostBean>() {
            @Override
            public void onResult(PostBean result) {
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
     * 关注用户
     *
     * @param build
     */
    public void follow(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.follow(build), new CommonObserver<HttpResult<AddChargeBean>>() {
            @Override
            public void onResult(HttpResult<AddChargeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.followSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.followFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.followFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.followFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 取消关注用户
     *
     * @param build
     */
    public void cancel(RequestBody build) {
        DevRing.httpManager().commonRequest(mIModel.cancel(build), new CommonObserver<HttpResult<AddChargeBean>>() {
            @Override
            public void onResult(HttpResult<AddChargeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.cancelSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.cancelFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.cancelFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.cancelFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}