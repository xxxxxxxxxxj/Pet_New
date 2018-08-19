package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.RechargeFragMentApiService;
import com.haotang.easyshare.mvp.model.imodel.IRechargeFragmentModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 12:07
 */
public class RechargeFragmentModel implements IRechargeFragmentModel {
    /**
     * 交易记录列表
     * @param build
     */
    @Override
    public Observable list(RequestBody build) {
        return DevRing.httpManager().getService(RechargeFragMentApiService.class).list(build);
    }
}
