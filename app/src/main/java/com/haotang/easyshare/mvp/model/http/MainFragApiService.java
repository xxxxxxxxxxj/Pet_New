package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 18:21
 */
public interface MainFragApiService {
    /**
     * 获取首页数据
     */
    @GET(UrlConstants.HOMEINDEX)
    Observable<HttpResult<MainFragmentData>> homeIndex(@Query("lng") double lng, @Query("lat") double lat);
}
