package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ICommentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ICommentView;

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
}