package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.entity.res.ScreenCarItem;

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
 * @date zhoujunxia on 2018/8/18 21:58
 */
public interface ScreenCarApiService {
    /**
     * 车型检索条件
     */
    @POST(UrlConstants.SCREENCAR_ITEM)
    Observable<ScreenCarItem> items(@HeaderMap Map<String, String> headers);

    /**
     * 车型检索
     *
     * @param build
     */
    @POST(UrlConstants.SCREENCAR_QUERY)
    Observable<HotSpecialCarBean> query(@HeaderMap Map<String, String> headers, @Body RequestBody build);
}
