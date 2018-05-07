package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IFollowDetailModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IFollowDetailView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:40
 */
public class FollowDetailPresenter extends BasePresenter<IFollowDetailView, IFollowDetailModel> {
    public FollowDetailPresenter(IFollowDetailView iFollowDetailView, IFollowDetailModel iFollowDetailModel) {
        super(iFollowDetailView, iFollowDetailModel);
    }
}