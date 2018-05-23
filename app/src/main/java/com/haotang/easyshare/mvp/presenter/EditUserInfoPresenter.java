package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IEditUserInfoModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IEditUserInfoView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/23 18:52
 */
public class EditUserInfoPresenter extends BasePresenter<IEditUserInfoView, IEditUserInfoModel> {
    public EditUserInfoPresenter(IEditUserInfoView iEditUserInfoView, IEditUserInfoModel iEditUserInfoModel) {
        super(iEditUserInfoView, iEditUserInfoModel);
    }
}
