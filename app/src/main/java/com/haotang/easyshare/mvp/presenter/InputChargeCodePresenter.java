package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IInputChargeCodeModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IInputChargeCodeView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 09:48
 */
public class InputChargeCodePresenter extends BasePresenter<IInputChargeCodeView, IInputChargeCodeModel> {
    public InputChargeCodePresenter(IInputChargeCodeView iInputChargeCodeView, IInputChargeCodeModel iInputChargeCodeModel) {
        super(iInputChargeCodeView, iInputChargeCodeModel);
    }
}