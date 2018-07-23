package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IRechargeModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IRechargeView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 16:46
 */
public class RechargePresenter extends BasePresenter<IRechargeView, IRechargeModel> {
    public RechargePresenter(IRechargeView iRechargeView, IRechargeModel iRechargeModel) {
        super(iRechargeView, iRechargeModel);
    }
}