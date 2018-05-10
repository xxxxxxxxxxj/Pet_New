package com.haotang.easyshare.mvp.model.imodel;

import android.app.Activity;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:53
 */
public interface ILoginModel extends IBaseModel{
    /**
     * 下发验证码
     */
    Observable sendVerifyCode(String phone);
}
