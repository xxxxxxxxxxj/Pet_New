package com.haotang.easyshare.mvp.model.imodel;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;

import java.util.Map;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:49
 */
public interface ILocalChargingModel extends IBaseModel{
    /**
     * 附近充电桩
     * @param lng
     * @param lat
     * @param mNextRequestPage
     * @param sign
     */
    Observable nearby(Map<String, String> headers, double lng, double lat, int mNextRequestPage, String sign);
}
