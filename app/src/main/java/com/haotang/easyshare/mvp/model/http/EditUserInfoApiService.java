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
 * @date XJ on 2018/5/24 10:17
 */
public interface EditUserInfoApiService {
    /**
     * 上传用户信息
     *
     * @param body
     */
    @POST(UrlConstants.UPDATE_USER_INFO)
    Observable<HttpResult<AddChargeBean>> save(@Body() RequestBody body);
}
