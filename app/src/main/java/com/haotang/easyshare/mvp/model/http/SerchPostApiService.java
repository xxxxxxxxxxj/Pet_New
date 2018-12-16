package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.SerchKeysBean;

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
 * @date zhoujunxia on 2018/12/16 10:51
 */
public interface SerchPostApiService {
    /**
     * 文章热门搜索关键字
     *
     * @param headers
     */
    @POST(UrlConstants.SERCH_POST_KEYS)
    Observable<SerchKeysBean> keys(@HeaderMap Map<String, String> headers);

    /**
     * 搜索文章
     *
     * @param headers
     */
    @POST(UrlConstants.SERCH_POST)
    Observable<HotPoint> list(@HeaderMap Map<String, String> headers, @Body() RequestBody body);
}
