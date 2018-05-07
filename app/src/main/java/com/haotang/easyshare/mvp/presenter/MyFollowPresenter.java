package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IMyFollowModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IMyFollowView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:00
 */
public class MyFollowPresenter extends BasePresenter<IMyFollowView, IMyFollowModel> {
    public MyFollowPresenter(IMyFollowView iMyFollowView, IMyFollowModel iMyFollowModel) {
        super(iMyFollowView, iMyFollowModel);
    }
}