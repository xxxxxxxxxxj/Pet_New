package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IRechargeRecordModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IRechargeRecordView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 17:25
 */
public class RechargeRecordPresenter extends BasePresenter<IRechargeRecordView, IRechargeRecordModel> {
    public RechargeRecordPresenter(IRechargeRecordView iRechargeRecordView, IRechargeRecordModel iRechargeRecordModel) {
        super(iRechargeRecordView, iRechargeRecordModel);
    }
}