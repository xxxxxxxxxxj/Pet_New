package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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
    Observable<HttpResult<HomeBean>> info(@Field("uuid") String uuid);

    /**
     * 用户帖子列表
     */
    @FormUrlEncoded
    @POST(UrlConstants.USERINFO_POST)
    Observable<PostBean> list(@FieldMap() Map<String, String> parms);

    /**
     * 关注用户
     */
    @POST(UrlConstants.FOLLOW_USER)
    Observable<HttpResult<AddChargeBean>> follow(@Body() RequestBody body);

    /**
     * 取消关注用户
     */
    @POST(UrlConstants.CANCEL_FOLLOW_USER)
    Observable<HttpResult<AddChargeBean>> cancel(@Body() RequestBody body);
}
