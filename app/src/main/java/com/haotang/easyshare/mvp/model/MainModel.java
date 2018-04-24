package com.haotang.easyshare.mvp.model;

import android.app.Activity;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.http.MainActivityApiService;
import com.haotang.easyshare.mvp.model.imodel.IMainModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/13 15:07
 */
public class MainModel implements IMainModel {

    @Override
    public Observable getLatestVersion(Activity activity, int systemType, String version, String time) {
        return DevRing.httpManager().getService(MainActivityApiService.class).getLatestVersion(UrlConstants.getGlobalParam(UrlConstants.GET_LASTVERSION_DATA, activity), systemType, version, time);
    }

    @Override
    public Observable getBottomBar(Activity activity) {
        return DevRing.httpManager().getService(MainActivityApiService.class).getBottomBar(UrlConstants.getGlobalParam(UrlConstants.GET_BOTTOMBAR_DATA, activity));
    }
}
