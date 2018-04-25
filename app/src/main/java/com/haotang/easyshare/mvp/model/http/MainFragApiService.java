package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

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
    @GET
    Observable<HttpResult<List<MainFragmentData>>> getMainFragData(@Url String url);
}
