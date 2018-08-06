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
 * @date XJ on 2018/7/23 16:47
 */
public interface IRechargeModel extends IBaseModel {
    /**
     * 可充值模板列表
     */
    Observable list();

    /**
     * 发起充值请求
     */
    Observable build(RequestBody body);
}
