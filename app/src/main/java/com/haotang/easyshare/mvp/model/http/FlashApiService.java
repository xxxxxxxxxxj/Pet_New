package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.mvp.model.entity.res.FlashBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/12 11:42
 */
public interface FlashApiService {
    /**
     * 获取广告页数据
     */
    @GET
    Observable<HttpResult<FlashBean>> startPageConfig(@Url String url);
}
