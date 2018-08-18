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
 * @date XJ on 2018/7/26 10:34
 */
public interface IScreenCarModel extends IBaseModel {
    /**
     * 车型检索条件
     */
    Observable items();

    /**
     * 车型检索
     */
    Observable query(RequestBody build);
}
