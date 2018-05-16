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
 * @date XJ on 2018/5/7 16:43
 */
public interface IAddChargeModel extends IBaseModel {
    /**
     * 上传充电桩
     */
    Observable save(RequestBody body);

    /**
     * 充电桩详情
     *
     * @param lng
     * @param lat
     * @param uuid
     * @param md5
     */
    Observable detail(double lng, double lat, String uuid, String md5);

    /**
     * 编辑充电桩
     */
    Observable update(RequestBody body);
}
