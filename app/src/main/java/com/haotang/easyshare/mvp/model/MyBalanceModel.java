package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.MyFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.IMyBalanceModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 17:46
 */
public class MyBalanceModel implements IMyBalanceModel {
    /**
     * 用户主页信息
     */
    @Override
    public Observable home() {
        return DevRing.httpManager().getService(MyFragmentApiService.class).home();
    }
}
