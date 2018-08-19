package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.HistoryList;

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
 * @date zhoujunxia on 2018/8/19 13:47
 */
public interface RechargeFragMentApiService {
    /**
     * 交易记录列表
     * @param build
     */
    @POST(UrlConstants.HISTORY_LIST)
    Observable<HistoryList> list(@Body RequestBody build);
}
