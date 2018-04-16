package com.haotang.deving.mvp.model.imodel;

import android.app.Activity;

import com.haotang.deving.mvp.model.imodel.base.IBaseModel;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/13 15:02
 */
public interface IMainModel extends IBaseModel {
    Observable getLatestVersion(Activity activity, int systemType, String version, String time);

    Observable getBottomBar(Activity activity);
}
