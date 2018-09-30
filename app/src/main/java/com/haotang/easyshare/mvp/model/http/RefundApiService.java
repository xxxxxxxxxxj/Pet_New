package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.ReFundExplain;
import com.haotang.easyshare.mvp.model.entity.res.ReFundSubmit;
import com.haotang.easyshare.mvp.model.entity.res.ReFundTag;

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
 * @date zhoujunxia on 2018/8/19 12:01
 */
public interface RefundApiService {
    /**
     * 标签列表
     *
     * @param build
     */
    @POST(UrlConstants.REFUND_TAG)
    Observable<ReFundTag> list(@HeaderMap Map<String, String> headers, @Body RequestBody build);

    /**
     * 退款说明
     */
    @POST(UrlConstants.REFUND_EXPLAN)
    Observable<ReFundExplain> explain(@HeaderMap Map<String, String> headers);

    /**
     * 退款说明
     */
    @POST(UrlConstants.REFUND)
    Observable<ReFundSubmit> refund(@HeaderMap Map<String, String> headers,@Body RequestBody build);
}
