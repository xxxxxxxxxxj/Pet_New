package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.ICurrentMessageFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ICurrentMessageFragmentView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:20
 */
public class CurrentMessageFragmentPresenter extends BasePresenter<ICurrentMessageFragmentView, ICurrentMessageFragmentModel> {
    public CurrentMessageFragmentPresenter(ICurrentMessageFragmentView iCurrentMessageFragmentView, ICurrentMessageFragmentModel iCurrentMessageFragmentModel) {
        super(iCurrentMessageFragmentView, iCurrentMessageFragmentModel);
    }
}
