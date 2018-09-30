package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.MyCarBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/11 12:59
 */
public interface MyFragmentApiService {
    /**
     * 用户主页信息
     */
    @GET(UrlConstants.HOME)
    Observable<HttpResult<HomeBean>> home(@HeaderMap Map<String, String> headers);

    /**
     * 用户车辆信息
     */
    @POST(UrlConstants.MY_CAR)
    Observable<MyCarBean> my(@HeaderMap Map<String, String> headers);

    /**
     * 保存或修改用户车辆信息
     */
    @POST(UrlConstants.SAVE_ORUPDATE_USERCAR)
    Observable<HttpResult<AddChargeBean>> save(@HeaderMap Map<String, String> headers,@Body RequestBody body);
}
