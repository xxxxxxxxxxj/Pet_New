package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IPostListModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IPostListView;

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
}