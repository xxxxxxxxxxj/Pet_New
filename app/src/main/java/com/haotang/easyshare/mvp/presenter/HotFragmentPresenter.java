package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IHotFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IHotFragmentView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:29
 */
public class HotFragmentPresenter extends BasePresenter<IHotFragmentView, IHotFragmentModel> {
    public HotFragmentPresenter(IHotFragmentView iHotFragmentView, IHotFragmentModel iHotFragmentModel) {
        super(iHotFragmentView, iHotFragmentModel);
    }
}
