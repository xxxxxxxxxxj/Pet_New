package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.HistoricalMsg;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/14 14:52
 */
public interface HistoricalMessageApiService {
    /**
     * 管家留言列表
     */
    @GET(UrlConstants.HISTORYMSG)
    Observable<HistoricalMsg> history(@HeaderMap Map<String, String> headers);
}
