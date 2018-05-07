package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IMyPostModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IMyPostView;

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
}