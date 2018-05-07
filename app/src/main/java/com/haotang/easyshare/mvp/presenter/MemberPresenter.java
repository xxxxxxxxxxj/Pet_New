package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.mvp.model.imodel.IMemberModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IMemberView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:56
 */
public class MemberPresenter extends BasePresenter<IMemberView, IMemberModel> {
    public MemberPresenter(IMemberView iMemberView, IMemberModel iMemberModel) {
        super(iMemberView, iMemberModel);
    }
}