package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.LoginBean;
import com.haotang.easyshare.mvp.model.entity.res.SendVerifyCodeBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:52
 */
public interface ILoginView extends IBaseView{
    void sendVerifyCodeSuccess(SendVerifyCodeBean data);

    void sendVerifyCodeFail(int status, String desc);

    void loginSuccess(LoginBean data);

    void loginFail(int status, String desc);
}
