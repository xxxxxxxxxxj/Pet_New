package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.HistoricalMessageApiService;
import com.haotang.easyshare.mvp.model.imodel.IHistoricalMessageFragmentModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:28
 */
public class HistoricalMessageFragmentModel implements IHistoricalMessageFragmentModel {
    /**
     * 管家留言列表
     */
    @Override
    public Observable history() {
        return DevRing.httpManager().getService(HistoricalMessageApiService.class).history();
    }
}
