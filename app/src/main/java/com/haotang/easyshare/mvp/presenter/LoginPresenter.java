package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ILoginModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ILoginView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:52
 */
public class LoginPresenter extends BasePresenter<ILoginView, ILoginModel> {
    public LoginPresenter(ILoginView iLoginView, ILoginModel iLoginModel) {
        super(iLoginView, iLoginModel);
    }
}