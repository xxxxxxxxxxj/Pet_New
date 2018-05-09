package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IAddAddressModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IAddAddressView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 16:24
 */
public class AddAddressPresenter extends BasePresenter<IAddAddressView, IAddAddressModel> {
    public AddAddressPresenter(IAddAddressView iAddAddressView, IAddAddressModel iAddAddressModel) {
        super(iAddAddressView, iAddAddressModel);
    }
}