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
 * @date XJ on 2018/7/25 14:12
 */
public interface IRefundModel extends IBaseModel {
    /**
     * 标签列表
     * @param build
     */
    Observable list(RequestBody build);

    /**
     * 退款说明
     */
    Observable explain();

    /**
     * 发起充值退款
     */
    Observable refund(RequestBody build);
}
