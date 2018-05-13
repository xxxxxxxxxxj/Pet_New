package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

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
     * @param files
     * @param params
     * @return
     */
    @Multipart
    @POST(UrlConstants.SAVECHARGE)
    Observable<HttpResult<AddChargeBean>> save(@PartMap() Map<String, RequestBody> files, @PartMap() Map<String, String> params);
}
