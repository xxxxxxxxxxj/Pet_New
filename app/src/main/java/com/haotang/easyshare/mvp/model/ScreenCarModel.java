package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.ScreenCarApiService;
import com.haotang.easyshare.mvp.model.imodel.IScreenCarModel;
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
 * @date XJ on 2018/7/26 10:36
 */
public class ScreenCarModel implements IScreenCarModel {
    /**
     * 车型检索条件
     */
    @Override
    public Observable items(Map<String, String> headers) {
        return DevRing.httpManager().getService(ScreenCarApiService.class).items(headers);
    }

    /**
     * 车型检索
     */
    @Override
    public Observable query(Map<String, String> headers,RequestBody build) {
        return DevRing.httpManager().getService(ScreenCarApiService.class).query(headers,build);
    }
}
