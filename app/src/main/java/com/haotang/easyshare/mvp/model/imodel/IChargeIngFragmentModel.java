package com.haotang.easyshare.mvp.model.imodel;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:42
 */
public interface IChargeIngFragmentModel extends IBaseModel {
    /**
     * 获取进行中的订单
     */
    Observable ing();

    /**
     * 查询充电状态
     *
     * @param build
     */
    Observable state(RequestBody build);

    /**
     * 结束充电
     *
     * @param build
     */
    Observable stop(RequestBody build);

    /**
     * 获取账单
     *
     * @param build
     */
    Observable bill(RequestBody build);

    /**
     * 支付账单
     *
     * @param build
     */
    Observable pay(RequestBody build);
}
