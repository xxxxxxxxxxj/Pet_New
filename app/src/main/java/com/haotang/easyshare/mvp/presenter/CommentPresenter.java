package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentTags;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.ICommentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ICommentView;
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
 * @date XJ on 2018/5/7 17:31
 */
public class CommentPresenter extends BasePresenter<ICommentView, ICommentModel> {
    public CommentPresenter(ICommentView iCommentView, ICommentModel iCommentModel) {
        super(iCommentView, iCommentModel);
    }

    /**
     * 评论充电桩
     *
     * @param filedMap
     */
    public void save(Map<String, String> headers,Map<String, RequestBody> filedMap) {
        DevRing.httpManager().commonRequest(mIModel.save(headers,filedMap), new CommonObserver<HttpResult<AddChargeBean>>() {
            @Override
            public void onResult(HttpResult<AddChargeBean> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.saveSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.saveFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.saveFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
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


    /**
     * 评论标签
     */
    public void tags(Map<String, String> headers) {
        DevRing.httpManager().commonRequest(mIModel.tags(headers), new CommonObserver<CommentTags>() {
            @Override
            public void onResult(CommentTags result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.tagsSuccess
                                    (result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.tagsFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.tagsFail
                                        (AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.tagsFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}