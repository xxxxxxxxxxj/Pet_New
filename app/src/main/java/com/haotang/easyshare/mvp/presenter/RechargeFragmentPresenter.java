package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IRechargeFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IRechargeFragmentView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 12:04
 */
public class RechargeFragmentPresenter extends BasePresenter<IRechargeFragmentView, IRechargeFragmentModel> {
    public RechargeFragmentPresenter(IRechargeFragmentView iRechargeFragmentView, IRechargeFragmentModel iRechargeFragmentModel) {
        super(iRechargeFragmentView, iRechargeFragmentModel);
    }
}