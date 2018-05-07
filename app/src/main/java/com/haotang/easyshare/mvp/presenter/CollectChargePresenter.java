package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ICollectChargeModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ICollectChargeView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:24
 */
public class CollectChargePresenter extends BasePresenter<ICollectChargeView, ICollectChargeModel> {
    public CollectChargePresenter(ICollectChargeView iCollectChargeView, ICollectChargeModel iCollectChargeModel) {
        super(iCollectChargeView, iCollectChargeModel);
    }
}