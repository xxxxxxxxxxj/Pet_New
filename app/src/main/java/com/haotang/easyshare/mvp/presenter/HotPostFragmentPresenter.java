package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.imodel.IHotPostFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IHotPostFragmentView;
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
 * @date zhoujunxia on 2019/1/9 18:26
 */
public class HotPostFragmentPresenter extends BasePresenter<IHotPostFragmentView, IHotPostFragmentModel> {
    public HotPostFragmentPresenter(IHotPostFragmentView iHotPostFragmentView, IHotPostFragmentModel iHotPostFragmentModel) {
        super(iHotPostFragmentView, iHotPostFragmentModel);
    }

    /**
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    public void list(Map<String, String> headers, RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.list(headers,body), new CommonObserver<AdvertisementBean>() {
            @Override
            public void onResult(AdvertisementBean result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.listSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.listFail(result.getCode(), result.getMsg());
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
     * 最新帖子列表
     */
    public void newest(Map<String, String> headers,RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.newest(headers,body), new CommonObserver<HotPoint>() {
            @Override
            public void onResult(HotPoint result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.newestSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.newestFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.newestFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.newestFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 热门帖子列表
     */
    public void hot(Map<String, String> headers,RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.hot(headers,body), new CommonObserver<HotPoint>() {
            @Override
            public void onResult(HotPoint result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.newestSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.newestFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.newestFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.newestFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 问题车帖子列表
     */
    public void problemCar(Map<String, String> headers,RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.problemCar(headers,body), new CommonObserver<HotPoint>() {
            @Override
            public void onResult(HotPoint result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.newestSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.newestFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.newestFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.newestFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}