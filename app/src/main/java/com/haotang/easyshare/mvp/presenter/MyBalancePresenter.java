package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IMyBalanceModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IMyBalanceView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 17:44
 */
public class MyBalancePresenter extends BasePresenter<IMyBalanceView, IMyBalanceModel> {
    public MyBalancePresenter(IMyBalanceView iMyBalanceView, IMyBalanceModel iMyBalanceModel) {
        super(iMyBalanceView, iMyBalanceModel);
    }
}