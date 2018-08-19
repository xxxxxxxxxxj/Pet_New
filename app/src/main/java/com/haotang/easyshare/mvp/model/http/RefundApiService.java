package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.ReFundExplain;
import com.haotang.easyshare.mvp.model.entity.res.ReFundSubmit;
import com.haotang.easyshare.mvp.model.entity.res.ReFundTag;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
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
    Observable<ReFundTag> list(@Body RequestBody build);

    /**
     * 退款说明
     */
    @POST(UrlConstants.REFUND_EXPLAN)
    Observable<ReFundExplain> explain();

    /**
     * 退款说明
     */
    @POST(UrlConstants.REFUND)
    Observable<ReFundSubmit> refund(@Body RequestBody build);
}
