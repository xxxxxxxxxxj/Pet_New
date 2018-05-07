package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IMyFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IMyFragmentView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:33
 */
public class MyFragmentPresenter  extends BasePresenter<IMyFragmentView, IMyFragmentModel> {
    public MyFragmentPresenter(IMyFragmentView iMyFragmentView, IMyFragmentModel iMyFragmentModel) {
        super(iMyFragmentView, iMyFragmentModel);
    }
}
