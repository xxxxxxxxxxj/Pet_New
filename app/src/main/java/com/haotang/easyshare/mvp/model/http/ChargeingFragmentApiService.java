package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.ChargeingBill;
import com.haotang.easyshare.mvp.model.entity.res.ChargeingState;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
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
 * @date XJ on 2018/8/8 14:53
 */
public interface ChargeingFragmentApiService {
    /**
     * 获取进行中的订单
     */
    @POST(UrlConstants.CHARGEING_ORDER)
    Observable<StartChargeing> ing(@HeaderMap Map<String, String> headers,@Body RequestBody build);

    /**
     * 查询充电状态
     *
     * @param build
     */
    @POST(UrlConstants.CHARGEING_STATE)
    Observable<ChargeingState> state(@HeaderMap Map<String, String> headers,@Body RequestBody build);

    /**
     * 结束充电
     *
     * @param build
     */
    @POST(UrlConstants.CHARGEING_STOP)
    Observable<ChargeingState> stop(@HeaderMap Map<String, String> headers,@Body RequestBody build);

    /**
     * 获取账单
     *
     * @param build
     */
    @POST(UrlConstants.CHARGEING_BILL)
    Observable<ChargeingBill> bill(@HeaderMap Map<String, String> headers,@Body RequestBody build);

    /**
     * 支付账单
     *
     * @param build
     */
    @POST(UrlConstants.CHARGEING_BILL_PAY)
    Observable<ChargeingState> pay(@HeaderMap Map<String, String> headers,@Body RequestBody build);

    /**
     * 故障报修
     *
     * @param build
     */
    @POST(UrlConstants.CHARGE_REPORT)
    Observable<HttpResult<AddChargeBean>> save(@HeaderMap Map<String, String> headers,@Body RequestBody build);

    /**
     * 取消订单
     *
     * @param build
     */
    @POST(UrlConstants.CANCEL_ORDER)
    Observable<HttpResult<AddChargeBean>> cancelOrder(@HeaderMap Map<String, String> headers,@Body RequestBody build);
}
