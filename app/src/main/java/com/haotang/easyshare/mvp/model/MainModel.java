package com.haotang.easyshare.mvp.model;

import android.app.Activity;

import com.haotang.easyshare.mvp.model.http.HotFragmentApiService;
import com.haotang.easyshare.mvp.model.http.MainActivityApiService;
import com.haotang.easyshare.mvp.model.imodel.IMainModel;
import com.ljy.devring.DevRing;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

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
    public Observable getLatestVersion(Map<String, String> headers) {
        return DevRing.httpManager().getService(MainActivityApiService.class).getLatestVersion(headers);
    }

    @Override
    public Observable getBottomBar(Map<String, String> headers,Activity activity) {
        return DevRing.httpManager().getService(MainActivityApiService.class).getBottomBar(headers,"");
    }

    /**
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    @Override
    public Observable list(Map<String, String> headers,RequestBody body) {
        return DevRing.httpManager().getService(HotFragmentApiService.class).list(headers,body);
    }
}
