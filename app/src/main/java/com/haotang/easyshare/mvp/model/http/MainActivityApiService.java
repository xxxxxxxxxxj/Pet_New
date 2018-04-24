package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.mvp.model.entity.res.BootmBarBean;
import com.haotang.easyshare.mvp.model.entity.res.LastVersionBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/13 15:11
 */
public interface MainActivityApiService {
    /**
     * 获取最新版本
     */
    @GET
    Observable<HttpResult<LastVersionBean>> getLatestVersion(@Url String url, @Query("systemType") int systemType,
                                                             @Query("version") String version,
                                                             @Query("time") String time);

    /**
     * 获取底部bar
     */
    @GET
    Observable<HttpResult<BootmBarBean>> getBottomBar(@Url String url);
}
