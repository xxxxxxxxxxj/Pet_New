package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IScreenCarModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IScreenCarView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 10:33
 */
public class ScreenCarPresenter extends BasePresenter<IScreenCarView, IScreenCarModel> {
    public ScreenCarPresenter(IScreenCarView iScreenCarView, IScreenCarModel iScreenCarModel) {
        super(iScreenCarView, iScreenCarModel);
    }
}