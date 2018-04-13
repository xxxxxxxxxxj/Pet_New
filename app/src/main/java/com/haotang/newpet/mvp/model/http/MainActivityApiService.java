package com.haotang.newpet.mvp.model.http;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
    Observable getLatestVersion(@Url String url);

    /**
     * 获取底部bar
     */
    @GET
    Observable getBottomBar(@Url String url);
}
