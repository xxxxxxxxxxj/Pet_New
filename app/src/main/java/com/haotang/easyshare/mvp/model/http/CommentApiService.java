package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentTags;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/13 22:16
 */
public interface CommentApiService {
    /**
     * 评论充电桩
     *
     * @param files
     */
    @Multipart
    @POST(UrlConstants.COMMENT_SAVE)
    Observable<HttpResult<AddChargeBean>> save(@HeaderMap Map<String, String> headers, @PartMap() Map<String, RequestBody> files);

    /**
     * 评论标签
     */
    @POST(UrlConstants.COMMENT_TAGS)
    Observable<CommentTags> tags(@HeaderMap Map<String, String> headers);
}
