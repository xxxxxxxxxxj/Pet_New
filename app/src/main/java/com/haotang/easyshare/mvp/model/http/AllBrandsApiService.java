package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;

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
 * @date XJ on 2018/5/15 10:25
 */
public interface AllBrandsApiService {
    /**
     * 所有品牌
     */
    @POST(UrlConstants.ALL_CAR_BRAND)
    Observable<HotCarBean> list(@HeaderMap Map<String, String> headers);

    /**
     * 所有品牌
     */
    @POST(UrlConstants.HOT_SPECIAL_CAR)
    Observable<HotSpecialCarBean> special(@HeaderMap Map<String, String> headers);
}
