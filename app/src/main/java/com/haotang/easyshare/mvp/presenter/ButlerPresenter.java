package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IButlerModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IButlerView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 15:51
 */
public class ButlerPresenter extends BasePresenter<IButlerView, IButlerModel> {
    public ButlerPresenter(IButlerView iButlerView, IButlerModel iButlerModel) {
        super(iButlerView, iButlerModel);
    }
}
