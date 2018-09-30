package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/15 11:49
 */
public interface PostListApiService {
    /**
     * 热门帖子列表
     */
    @POST(UrlConstants.HOT_POINT)
    Observable<HotPoint> hot(@HeaderMap Map<String, String> headers, @Body() RequestBody body);

    /**
     * 问题车帖子列表
     */
    @POST(UrlConstants.PROBLEM_CAR_POINT)
    Observable<HotPoint> problemCar(@HeaderMap Map<String, String> headers,@Body() RequestBody body);
}
