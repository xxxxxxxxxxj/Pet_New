package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.CollectChargeBean;

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
 * @date XJ on 2018/5/14 16:34
 */
public interface CollectChargeApiService {
    /**
     * 收藏的充电桩列表
     */
    @POST(UrlConstants.COLLECT_CHARGE)
    Observable<CollectChargeBean> list(@HeaderMap Map<String, String> headers);
}
