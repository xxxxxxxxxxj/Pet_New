package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.BrandCarBean;

import io.reactivex.Observable;
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
     * 品牌车型
     */
    @POST(UrlConstants.BRAND_AND_CAR)
    Observable<BrandCarBean> car();
}
