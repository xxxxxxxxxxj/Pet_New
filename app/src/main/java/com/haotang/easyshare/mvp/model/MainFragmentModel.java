package com.haotang.easyshare.mvp.model;

import android.app.Activity;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.http.FlashApiService;
import com.haotang.easyshare.mvp.model.http.MainFragApiService;
import com.haotang.easyshare.mvp.model.imodel.IMainFragmentModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 18:18
 */
public class MainFragmentModel implements IMainFragmentModel {
    //获取首页数据
    @Override
    public Observable getMainFragData(Activity activity) {
        return DevRing.httpManager().getService(MainFragApiService.class).getMainFragData(UrlConstants.getGlobalParam(UrlConstants.GET_MAINFRAG_DATA, activity));
    }
}
