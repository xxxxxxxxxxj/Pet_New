package com.haotang.easyshare.mvp.model.imodel;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:49
 */
public interface ISelectCarFragmentModel extends IBaseModel {
    /**
     * 热门品牌
     */
    Observable hot();

    /**
     * 热门车型
     */
    Observable special();
}
