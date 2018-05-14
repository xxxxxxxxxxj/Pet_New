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
 * @date XJ on 2018/5/7 17:18
 */
public interface IChargingPileDetailModel extends IBaseModel {
    /**
     * 充电桩详情
     *
     * @param lng
     * @param lat
     * @param uuid
     * @param md5
     */
    Observable detail(double lng, double lat, String uuid, String md5);

    /**
     * 收藏充电桩
     *
     * @param parmMap
     */
    Observable follow(Map<String, String> parmMap);

    /**
     * 取消收藏充电桩
     *
     * @param parmMap
     */
    Observable cancel(Map<String, String> parmMap);
}
