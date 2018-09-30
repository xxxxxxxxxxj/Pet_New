package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.CommentBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/13 21:23
 */
public interface CommentDetailApiService {
    /**
     * 充电桩评论列表
     * @param uuid
     * @param mNextRequestPage
     */
    @GET(UrlConstants.COMMENT_LIST)
    Observable<HttpResult<CommentBean>> list(@HeaderMap Map<String, String> headers, @Query("uuid") String uuid, @Query("page") int mNextRequestPage);
}
