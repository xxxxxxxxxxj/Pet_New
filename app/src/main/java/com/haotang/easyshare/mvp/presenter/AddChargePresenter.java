package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IAddChargeModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IAddChargeView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 16:40
 */
public class AddChargePresenter extends BasePresenter<IAddChargeView, IAddChargeModel> {
    public AddChargePresenter(IAddChargeView iAddChargeView, IAddChargeModel iAddChargeModel) {
        super(iAddChargeView, iAddChargeModel);
    }
}