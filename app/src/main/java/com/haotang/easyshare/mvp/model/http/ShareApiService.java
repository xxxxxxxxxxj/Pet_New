package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

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
 * @date XJ on 2018/5/21 18:59
 */
public interface ShareApiService {
    /**
     * 分享帖子成功回调
     *
     * @return
     */
    @POST(UrlConstants.SHARE_POST)
    Observable<HttpResult<AddChargeBean>> callback(@Body() RequestBody body);
}
