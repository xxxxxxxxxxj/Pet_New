package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.FollowBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/14 18:02
 */
public interface MyFollowApiService {
    /**
     * 关注的人列表
     */
    @POST(UrlConstants.FOLLOW_LIST)
    Observable<FollowBean> list(@HeaderMap Map<String, String> headers);
}
