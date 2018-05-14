package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.CollectChargeApiService;
import com.haotang.easyshare.mvp.model.imodel.ICollectChargeModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:26
 */
public class CollectChargeModel implements ICollectChargeModel {
    /**
     * 收藏的充电桩列表
     */
    @Override
    public Observable list() {
        return DevRing.httpManager().getService(CollectChargeApiService.class).list();
    }
}
