package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.BootmBarBean;
import com.haotang.easyshare.mvp.model.entity.res.LastVersionBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
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
    @POST(UrlConstants.CHECK_VERSION)
    Observable<HttpResult<LastVersionBean>> getLatestVersion(@HeaderMap Map<String, String> headers,@Body() RequestBody body);

    /**
     * 获取底部bar
     */
    @GET
    Observable<HttpResult<BootmBarBean>> getBottomBar(@HeaderMap Map<String, String> headers,@Url String url);
}
