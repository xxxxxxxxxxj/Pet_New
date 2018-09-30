package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.RechargePayInfo;
import com.haotang.easyshare.mvp.model.entity.res.RechargeTemp;

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
 * @date XJ on 2018/8/6 13:22
 */
public interface RechargeApiService {
    /**
     * 可充值模板列表
     *
     * @return
     */
    @POST(UrlConstants.RECHARGE_TEMP)
    Observable<RechargeTemp> list(@HeaderMap Map<String, String> headers);

    /**
     * 发起充值请求
     */
    @POST(UrlConstants.RECHARGE_BUILD)
    Observable<RechargePayInfo> build(@HeaderMap Map<String, String> headers, @Body RequestBody body);
}
