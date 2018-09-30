package com.haotang.easyshare.mvp.model.imodel;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/4 16:54
 */
public interface ISelectCouponModel extends IBaseModel{
    /**
     * 匹配可用优惠券列表
     * @param body
     */
    Observable match(Map<String, String> headers, RequestBody body);
}
