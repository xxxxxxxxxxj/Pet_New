package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ILocalChargingModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ILocalChargingView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:48
 */
public class LocalChargingPresenter extends BasePresenter<ILocalChargingView, ILocalChargingModel> {
    public LocalChargingPresenter(ILocalChargingView iLocalChargingView, ILocalChargingModel iLocalChargingModel) {
        super(iLocalChargingView, iLocalChargingModel);
    }
}