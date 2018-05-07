package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ICarInfoModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ICarInfoView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:09
 */
public class CarInfoPresenter extends BasePresenter<ICarInfoView, ICarInfoModel> {
    public CarInfoPresenter(ICarInfoView iCarInfoView, ICarInfoModel iCarInfoModel) {
        super(iCarInfoView, iCarInfoModel);
    }
}