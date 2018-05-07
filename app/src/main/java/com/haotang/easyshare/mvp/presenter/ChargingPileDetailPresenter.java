package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IChargingPileDetailModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IChargingPileDetailView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:16
 */
public class ChargingPileDetailPresenter extends BasePresenter<IChargingPileDetailView, IChargingPileDetailModel> {
    public ChargingPileDetailPresenter(IChargingPileDetailView iChargingPileDetailView, IChargingPileDetailModel iChargingPileDetailModel) {
        super(iChargingPileDetailView, iChargingPileDetailModel);
    }
}