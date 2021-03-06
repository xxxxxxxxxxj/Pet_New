package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ISerchAddressModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ISerchAddressView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 16:50
 */
public class SerchAddressPresenter extends BasePresenter<ISerchAddressView, ISerchAddressModel> {
    public SerchAddressPresenter(ISerchAddressView iSerchAddressView, ISerchAddressModel iSerchAddressModel) {
        super(iSerchAddressView, iSerchAddressModel);
    }
}