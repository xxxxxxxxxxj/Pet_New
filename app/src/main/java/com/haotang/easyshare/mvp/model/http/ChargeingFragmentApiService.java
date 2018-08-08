package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.ChargeingBill;
import com.haotang.easyshare.mvp.model.entity.res.ChargeingState;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;

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
 * @date XJ on 2018/8/8 14:53
 */
public interface ChargeingFragmentApiService {
    /**
     * 获取进行中的订单
     */
    @POST(UrlConstants.CHARGEING_ORDER)
    Observable<StartChargeing> ing();

    /**
     * 查询充电状态
     *
     * @param build
     */
    @POST(UrlConstants.CHARGEING_STATE)
    Observable<ChargeingState> state(@Body RequestBody build);

    /**
     * 结束充电
     *
     * @param build
     */
    @POST(UrlConstants.CHARGEING_STOP)
    Observable<ChargeingState> stop(@Body RequestBody build);

    /**
     * 获取账单
     *
     * @param build
     */
    @POST(UrlConstants.CHARGEING_BILL)
    Observable<ChargeingBill> bill(@Body RequestBody build);

    /**
     * 支付账单
     *
     * @param build
     */
    @POST(UrlConstants.CHARGEING_BILL_PAY)
    Observable<ChargeingState> pay(@Body RequestBody build);
}
