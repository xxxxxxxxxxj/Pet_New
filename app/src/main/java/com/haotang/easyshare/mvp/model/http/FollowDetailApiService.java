package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.model.entity.res.StarsBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/14 18:27
 */
public interface FollowDetailApiService {
    /**
     * 用户信息
     *
     * @param uuid
     */
    @FormUrlEncoded
    @POST(UrlConstants.USERINFO_UUID)
    Observable<HttpResult<HomeBean>> info(@HeaderMap Map<String, String> headers, @Field("uuid") String uuid);

    /**
     * 用户帖子列表
     */
    @POST(UrlConstants.USERINFO_POST)
    Observable<PostBean> list(@HeaderMap Map<String, String> headers,@Body() RequestBody body);

    /**
     * 关注用户
     */
    @POST(UrlConstants.FOLLOW_USER)
    Observable<HttpResult<AddChargeBean>> follow(@HeaderMap Map<String, String> headers,@Body() RequestBody body);

    /**
     * 取消关注用户
     */
    @POST(UrlConstants.CANCEL_FOLLOW_USER)
    Observable<HttpResult<AddChargeBean>> cancel(@HeaderMap Map<String, String> headers,@Body() RequestBody body);

    /**
     * 评价用户
     */
    @POST(UrlConstants.EVAL_USER)
    Observable<HttpResult<AddChargeBean>> eval(@HeaderMap Map<String, String> headers,@Body() RequestBody body);

    /**
     * 点赞
     */
    @POST(UrlConstants.PRAISE_USER)
    Observable<HttpResult<AddChargeBean>> praise(@HeaderMap Map<String, String> headers,@Body() RequestBody body);

    /**
     * 评价星级
     */
    @POST(UrlConstants.STARS)
    Observable<StarsBean> stars(@HeaderMap Map<String, String> headers);

    /**
     * 删除帖子
     */
    @POST(UrlConstants.DELETE_POST)
    Observable<HttpResult<AddChargeBean>> delete(@HeaderMap Map<String, String> headers,@Body() RequestBody body);
}
