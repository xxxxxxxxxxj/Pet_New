package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ISwitchCityModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ISwitchCityView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:11
 */
public class SwitchCityPresenter extends BasePresenter<ISwitchCityView, ISwitchCityModel> {
    public SwitchCityPresenter(ISwitchCityView iSwitchCityView, ISwitchCityModel iSwitchCityModel) {
        super(iSwitchCityView, iSwitchCityModel);
    }
}