package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ICommentDetailModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ICommentDetailView;

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
}