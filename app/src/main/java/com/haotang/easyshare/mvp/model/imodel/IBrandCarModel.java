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
 * @date XJ on 2018/6/4 18:12
 */
public interface IBrandCarModel extends IBaseModel {
    /**
     * 所有品牌
     */
    Observable list();

    /**
     * 品牌车型
     */
    Observable carList(RequestBody body);
}
