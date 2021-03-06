package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

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
 * @date zhoujunxia on 2018/5/13 13:07
 */
public interface AddChargeApiService {
    /**
     * 上传充电桩
     *
     * @return
     */
    @POST(UrlConstants.SAVECHARGE)
    Observable<HttpResult<AddChargeBean>> save(@HeaderMap Map<String, String> headers,@Body() RequestBody body);

    /**
     * 编辑充电桩
     */
    @POST(UrlConstants.UPDATECHARGE)
    Observable<HttpResult<AddChargeBean>> update(@HeaderMap Map<String, String> headers,@Body() RequestBody body);
}
