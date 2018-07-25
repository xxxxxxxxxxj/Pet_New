package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IRefundModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IRefundView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 14:11
 */
public class RefundPresenter extends BasePresenter<IRefundView, IRefundModel> {
    public RefundPresenter(IRefundView iRefundView, IRefundModel iRefundModel) {
        super(iRefundView, iRefundModel);
    }
}