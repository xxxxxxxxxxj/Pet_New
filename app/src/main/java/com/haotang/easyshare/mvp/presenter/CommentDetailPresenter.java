package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.CommentBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.ICommentDetailModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ICommentDetailView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import java.util.Map;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:35
 */
public class CommentDetailPresenter extends BasePresenter<ICommentDetailView, ICommentDetailModel> {
    public CommentDetailPresenter(ICommentDetailView iCommentDetailView, ICommentDetailModel iCommentDetailModel) {
        super(iCommentDetailView, iCommentDetailModel);
    }


    /**
     * 充电桩评论列表
     * @param uuid
     * @param mNextRequestPage
     */
    public void list(Map<String, String> headers, String uuid, int mNextRequestPage) {
        DevRing.httpManager().commonRequest(mIModel.list(headers,uuid, mNextRequestPage), new CommonObserver<HttpResult<CommentBean>>() {
            @Override
            public void onResult(HttpResult<CommentBean> result) {
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
}