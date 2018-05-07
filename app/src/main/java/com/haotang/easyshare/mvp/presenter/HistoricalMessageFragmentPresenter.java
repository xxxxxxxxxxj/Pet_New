package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IHistoricalMessageFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IHistoricalMessageFragmentView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:25
 */
public class HistoricalMessageFragmentPresenter extends BasePresenter<IHistoricalMessageFragmentView, IHistoricalMessageFragmentModel> {
    public HistoricalMessageFragmentPresenter(IHistoricalMessageFragmentView iHistoricalMessageFragmentView, IHistoricalMessageFragmentModel iHistoricalMessageFragmentModel) {
        super(iHistoricalMessageFragmentView, iHistoricalMessageFragmentModel);
    }
}
