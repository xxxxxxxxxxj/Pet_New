package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IBrandAreaModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IBrandAreaView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 16:47
 */
public class BrandAreaPresenter extends BasePresenter<IBrandAreaView, IBrandAreaModel> {
    public BrandAreaPresenter(IBrandAreaView iBrandAreaView, IBrandAreaModel iBrandAreaModel) {
        super(iBrandAreaView, iBrandAreaModel);
    }
}