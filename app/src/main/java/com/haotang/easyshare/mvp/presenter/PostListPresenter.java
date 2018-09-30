package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.imodel.IPostListModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IPostListView;
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
 * @date XJ on 2018/5/8 16:51
 */
public class PostListPresenter extends BasePresenter<IPostListView, IPostListModel> {
    public PostListPresenter(IPostListView iPostListView, IPostListModel iPostListModel) {
        super(iPostListView, iPostListModel);
    }

    /**
     * 最新帖子列表
     */
    public void newest(Map<String, String> headers, RequestBody body) {
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
                                mIView.newestFail
                                        (AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
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