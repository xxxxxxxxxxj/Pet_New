package com.haotang.deving.shareutil.login;

import com.haotang.deving.shareutil.login.result.BaseToken;

/**
 * Created by shaohui on 2016/12/2.
 */

public abstract class LoginListener {

    public abstract void loginSuccess(LoginResult result);

    public void beforeFetchUserInfo(BaseToken token) {
    }

    public abstract void loginFailure(Exception e);

    public abstract void loginCancel();
}
