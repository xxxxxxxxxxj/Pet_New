package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IChargeIngFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IChargeIngFragmentView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:41
 */
public class ChargeIngFragmentPresenter extends BasePresenter<IChargeIngFragmentView, IChargeIngFragmentModel> {
    public ChargeIngFragmentPresenter(IChargeIngFragmentView iChargeIngFragmentView, IChargeIngFragmentModel iChargeIngFragmentModel) {
        super(iChargeIngFragmentView, iChargeIngFragmentModel);
    }
}
