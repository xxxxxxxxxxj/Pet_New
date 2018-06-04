package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;

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
 * @date XJ on 2018/6/4 19:11
 */
public interface BrandCarApiService {
    /**
     * 上传充电桩
     *
     * @return
     */
    @POST(UrlConstants.BRAND_CAR)
    Observable<HotSpecialCarBean> carList(@Body() RequestBody body);
}
