package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IRefundResultModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IRefundResultView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 16:21
 */
public class RefundResultPresenter extends BasePresenter<IRefundResultView, IRefundResultModel> {
    public RefundResultPresenter(IRefundResultView iRefundResultView, IRefundResultModel iRefundResultModel) {
        super(iRefundResultView, iRefundResultModel);
    }
}