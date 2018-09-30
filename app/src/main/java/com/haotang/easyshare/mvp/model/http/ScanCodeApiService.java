package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;

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
 * @date XJ on 2018/8/6 14:39
 */
public interface ScanCodeApiService {
    /**
     * 发起充电
     *
     * @param body
     */
    @POST(UrlConstants.START_CHARGEING)
    Observable<StartChargeing> start(@HeaderMap Map<String, String> headers, @Body RequestBody body);
}
