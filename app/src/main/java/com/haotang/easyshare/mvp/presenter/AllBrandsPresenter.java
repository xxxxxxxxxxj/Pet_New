package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IAllBrandsModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IAllBrandsView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 16:43
 */
public class AllBrandsPresenter extends BasePresenter<IAllBrandsView, IAllBrandsModel> {
    public AllBrandsPresenter(IAllBrandsView iAllBrandsView, IAllBrandsModel iAllBrandsModel) {
        super(iAllBrandsView, iAllBrandsModel);
    }
}