package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.CarType;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IMainFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IMainFragmentView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 18:07
 */
public class MainFragmentPresenter extends BasePresenter<IMainFragmentView, IMainFragmentModel> {
    public MainFragmentPresenter(IMainFragmentView iMainFragmentView, IMainFragmentModel iMainFragmentModel) {
        super(iMainFragmentView, iMainFragmentModel);
    }

    /**
     * 获取首页数据
     */
    public void homeIndex(Map<String, String> headers, double lng, double lat) {
        DevRing.httpManager().commonRequest(mIModel.homeIndex(headers,lng, lat), new CommonObserver<HttpResult<MainFragmentData>>() {
            @Override
            public void onResult(HttpResult<MainFragmentData> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.getMainFragmentSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.getMainFragmentFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.getMainFragmentFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.getMainFragmentFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    public void list(Map<String, String> headers,RequestBody body) {
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
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    public void list1(Map<String, String> headers, MultipartBody body) {
        DevRing.httpManager().commonRequest(mIModel.list(headers,body), new CommonObserver<AdvertisementBean>() {
            @Override
            public void onResult(AdvertisementBean result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.list1Success(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.list1Fail(result.getCode(), result.getMsg());
                            } else {
                                mIView.list1Fail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.list1Fail(errType, errMessage);
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
                            mIView.hotSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.hotFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.hotFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.hotFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 选车首页
     * @param headers
     * @param body
     */
    public void carType(Map<String, String> headers, RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.carType(headers,body), new CommonObserver<CarType>() {
            @Override
            public void onResult(CarType result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.carTypeSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.carTypeFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.carTypeFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.carTypeFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
