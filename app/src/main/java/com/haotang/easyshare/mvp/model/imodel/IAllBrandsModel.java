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
 * @date XJ on 2018/5/8 16:44
 */
public interface IAllBrandsModel extends IBaseModel {
    /**
     * 所有品牌
     */
    Observable list(Map<String, String> headers);

    /**
     * 热门车型
     */
    Observable special(Map<String, String> headers);
}
