package com.haotang.newpet.mvp.model.imodel;

import android.app.Activity;

import com.haotang.newpet.mvp.model.imodel.base.IBaseModel;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 18:39
 */
public interface IFlashModel extends IBaseModel {
    Observable startPageConfig(Activity activity);
}
