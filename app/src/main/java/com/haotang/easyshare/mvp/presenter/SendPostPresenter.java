package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ISendPostModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ISendPostView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:07
 */
public class SendPostPresenter extends BasePresenter<ISendPostView, ISendPostModel> {
    public SendPostPresenter(ISendPostView iSendPostView, ISendPostModel iSendPostModel) {
        super(iSendPostView, iSendPostModel);
    }
}