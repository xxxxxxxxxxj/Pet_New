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
 * @date XJ on 2018/5/7 17:25
 */
public interface ICollectChargeModel extends IBaseModel{
    /**
     * 收藏的充电桩列表
     */
    Observable list(Map<String, String> headers);
    /**
     * 取消收藏充电桩
     *
     * @param parmMap
     */
    Observable cancel(Map<String, String> headers,Map<String, String> parmMap);
}
