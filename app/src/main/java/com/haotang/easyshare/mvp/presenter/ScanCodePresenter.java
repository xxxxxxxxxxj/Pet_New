package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IScanCodeModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IScanCodeView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/24 10:58
 */
public class ScanCodePresenter extends BasePresenter<IScanCodeView, IScanCodeModel> {
    public ScanCodePresenter(IScanCodeView iScanCodeView, IScanCodeModel iScanCodeModel) {
        super(iScanCodeView, iScanCodeModel);
    }
}