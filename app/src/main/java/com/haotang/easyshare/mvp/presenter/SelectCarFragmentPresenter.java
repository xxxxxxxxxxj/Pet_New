package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ISelectCarFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ISelectCarFragmentView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:48
 */
public class SelectCarFragmentPresenter extends BasePresenter<ISelectCarFragmentView, ISelectCarFragmentModel> {
    public SelectCarFragmentPresenter(ISelectCarFragmentView iSelectCarFragmentView, ISelectCarFragmentModel iSelectCarFragmentModel) {
        super(iSelectCarFragmentView, iSelectCarFragmentModel);
    }
}
