package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IMyPostModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IMyPostView;
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
 * @date XJ on 2018/5/7 18:04
 */
public class MyPostPresenter extends BasePresenter<IMyPostView, IMyPostModel> {
    public MyPostPresenter(IMyPostView iMyPostView, IMyPostModel iMyPostModel) {
        super(iMyPostView, iMyPostModel);
    }

    /**
     * 用户帖子列表
     */
    public void list(RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.list(body), new CommonObserver<PostBean>() {
            @Override
            public void onResult(PostBean result) {
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
     * 删除帖子
     */
    public void delete(RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.delete(body), new CommonObserver<HttpResult<AddChargeBean>>() {
            @Override
            public void onResult(HttpResult<AddChargeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.deleteSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.deleteFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.deleteFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.deleteFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}