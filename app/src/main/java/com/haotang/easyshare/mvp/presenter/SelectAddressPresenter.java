package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ISelectAddressModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ISelectAddressView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 15:59
 */
public class SelectAddressPresenter extends BasePresenter<ISelectAddressView, ISelectAddressModel> {
    public SelectAddressPresenter(ISelectAddressView iSelectAddressView, ISelectAddressModel iSelectAddressModel) {
        super(iSelectAddressView, iSelectAddressModel);
    }
}