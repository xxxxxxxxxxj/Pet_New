package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.SerchPostApiService;
import com.haotang.easyshare.mvp.model.imodel.ISerchPostModel;
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
 * @date zhoujunxia on 2018/12/16 10:07
 */
public class SerchPostModel implements ISerchPostModel {
    /**
     * 文章热门搜索关键字
     *
     * @param headers
     */
    @Override
    public Observable keys(Map<String, String> headers) {
        return DevRing.httpManager().getService(SerchPostApiService.class).keys(headers);
    }

    /**
     * 搜索文章
     *
     * @param headers
     */
    @Override
    public Observable list(Map<String, String> headers, RequestBody body) {
        return DevRing.httpManager().getService(SerchPostApiService.class).list(headers, body);
    }
}
