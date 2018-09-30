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
 * @date XJ on 2018/5/7 16:43
 */
public interface IAddChargeModel extends IBaseModel {
    /**
     * 上传充电桩
     */
    Observable save(Map<String, String> headers, RequestBody body);

    /**
     * 充电桩详情
     *
     * @param lng
     * @param lat
     * @param uuid
     * @param md5
     */
    Observable detail(Map<String, String> headers,double lng, double lat, String uuid, String md5);

    /**
     * 编辑充电桩
     */
    Observable update(Map<String, String> headers,RequestBody body);
}
