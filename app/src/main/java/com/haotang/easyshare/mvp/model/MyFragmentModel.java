package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.MyFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.IMyFragmentModel;
import com.ljy.devring.DevRing;

import java.util.Map;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:36
 */
public class MyFragmentModel implements IMyFragmentModel {
    /**
     * 用户主页信息
     */
    @Override
    public Observable home(Map<String, String> headers) {
        return DevRing.httpManager().getService(MyFragmentApiService.class).home(headers);
    }

    /**
     * 用户车辆信息
     */
    @Override
    public Observable my(Map<String, String> headers) {
        return DevRing.httpManager().getService(MyFragmentApiService.class).my(headers);
    }
}
