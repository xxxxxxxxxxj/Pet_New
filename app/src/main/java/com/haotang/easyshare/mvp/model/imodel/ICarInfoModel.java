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
 * @date XJ on 2018/5/7 17:11
 */
public interface ICarInfoModel extends IBaseModel {
    /**
     * 用户车辆信息
     */
    Observable my(Map<String, String> headers);

    /**
     * 保存或修改用户车辆信息
     */
    Observable save(Map<String, String> headers, RequestBody body);
}
